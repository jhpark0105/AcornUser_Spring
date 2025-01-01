package com.shop.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import com.shop.dto.CustomerDto;
import com.shop.dto.SignUpRequestDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
@Table(name = "customer")
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //자동증가(회원번호)
	@Column(name="customer_id")
	private int customerId;
	
	@Column(name="customer_name")
	private String customerName;
	
	@Column(name = "customer_gender")
	private String customerGender;
	
	@Column(name = "customer_tel")
	private String customerTel;
	
	@Column(name = "customer_mail")
	private String customerMail;
	
	@Column(name = "customer_reg")
	private String customerReg;
	
	@Column(name = "customer_rank")
	private String customerRank;
	
	@Column(name = "customer_note")
	private String customerNote;
	
	@Column(name = "customer_total")
	private int customerTotal;

	@Column(name = "customer_shopid")
	private String customerShopid;

	@Column(name = "customer_shoppw")
	private String customerShoppw;

	@Column(name = "customer_postcode")
	private String customerPostcode;

	@Column(name = "customer_addr1")
	private String customerAddr1;

	@Column(name = "customer_addr2")
	private String customerAddr2;


	@OneToMany(mappedBy = "customer",fetch = FetchType.EAGER)
	@JsonBackReference
	private List<Reservation> reservationList;

	// toEntity
	// 클라이언트로부터 받아온 데이터를 엔티티에 저장하는 메소드
	public static Customer toEntity(CustomerDto dto) {
		return Customer.builder()
				.customerId(dto.getCustomerId())
				.customerName(dto.getCustomerName())
				.customerGender(dto.getCustomerGender())
				.customerTel(dto.getCustomerTel())
				.customerMail(dto.getCustomerMail())
				.customerReg(dto.getCustomerReg())
				.customerShopid(dto.getCustomerShopid())
				.customerShoppw(dto.getCustomerShoppw())
				.customerPostcode(dto.getCustomerPostcode())
				.customerAddr1(dto.getCustomerAddr1())
				.customerAddr2(dto.getCustomerAddr2())
				.build();
	}

	// 회원 가입 시 클라이언트로부터 받아온 데이터를 엔티티에 저장하는 메소드
	// 데이터 저장 및 데이터베이스 매핑용 객체를 생성
	public Customer(SignUpRequestDto dto) {
		this.customerShopid = dto.getCustomerShopid();
		this.customerShoppw = dto.getCustomerShoppw();
		this.customerName = dto.getCustomerName();
		this.customerGender = dto.getCustomerGender();
		this.customerMail = dto.getCustomerMail();
		this.customerTel = dto.getCustomerTel();
		this.customerPostcode = dto.getCustomerPostcode();
		this.customerAddr1 = dto.getCustomerAddr1();
		this.customerAddr2 = dto.getCustomerAddr2();
	}
}
