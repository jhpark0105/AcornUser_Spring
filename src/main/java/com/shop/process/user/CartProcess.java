package com.shop.process.user;

import com.shop.dto.CartDto;
import com.shop.entity.Cart;
import com.shop.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartProcess {

    @Autowired
    private CartRepository cartRepository;

    // 장바구니 추가
    public void addItemToCart(CartDto cartDto) {
        Cart cart = Cart.toEntity(cartDto);
        cartRepository.save(cart);
    }
    
    // 상품 삭제
    public void removeItemFromCart(int customerId, String productCode) {
        cartRepository.deleteByCustomerIdAndProductCode(customerId, productCode);
    }

    // 장바구니 전체 리스트 불러오기
    public List<CartDto> getCartItems(int customerId) {
        return cartRepository.findByCustomerId(customerId).stream()
                .map(CartDto::toDto)
                .collect(Collectors.toList());
    }

    // 장바구니 비우기
    public void clearCart(int customerId) {
        cartRepository.deleteByCustomerId(customerId);
    }
}
