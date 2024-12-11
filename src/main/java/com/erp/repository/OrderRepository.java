package com.erp.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.erp.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Integer>{
	//브랜치별 발주 목록 list에 담기
	@Query("SELECT o FROM Order o WHERE o.branch.branchCode=:branchCode")
	List<Order> getBranchOrders(@Param("branchCode") String branchCode);
}
