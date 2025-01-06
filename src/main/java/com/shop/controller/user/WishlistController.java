package com.shop.controller.user;

import com.shop.dto.CustomerDto;
import com.shop.dto.ProductDto;
import com.shop.dto.WishlistDto;
import com.shop.entity.Customer;
import com.shop.process.user.CustomerLoginProcess;
import com.shop.process.user.WishlistProcess;
import com.shop.provider.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/wishlist")
public class WishlistController {

    @Autowired
    private WishlistProcess wishlistProcess;

    @Autowired
    private CustomerLoginProcess customerLoginProcess;

    @Autowired
    private JwtProvider jwtProvider;

    // 쿠키에서 customerShopId 추출하여 고객 정보 조회
    @GetMapping("/user")
    public CustomerDto getCustomerByToken(@CookieValue(name = "accessToken") String token) {
        String customerShopId = jwtProvider.validate(token);
        Customer customer = customerLoginProcess.findOne(customerShopId);
        return CustomerDto.toDto(customer);
    }

    // 특정 고객의 위시리스트 조회
    @GetMapping("/{customerId}")
    public List<WishlistDto> getCustomerWishlist(@PathVariable int customerId) {
        return wishlistProcess.findCustomerWishlists(customerId);
    }

    // 특정 상품의 상세 정보 조회
    @GetMapping("/product/{productCode}")
    public ProductDto getProductByCode(@PathVariable String productCode) {
        return wishlistProcess.getProductData(productCode);
    }

    // 위시리스트에 상품 추가
    @PostMapping
    public ResponseEntity<String> addWishlist(@RequestParam int customerId, @RequestParam String productCode) {
        wishlistProcess.addWishlist(customerId, productCode);
        return ResponseEntity.ok("Item added to wishlist.");
    }

    // 위시리스트에서 상품 제거
//    @DeleteMapping
//    public ResponseEntity<String> removeWishlist(@RequestParam int customerId, @RequestParam String productCode) {
//        wishlistProcess.deleteWishlist(customerId, productCode);
//        return ResponseEntity.ok("Item removed from wishlist.");
//    }
    @DeleteMapping
    public ResponseEntity<?> removeWishlist(
            @RequestParam int customerId,
            @RequestParam String productCode) {
        try {
            // 위시리스트 아이템 삭제
            boolean isDeleted = wishlistProcess.deleteWishlist(customerId, productCode);

            if (isDeleted) {
                return ResponseEntity.ok("Item removed from wishlist.");
            } else {
                return ResponseEntity.badRequest().body("Wishlist item not found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to remove item: " + e.getMessage());
        }
    }

    // 위시리스트 상태 확인
    @GetMapping("/check")
    public boolean checkWishlistStatus(@RequestParam int customerId, @RequestParam String productCode) {
        return wishlistProcess.isProductInWishlist(customerId, productCode);
    }

    // 여러 상품 정보를 가져오는 메서드
    @GetMapping("/products")
    public ResponseEntity<?> getProductsByCodes(@RequestParam("codes") String codes) {
        try {
            // "codes"를 ','로 분리하여 List 생성
            List<String> productCodes = Arrays.asList(codes.split(","));
            if (productCodes.isEmpty()) {
                return ResponseEntity.badRequest().body("Product codes are required.");
            }

            // 상품 데이터 가져오기
            List<ProductDto> products = wishlistProcess.getProductsByCodes(productCodes);
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            // 예외를 출력하고 500 응답 반환
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while fetching products.");
        }
    }

    //좋아요 상태를 토글
    @PostMapping("/toggle")
    public ResponseEntity<?> toggleWishlist(
            @RequestParam int customerId,
            @RequestParam String productCode) {
        try {
            // 좋아요 상태 토글
            boolean isLiked = wishlistProcess.toggleWishlist(customerId, productCode);

            if (isLiked) {
                return ResponseEntity.ok("위시리스트에 추가되었습니다.");
            } else {
                return ResponseEntity.ok("위시리스트에서 제거되었습니다.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to toggle wishlist item: " + e.getMessage());
        }
    }
}
