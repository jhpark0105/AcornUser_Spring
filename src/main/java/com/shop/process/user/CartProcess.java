package com.shop.process.user;

import com.shop.dto.CartDto;
import com.shop.entity.Cart;
import com.shop.entity.Customer;
import com.shop.entity.Product;
import com.shop.repository.CartRepository;
import com.shop.repository.CustomerRepository;
import com.shop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartProcess {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;

    public void addItemToCart(CartDto cartDto) {
        Customer customer = customerRepository.findById(cartDto.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        Product product = productRepository.findById(cartDto.getProductCode())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Cart cart = Cart.toEntity(cartDto, customer, product);
        cartRepository.save(cart);
    }

    // 고객 아이디로 장바구니 불러오기
    public List<CartDto> getCartItemsByCustomerId(int customerId) {
        List<Cart> carts = cartRepository.findByCustomer_CustomerId(customerId);
        return carts.stream().map(CartDto::toDto).collect(Collectors.toList());
    }

    public void removeItemFromCart(Long cartId) {
        cartRepository.deleteById(cartId);
    }

    // 특정 고객의 장바구니 비우기
    public void clearCartByCustomerId(int customerId) {
        cartRepository.deleteByCustomer_CustomerId(customerId);
    }

    public List<CartDto> getCartItemsByCustomerShopid(String customerShopid) {
        List<Cart> cartItems = cartRepository.findByCustomer_CustomerShopid(customerShopid);
        return cartItems.stream()
                .map(CartDto::toDto)
                .collect(Collectors.toList());
    }


}
