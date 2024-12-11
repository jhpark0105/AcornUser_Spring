package com.erp.repository;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.erp.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Integer>{
	//브랜치별 발주 목록 list에 담기
	@Query("SELECT o FROM Order o WHERE o.branch.branchCode=:branchCode")
	List<Order> getBranchOrders(@Param("branchCode") String branchCode);
	
	//지점별&날짜별 발주 목록
	@Query("SELECT o FROM Order o WHERE o.branch.branchCode = :branchCode AND o.ordersApplyDate >= :ordersApplyDate AND o.ordersEndDate <= :ordersEndDate")
	List<Order> getBranchDateOrders(
	    @Param("branchCode") String branchCode,
	    @Param("ordersApplyDate") LocalDate ordersApplyDate,
	    @Param("ordersEndDate") LocalDate ordersEndDate
	);
	
	//발주 상태 변화 (0 > 1)
    @Modifying
    @Transactional
    @Query("UPDATE Order o SET o.ordersStatus = 1 WHERE o.branch.branchCode = :branchCode AND o.ordersApplyDate >= :ordersApplyDate AND o.ordersEndDate <= :ordersEndDate")
    int updateOrderStatus(
        @Param("branchCode") String branchCode,
        @Param("ordersApplyDate") LocalDate ordersApplyDate,
        @Param("ordersEndDate") LocalDate ordersEndDate
    );
}