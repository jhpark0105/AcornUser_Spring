package com.erp.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.erp.dto.CustomerDto;
import com.erp.process.CustomerProcess;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class CustomerController {
	@Autowired
	private CustomerProcess customerProcess;
	
	// 전체 회원
	@GetMapping("customer")
	public List<CustomerDto> listProcess(){
		return customerProcess.getCustomerAll();
	}
	
	// 한 명 읽기
	@GetMapping("customer/{customerName}")
	public CustomerDto getCustomerByName(@PathVariable("customerName")String customerName) {
		
		return customerProcess.getCustomerByName(customerName);
	}
	
	// 회원 추가
	@PostMapping("customer")
	public Map<String, Object> insertProcess(@RequestBody CustomerDto customerDto) {
		customerProcess.insert(customerDto);
		return Map.of("isSuccess", true);
	}

	// 수정
	@PutMapping("customer/{customerId}")
	public Map<String, Object> updateProcess(@PathVariable("customerId")int customerId,
											@RequestBody CustomerDto customerDto){
		customerDto.setCustomerId(customerId);
		customerProcess.update(customerDto);
		return Map.of("isSuccess", true);
	}
	
	// 삭제
	@DeleteMapping("customer/{customerId}")
	public Map<String, Object> deleteProcess(@PathVariable("customerId")int customerId,
											@RequestBody CustomerDto customerDto){
		customerDto.setCustomerId(customerId);
		customerProcess.delete(customerId);
		return Map.of("isSuccess", true);
	}
	

}
