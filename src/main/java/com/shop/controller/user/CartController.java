package com.shop.controller.user;

import com.shop.dto.CartDto;
import com.shop.process.user.CartProcess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class CartController {

    @Autowired
    private CartProcess cartProcess;

    /**
     * 장바구니에 아이템 추가
     * @param cartDto 장바구니에 추가할 상품 정보
     * @return 성공 메시지
     */
    @PostMapping("/add")
    public ResponseEntity<String> addItem(@RequestBody CartDto cartDto) {
        cartProcess.addItemToCart(cartDto);
        return ResponseEntity.ok("Item added to the cart.");
    }

    /**
     * 특정 고객의 장바구니 아이템 가져오기
     * @param customerId 고객 ID
     * @return 장바구니 항목 리스트
     */
    @GetMapping("/items/{customerId}")
    public ResponseEntity<List<CartDto>> getCartItems(@PathVariable int customerId) {
        List<CartDto> items = cartProcess.getCartItemsByCustomerId(customerId);
        return ResponseEntity.ok(items);
    }

    /**
     * 장바구니에서 특정 아이템 제거
     * @param cartId 장바구니 아이템 ID
     * @return 성공 메시지
     */
    @DeleteMapping("/remove/{cartId}")
    public ResponseEntity<String> removeItem(@PathVariable Long cartId) {
        cartProcess.removeItemFromCart(cartId);
        return ResponseEntity.ok("Item removed from the cart.");
    }

    /**
     * 특정 고객의 장바구니 비우기
     * @param customerId 고객 ID
     * @return 성공 메시지
     */
    @DeleteMapping("/clear/{customerId}")
    public ResponseEntity<String> clearCart(@PathVariable int customerId) {
        cartProcess.clearCartByCustomerId(customerId);
        return ResponseEntity.ok("Cart cleared.");
    }
}
