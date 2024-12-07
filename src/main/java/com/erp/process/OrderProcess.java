package com.erp.process;

import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.erp.dto.OrderDto;
import com.erp.dto.OrderDtoWithNo;
import com.erp.repository.OrderRepository;

@Repository
public class OrderProcess {
	private OrderRepository orderRepository;
	public OrderProcess(OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}
	public List<OrderDtoWithNo> getAllOrderList(){
	    return orderRepository.findAll().stream()
	    		.map(OrderDtoWithNo::fromEntity)
	    		.toList();
	}
	public List<OrderDtoWithNo> getBranchOrders(String branchCode){
	    return orderRepository.getBranchOrders(branchCode).stream()
	    		.map(OrderDtoWithNo::fromEntity)
	    		.toList();
	}
	@Transactional
	public void insert(OrderDto dto) {
		orderRepository.save(OrderDto.toEntity(dto));
	}
}