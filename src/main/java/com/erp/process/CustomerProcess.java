package com.erp.process;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.erp.dto.CustomerDto;
import com.erp.entity.Customer;
import com.erp.repository.CustomerRepository;

@Service
public class CustomerProcess {
	@Autowired
	private CustomerRepository customerRepository;
	
	// customer 전체
	public List<CustomerDto> getCustomerAll(){
		List<Customer> customers = customerRepository.findAll();
		return customers.stream()
				.map(CustomerDto::fromEntity)
				.collect(Collectors.toList());
	} 
	

	// 이름으로 고객 조회
	public CustomerDto getCustomerByName(String customerName) {
	    List<Customer> customers = customerRepository.findByCustomerName(customerName);
	    
	    
	    return CustomerDto.fromEntity(customers.get(0));  // 첫 번째 고객만 반환
	}

	
	// 수정/삭제를 위한 레코드 읽기
	public Customer getData(int customerId) {
		return customerRepository.findById(customerId)
				.orElse(null);
	}
	
	// 추가(id 자동증가)
	public String insert(CustomerDto customerDto) {
	    Customer customer = customerDto.toEntity();
	    // 고객 데이터 저장
	    customerRepository.save(customer);
	    return "isSuccess";
	}

	
	// 수정
	public String update(CustomerDto customerDto) {
		try {
			Customer customer = customerDto.toEntity();
			
			customerRepository.save(customer);
		            
			return "success";
		} catch (Exception e) {
			return "수정 작업 오류:" + e.getMessage();
		}
	}
	

	// 삭제
	public String delete(int customerId) {
		try {
			customerRepository.deleteById(customerId);
			return "success";
		} catch (Exception e) {
			return "삭제 작업 오류:" + e.getMessage();
		}
	}

}

