package com.erp.entity;

import java.time.LocalDate;
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
@Table(name="member")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member {
	@Id
	@Column(name="member_id")
	private String memberId;
	
	@Column(name="member_name")
	private String memberName;
	
	@Column(name="member_job")
	private String memberJob;
	
	@Column(name="member_date")
	private LocalDate memberDate;
	
	@Column(name="member_tel")
	private String memberTel;
	
	@Column(name="member_cnt")
	private int memberCnt;
	
	@Column(name="branch_code")
	private String branchCode;
	
	@OneToMany(mappedBy = "member",fetch = FetchType.EAGER)
	@JsonBackReference
	private List<Reservation> reservationList;
	
}
