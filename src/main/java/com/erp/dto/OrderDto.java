package com.erp.dto;

import java.time.LocalDate;
import com.erp.entity.Branch;
import com.erp.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
	private Integer ordersEa;
	private Integer ordersPrice;
	private LocalDate ordersApplyDate;
	private LocalDate ordersEndDate;
	private ProductDtoFO productDtoFO;
	private BranchDto branchDto;
	public static OrderDto fromEntity(Order entity) {
		return OrderDto.builder()
				.ordersEa(entity.getOrdersEa())
				.ordersPrice(entity.getOrdersPrice())
				.ordersApplyDate(entity.getOrdersApplyDate())
				.ordersEndDate(entity.getOrdersEndDate())
				.productDtoFO(ProductDtoFO.fromEntity(entity.getProduct()))
				.branchDto(BranchDto.fromEntity(entity.getBranch()))
				.build();
	}
	public static Order toEntity(OrderDto dto) {
		return Order.builder()
				.ordersEa(dto.getOrdersEa())
				.ordersPrice(dto.ordersPrice)
				.ordersApplyDate(dto.getOrdersApplyDate())
				.ordersEndDate(dto.getOrdersEndDate())
				.product(ProductDtoFO.toEntity(dto.productDtoFO))
				.branch(Branch.toEntity(dto.getBranchDto()))
				.build();
	}
}
