package com.shop.dto;

import java.time.LocalDate;
import com.shop.entity.Branch;
import com.shop.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {//orders_no은 자동생성이고 화면에도 노출할 필요가 없어서 제외
	private Integer ordersEa;	//발주 신청한 상품 개수
	private Integer ordersPrice;	//발주 신청한 상품 금액(상품가격*ordersEa)
	private LocalDate ordersApplyDate;	//발주 신청일
	private LocalDate ordersEndDate;	//발주 마감일
	private ProductDtoFO productDtoFO;	//상품 발주를 위한 상품 Dto
	private BranchDto branchDto;	//참조를 위한 BranchDto
    //Entity -> DTO 변환 메서드
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
    //DTO -> Entity 변환 메서드
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
