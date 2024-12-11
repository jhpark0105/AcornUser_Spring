package com.erp.process.branch;

import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.erp.dto.OrderDto;
import com.erp.repository.OrderRepository;

@Repository
public class OrderProcess {
	private OrderRepository orderRepository;
	
	//OrderRepository 의존성 주입
	public OrderProcess(OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}
	
	//admin용 전체 발주 리스트 불러오기
	public List<OrderDto> getAllOrderList(){
	    return orderRepository.findAll().stream().map(OrderDto::fromEntity).toList();
	}
	
	//branch별 전체 발주 리스트 불러오기
	public List<OrderDto> getBranchOrders(String branchCode){
	    return orderRepository.getBranchOrders(branchCode).stream().map(OrderDto::fromEntity).toList();
	}
	
	//지점별&날짜별 발주 목록
	public List<OrderDto> getBranchDateOrders(String branchCode, LocalDate ordersApplyDate, LocalDate ordersEndDate) {
		return orderRepository.getBranchDateOrders(branchCode, ordersApplyDate, ordersEndDate)
				.stream().map(OrderDto::fromEntity).toList();
	}
	
	//발주 신청된 상품을 Order객체에 담아 영속성컨텍스트에 저장
	@Transactional
	public void insert(OrderDto dto) {
		orderRepository.save(OrderDto.toEntity(dto));
	}
	
	//발주 상태 변화(0 > 1)
	@Transactional
	public int changeOrderStatus(String branchCode, LocalDate applyDate, LocalDate endDate) {
        return orderRepository.updateOrderStatus(branchCode, applyDate, endDate);
    }
}