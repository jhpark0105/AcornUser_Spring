package com.shop.entity;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "orders_no")
	private Integer ordersNo;	//발주번호
	
	@Column(name = "orders_ea")
	private Integer ordersEa;	//발주 신청한 상품 개수
	
	@Column(name = "orders_price")
	private Integer ordersPrice;	//발주 신청한 상품 금액(상품가격*ordersEa)
	
	@Column(name = "orders_apply_date")
	private LocalDate ordersApplyDate;	//발주 신청일
	
	@Column(name = "orders_end_date")
	private LocalDate ordersEndDate;	//발주 마감일
	
	@Column(name = "orders_status", columnDefinition = "TINYINT DEFAULT 0")
	private Integer ordersStatus;	//발주 상태

    @OneToOne
    @JoinColumn(name = "product_code", nullable = false)
    @JsonBackReference
    private Product product;
	//@ManyToOne
	//@JoinColumn(name = "branch_code")
	//@JsonBackReference
	//private Branch branch;
	
}