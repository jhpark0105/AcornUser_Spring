package com.erp.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
public class Customer {
	@Id
	private int customerId;
	private String customerName;
	private String customerGender;
	private String customerTel;
	private String customerMail;
	private String customerReg;
	private String customerRank;
	private String customerNote;
	private int customerTotal;
	
	private String branchCode;

	@OneToMany(mappedBy = "customer",fetch = FetchType.EAGER)
	@JsonBackReference
	private List<Reservation> reservationList;
}
