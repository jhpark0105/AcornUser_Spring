package com.shop.controller.user;

import com.shop.entity.Wishlist;
import com.shop.process.user.WishlistProcess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class WishlistController {

    @Autowired
    private WishlistProcess wishlistProcess;

    @GetMapping("/wishlist/{customerId}")
    public List<Wishlist> getCustomerWishlist(@PathVariable int customerId) {
        return wishlistProcess.findCustomerWishlists(customerId);
    }

    // 위시리스트 추가
    @PostMapping("/wishlist")
    public ResponseEntity<String> addWishlist(@RequestParam int customerId, @RequestParam String productCode) {
        wishlistProcess.addWishlist(customerId, productCode);
        return ResponseEntity.ok("위시리스트에 추가되었습니다.");
    }

    // 위시리스트 삭제
    @DeleteMapping("/wishlist")
    public ResponseEntity<String> deleteWishlist(@RequestParam int customerId, @RequestParam String productCode) {
        wishlistProcess.deleteWishlist(customerId, productCode);
        return ResponseEntity.ok("위시리스트에서 제거되었습니다.");
    }

    // 위시 상태 확인
    @GetMapping("/check")
    public boolean checkWishlistStatus(@RequestParam int customerId, @RequestParam String productCode){
        return wishlistProcess.isProductInWishlist(customerId, productCode);
    }
}
