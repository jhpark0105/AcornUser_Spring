package com.shop.controller;

import com.shop.dto.CartDto;
import com.shop.process.user.CartProcess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartProcess cartProcess;

    @PostMapping("/add")
    public ResponseEntity<String> addItem(@RequestBody CartDto cartDto) {
        cartProcess.addItemToCart(cartDto);
        return ResponseEntity.ok("Item added to the cart.");
    }

    @DeleteMapping("/remove/{customerId}/{productCode}")
    public ResponseEntity<String> removeItem(@PathVariable int customerId, @PathVariable String productCode) {
        cartProcess.removeItemFromCart(customerId, productCode);
        return ResponseEntity.ok("Item removed from the cart.");
    }

    @GetMapping("/items/{customerId}")
    public ResponseEntity<List<CartDto>> getCartItems(@PathVariable int customerId) {
        List<CartDto> items = cartProcess.getCartItems(customerId);
        return ResponseEntity.ok(items);
    }

    @DeleteMapping("/clear/{customerId}")
    public ResponseEntity<String> clearCart(@PathVariable int customerId) {
        cartProcess.clearCart(customerId);
        return ResponseEntity.ok("Cart cleared.");
    }
}
