package com.erp.process.branch;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.erp.dto.ProductBDto;
import com.erp.dto.ProductDto;
import com.erp.dto.ProductDtoFO;
import com.erp.entity.Product;
import com.erp.entity.Product_B;
import com.erp.repository.ProductBRepository;
import com.erp.repository.ProductRepository;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProductProcess {
	@Autowired
    private ProductRepository productRepository;

	@Autowired
    private ProductBRepository productBRepository;

	@Value("${file.upload-dir.product}")
	private String productUploadDir;

	//대분류 목록 조회
	@Transactional
	public List<ProductBDto> getBProductAll() {
        List<Product_B> productBs = productBRepository.findAll();

        return productBs.stream()
                .map(productB -> new ProductBDto(productB.getProductBCode(), productB.getProductBName()))
                .collect(Collectors.toList());
    }

	//대분류에서 하나의 데이터 읽기
	@Transactional
    public Product_B getBProductOne(String productBCode) {
    	return productBRepository.findProductBOne(productBCode);
    }

	//대분류 등록
	@Transactional
	public Map<String, Object> insertBProduct(ProductBDto productBDto) {
	    Map<String, Object> response = new HashMap<>();

	    try {
	        // 대분류 코드가 이미 있는 경우
	        if (productBRepository.findById(productBDto.getProductBCode()).isPresent()) {
	            response.put("isSuccess", false);
	            response.put("message", "이미 존재하는 대분류입니다.");

	            return response;
	        } else {
	            // 대분류 코드가 없음 > 등록
	            Product_B product_B = new Product_B(productBDto.getProductBCode(), productBDto.getProductBName(), null);

	            productBRepository.save(product_B);

	            response.put("isSuccess", true);
	            response.put("message", "대분류 등록 성공!");

	            return response;
	        }
	    } catch (Exception e) {
	        response.put("isSuccess", false);
	        response.put("message", "입력 자료 오류입니다. " + e.getMessage());

	        return response;
	    }
	}

	// 대분류 삭제(소분류가 있으면 삭제할 수 없음)
	@Transactional
	public Map<String, Object> deleteBProduct(String productCode) {
	    Map<String, Object> response = new HashMap<>();

	    try {
	        // 대분류 코드 존재 여부 확인
	        if (!productBRepository.existsById(productCode)) {
	            response.put("isSuccess", false);
	            response.put("message", "해당 번호의 데이터가 존재하지 않습니다.");
	            return response;
	        }

	        // 해당 대분류에 소분류가 존재하는지 확인
	        if (productRepository.existsByProductBCode(productCode)) {
	            response.put("isSuccess", false);
	            response.put("message", "해당 대분류에 소분류 상품이 존재합니다. 삭제할 수 없습니다.");

	            return response;
	        }

	        // 대분류 삭제
	        productBRepository.deleteById(productCode);
	        response.put("isSuccess", true);
	        response.put("message", "대분류 삭제 성공!");
	    } catch (Exception e) {
	        response.put("isSuccess", false);
	        response.put("message", "삭제 중 오류 발생: " + e.getMessage());
	    }

	    return response;
	}

	//소분류 목록 조회
	@Transactional
	public List<ProductDto> getProductList() {
	    List<Product> products = productRepository.findAll();

	    return products.stream()
	        .map(product -> ProductDto.builder()
	            .product_b(product.getProduct_b())  // 대분류 객체
	            .productCode(product.getProductCode())  // 상품 소분류 코드
	            .productName(product.getProductName())  // 상품 이름
	            .productPrice(product.getProductPrice())  // 상품 가격
	            .productEa(product.getProductEa())  // 상품 수량
				.productImagePath(product.getProductImagePath()) // 상품 사진
	            .build())
	        .collect(Collectors.toList());
	}

	//소분류(재고 10개 미만) 목록 조회
	public List<ProductDto> getProductListLessThan10() {
		return productRepository.getProductListLessThan10().stream()
				.map(ProductDto::fromEntity)
				.toList();
	}

	//대분류별 소분류 목록 조회
	public List<ProductDtoFO> getProductListWithBCode(String productBCode){
		return productRepository.getProductListWithBCode(productBCode).stream()
				.map(ProductDtoFO::fromEntity)
				.toList();
	}

	//소분류 상품 1개의 자료 읽기
	@Transactional
	public Product getProductOne(String productCode) {
	    return productRepository.findProductOne(productCode);
	}

	//대분류 목록을 DB에서 가져오기
	public List<Product_B> getProductBList() {
	       return productBRepository.findAll();
	}

	// 파일 업로드 메서드
	private String uploadFile(MultipartFile file) throws IOException {
		System.out.println("Product Upload Directory in uploadFile: " + productUploadDir);

		if (file == null || file.isEmpty()) {
			throw new IllegalArgumentException("파일이 비어있습니다.");
		}

		String imageName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
		Path uploadPath = Paths.get(productUploadDir).toAbsolutePath();

		if (Files.notExists(uploadPath)) {
			Files.createDirectories(uploadPath);
		}

		Path filePath = uploadPath.resolve(imageName);
		Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

		return "/uploads/product/" + imageName;
	}

	//소분류 등록
	@Transactional
	public Map<String, Object> insertProduct(ProductDto productDto, MultipartFile image) {
	    Map<String, Object> response = new HashMap<>();
	    try {
	        // 상품 코드 확인
	        if (productRepository.findById(productDto.getProductCode()).isPresent()) {
				response.put("isSuccess", false);
				response.put("message", "이미 존재하는 상품입니다.");

				return response;
			}

	        // productBCode로 Product_B를 조회하여 product_b에 설정
	        Product_B productB = productBRepository.findProductBOne(productDto.getProductBCode());

			if (productB == null) {
				response.put("isSuccess", false);
				response.put("message", "유효하지 않은 대분류 코드입니다.");

				return response;
			}

			// 이미지 업로드 처리
			String uploadedImagePath = null;

			if (image != null && !image.isEmpty()) {
				uploadedImagePath = uploadFile(image);

				if (uploadedImagePath == null) {
					response.put("isSuccess", false);
					response.put("message", "이미지 업로드 실패: 파일 경로 생성 오류");

					return response;
				}
			} else {
				response.put("isSuccess", false);
				response.put("message", "상품 이미지를 업로드해주세요.");

				return response;
			}

	        // Product 객체 생성 (product_b는 이제 productB로 설정됨)
	        Product product = new Product(
	            productDto.getProductCode(),
	            productDto.getProductName(),
	            productDto.getProductPrice(),
	            productDto.getProductEa(),
				uploadedImagePath, //업로드된 이미지 경로 설정
	            productB // Product_B 객체 설정
	        );

			System.out.println("product : " + product);

	        // 상품 저장
	        productRepository.save(product);

	        response.put("isSuccess", true);
	        response.put("message", "상품 등록 성공!");
	        
	        return response;
	    } catch (Exception e) {
	        response.put("isSuccess", false);
	        response.put("message", "입력 자료 오류입니다. " + e.getMessage());

	        return response;
	    }
	}

	// 소분류 수정
	@Transactional
	public Map<String, Object> updateProduct(ProductDto productDto, MultipartFile image) {
	    Map<String, Object> response = new HashMap<>();

	    try {
	        // 소분류 코드가 존재하는지 확인
	        if (!productRepository.existsById(productDto.getProductCode())) {
	            response.put("isSuccess", false);
	            response.put("message", "해당 번호의 데이터가 존재하지 않습니다.");
	            
	            return response;
	        }

	        // 대분류 코드가 존재하는지 확인
	        Product_B productB = productBRepository.findById(productDto.getProduct_b().getProductBCode()).orElse(null);
	        if (productB == null) {
	            response.put("isSuccess", false);
	            response.put("message", "해당 대분류 코드가 존재하지 않습니다.");
	            
	            return response;
	        }

	        // 이미지 업로드 처리
	        String uploadedImagePath = productDto.getProductImagePath(); // 기본 경로는 기존 이미지
	        if (image != null && !image.isEmpty()) {
	            uploadedImagePath = uploadFile(image); // 새로운 이미지 업로드
	        }

	        // 수정할 상품 객체 생성
	        Product product = new Product(
	            productDto.getProductCode(),
	            productDto.getProductName(),
	            productDto.getProductPrice(),
	            productDto.getProductEa(),
	            uploadedImagePath, //새 이미지 경로 또는 기존 경로 설정
	            productB
	        );
	        
	        System.out.println("상품 수정 product 사진 : " + uploadedImagePath);

	        // 상품 저장
	        productRepository.save(product);

	        response.put("isSuccess", true);
	        response.put("message", "소분류 수정 성공!");
	        
	        return response;
	    } catch (Exception e) {
	        response.put("isSuccess", false);
	        response.put("message", "수정 중 오류 발생: " + e.getMessage());
	        
	        return response;
	    }
	}

	// 소분류 삭제
	@Transactional
	public Map<String, Object> deleteProduct(String productCode) {
	    Map<String, Object> response = new HashMap<>();

	    try {
	        // 소분류 코드가 존재하는지 확인
	        if (!productRepository.existsById(productCode)) {
	            response.put("isSuccess", false);
	            response.put("message", "해당 번호의 데이터가 존재하지 않습니다.");
	            
	            return response;
	        }

	        // 소분류 삭제
	        productRepository.deleteById(productCode);

	        response.put("isSuccess", true);
	        response.put("message", "소분류 삭제 성공!");
	        
	        return response;
	    } catch (Exception e) {
	        response.put("isSuccess", false);
	        response.put("message", "삭제 중 오류 발생: " + e.getMessage());

	        return response;
	    }
	}
}