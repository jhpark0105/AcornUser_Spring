package com.erp.process.branch;

import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.erp.dto.OrderDto;
import com.erp.repository.OrderRepository;

@Repository
public class OrderProcess {
	private OrderRepository orderRepository;
	public OrderProcess(OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}
	public List<OrderDto> getAllOrderList(){
	    return orderRepository.findAll().stream().map(OrderDto::fromEntity).toList();
	}
	public List<OrderDto> getBranchOrders(String branchCode){
	    return orderRepository.getBranchOrders(branchCode).stream().map(OrderDto::fromEntity).toList();
	}
	@Transactional
	public void insert(OrderDto dto) {
		orderRepository.save(OrderDto.toEntity(dto));
	}
}