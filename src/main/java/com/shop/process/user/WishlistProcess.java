package com.shop.process.user;

import com.shop.dto.ProductDto;
import com.shop.dto.WishlistDto;
import com.shop.entity.Customer;
import com.shop.entity.Product;
import com.shop.entity.Wishlist;
import com.shop.repository.ProductRepository;
import com.shop.repository.WishlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WishlistProcess {

    @Autowired
    private WishlistRepository wishlistRepository;

    @Autowired
    private ProductRepository productRepository;

    // 특정 고객의 위시리스트 조회
    public List<WishlistDto> findCustomerWishlists(int customerId) {
        return wishlistRepository.findByCustomer_CustomerId(customerId).stream()
                .map(WishlistDto::fromEntity)
                .collect(Collectors.toList());
    }

    // 특정 상품 정보 조회
    public ProductDto getProductData(String productCode) {
        Product product = productRepository.findProductOne(productCode);
        return ProductDto.fromEntity(product);
    }

    // 위시리스트에 상품 추가
    public void addWishlist(int customerId, String productCode) {
        Wishlist wishlist = Wishlist.builder()
                .customer(Customer.builder().customerId(customerId).build())
                .product(Product.builder().productCode(productCode).build())
                .createAt(LocalDateTime.now())
                .build();

        wishlistRepository.save(wishlist);
    }

    // 위시리스트에서 상품 제거
    public void deleteWishlist(int customerId, String productCode) {
        wishlistRepository.deleteByCustomer_CustomerIdAndProduct_ProductCode(customerId, productCode);
    }

    // 위시리스트 상태 확인
    public boolean isProductInWishlist(int customerId, String productCode) {
        return wishlistRepository.existsByCustomer_CustomerIdAndProduct_ProductCode(customerId, productCode);
    }

    // 여러 상품 데이터를 가져오는 메서드
    public List<ProductDto> getProductsByCodes(List<String> productCodes) {
        List<Product> products = productRepository.findAllByProductCodeIn(productCodes);
        if (products == null || products.isEmpty()) {
            throw new RuntimeException("No products found for the given codes.");
        }
        return products.stream()
                .map(ProductDto::fromEntity) // Product 엔티티를 ProductDto로 변환
                .collect(Collectors.toList());
    }
}
