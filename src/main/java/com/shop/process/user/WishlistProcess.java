package com.shop.process.user;

import com.shop.entity.Customer;
import com.shop.entity.Product;
import com.shop.entity.Wishlist;
import com.shop.repository.CustomerRepository;
import com.shop.repository.WishlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class WishlistProcess {
    @Autowired
    private WishlistRepository wishlistRepository;

    @Autowired
    private CustomerRepository customerRepository;

    // 로그인한 고객의 위시리스트 조회
    public List<Wishlist> findCustomerWishlists(int customerId) {
        // customerId로 위시리스트 조회
        return wishlistRepository.findByCustomer_CustomerId(customerId);
    }

    // 위시리스트 추가
    public void addWishlist(int customerId, String productCode) {
        Customer customer = new Customer();
        customer.setCustomerId(customerId);
        Product product = new Product();
        product.setProductCode(productCode);

        Wishlist wishlist = Wishlist.builder().customer(customer).product(product).build();
        wishlistRepository.save(wishlist);
    }

    // 위시리스트 삭제
    public void deleteWishlist(int customerId, String productCode) {
        wishlistRepository.deleteByCustomer_CustomerIdAndProduct_ProductCode(customerId, productCode);
    }

    //위시 상태 확인
    public boolean isProductInWishlist(int customerId, String productCode) {
        return wishlistRepository.existsByCustomer_CustomerIdAndProduct_ProductCode(customerId,productCode);
    }
}
