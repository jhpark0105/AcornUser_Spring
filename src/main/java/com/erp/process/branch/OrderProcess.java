package com.erp.process.branch;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
	public Page<OrderDtoWithNo> getAllOrderList(int page){
		Sort sort = Sort.by(Sort.Order.asc("branch.branchCode"));
		Pageable pageable = PageRequest.of(page, 10, sort);
	    return orderRepository.findAll(pageable).map(OrderDtoWithNo::fromEntity);
	}
	public Page<OrderDtoWithNo> getBranchOrders(int page,String branchCode){
		Sort sort = Sort.by(Sort.Order.desc("ordersApplyDate"));
		Pageable pageable = PageRequest.of(page, 10, sort);
	    return orderRepository.getBranchOrders(pageable, branchCode).map(OrderDtoWithNo::fromEntity);
	}
	@Transactional
	public void insert(OrderDto dto) {
		orderRepository.save(OrderDto.toEntity(dto));
	}
}