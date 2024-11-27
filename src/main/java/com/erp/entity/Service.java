package com.erp.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

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
@Table
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Service {

    @Id
	@Column(name="service_code")
	private String serviceCode;
	@Column(name="service_name")
	private String serviceName;
	@Column(name="service_cnt")
	private Integer serviceCnt;
	@Column(name="service_price")
	private Integer servicePrice;
	//private String branchCode;
	
    public Service(String serviceCode, String serviceName, int servicePrice) {
        this.serviceCode = serviceCode;
        this.serviceName = serviceName;
        this.servicePrice = servicePrice;
    }

	@OneToMany(mappedBy = "service",fetch = FetchType.EAGER)
	@JsonBackReference
	private List<Reservation> reservationList;
}
