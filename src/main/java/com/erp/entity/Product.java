package com.erp.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "product")
public class Product {
    @Id
    @Column(name = "product_code")
    private String productCode;  // 소분류 코드

    @Column(name = "product_name")
    private String productName;  // 소분류 이름

    @Column(name = "product_price")
    private int productPrice;    // 소분류 가격

    @Column(name = "product_ea")
    private int productEa;       // 소분류 수량

    //ManyToOne 관계로 대분류를 참조
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_b_code", referencedColumnName = "product_b_code")
    @JsonBackReference
    private Product_B product_b;  //하나의 Product_B를 참조
}