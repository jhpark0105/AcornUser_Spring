package com.erp.entity;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonBackReference;
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
	private Integer ordersNo;
	private Integer ordersEa;
	private Integer ordersPrice;
	private LocalDate ordersApplyDate;
	private LocalDate ordersEndDate;
    @OneToOne
    @JoinColumn(name = "product_code", nullable = false)
    @JsonBackReference
    private Product product;
	@ManyToOne
	@JoinColumn(name = "branch_code")
	@JsonBackReference
	private Branch branch;
	
}