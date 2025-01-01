package com.shop.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

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
	
	private String branchCode;

	@OneToMany(mappedBy = "customer",fetch = FetchType.EAGER)
	@JsonBackReference
	private List<Reservation> reservationList;
}
