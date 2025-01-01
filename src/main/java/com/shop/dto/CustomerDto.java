package com.shop.dto;

import com.shop.entity.Customer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto {
	private int customerId;
	private String customerName;
	private String customerGender;
	private String customerTel;
	private String customerMail;
	private String customerReg;
	private String customerRank;
	private String customerNote;
	private int customerTotal;
	
	private String customerShopid, customerShoppw, customerPostcode, customerAddr1, customerAddr2;
	
	public static CustomerDto fromEntity(Customer entity) {
		return CustomerDto.builder()
				.customerId(entity.getCustomerId())
				.customerName(entity.getCustomerName())
				.customerGender(entity.getCustomerGender())
				.customerTel(entity.getCustomerTel())
				.customerMail(entity.getCustomerMail())
				.customerReg(entity.getCustomerReg())
				.customerRank(entity.getCustomerRank())
				.customerNote(entity.getCustomerNote())
				.customerTotal(entity.getCustomerTotal())
				.build();
	}

	public Customer toEntity() {
		return Customer.builder()
				.customerId(this.customerId)
				.customerName(this.customerName)
				.customerGender(this.customerGender)
				.customerTel(this.customerTel)
				.customerMail(this.customerMail)
				.customerReg(this.customerReg)
				.customerRank(this.customerRank)
				.customerNote(this.customerNote)
				.build();
	}

	public static CustomerDto toDto(Customer customer) {
		return CustomerDto.builder()
				.customerShopid(customer.getCustomerShopid())
				.customerShoppw(customer.getCustomerShoppw())
				.customerName(customer.getCustomerName())

				.customerTel(customer.getCustomerTel())
				.customerMail(customer.getCustomerMail())
				.customerPostcode(customer.getCustomerPostcode())
				.customerAddr1(customer.getCustomerAddr1())
				.customerAddr2(customer.getCustomerAddr2())
				.build();
	}
}
