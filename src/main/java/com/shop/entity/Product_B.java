package com.shop.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product_b")
public class Product_B {
    @Id
    @Column(name = "product_b_code")
    private String productBCode;  // 대분류 코드

    @Column(name = "product_b_name")
    private String productBName;   // 대분류 이름

    // OneToMany 관계로 여러 Product들이 대분류를 참조
    @OneToMany(mappedBy = "product_b", fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Product> productList;  // 소분류 목록
}