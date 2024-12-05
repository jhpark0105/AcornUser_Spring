package com.erp.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.erp.dto.BranchDto;
import com.erp.dto.OrderDto;
import com.erp.dto.OrderDtoWithNo;
import com.erp.dto.ProductBDto;
import com.erp.dto.ProductDtoFO;
import com.erp.process.BranchProcess;
import com.erp.process.OrderProcess;
@RestController
@RequestMapping("/order")
@CrossOrigin(origins = "http://localhost:3000")
public class OrderController {
	private OrderProcess orderProcess;
	private BranchProcess branchProcess;
	public OrderController(OrderProcess orderProcess,BranchProcess branchProcess) {
		this.orderProcess = orderProcess;
		this.branchProcess=branchProcess;
	}	
	@GetMapping("/{branchCode}")
	public ResponseEntity<Object> getAllOrderList(@PathVariable("branchCode") String branchCode, @RequestParam(value = "page", required = false, defaultValue = "1" ) int page){
		Page<OrderDtoWithNo> list= orderProcess.getBranchOrders(page-1, branchCode);
		int nowPage = list.getNumber()+1;
		int startPage = 1;
		int endPage = list.getTotalPages();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		map.put("nowPage", nowPage);
		map.put("startPage", startPage);
		map.put("endPage", endPage);
		map.put("page", page);
		return ResponseEntity.ok(map);
	}
	@PostMapping
	public ResponseEntity<Object> insertOrder(@RequestBody Map<String, Object> req){
		String date1 = (String) req.get("ordersApplyDate");
		LocalDate ordersApplyDate = LocalDate.parse(date1);
		String date2 = (String) req.get("ordersEndDate");
		LocalDate ordersEndDate = LocalDate.parse(date2);
		BranchDto branchDto= branchProcess.getBranch(req.get("branchCode").toString());
		ArrayList<LinkedHashMap<String, Object>> cartArray=(ArrayList<LinkedHashMap<String, Object>>)req.get("cart");
		cartArray.stream()
			.forEach(product-> {
				OrderDto orderDto = new OrderDto();				
				orderDto.setOrdersEa((Integer)product.get("ordersEa"));
				orderDto.setOrdersPrice((Integer)product.get("ordersPrice"));
				orderDto.setOrdersApplyDate(ordersApplyDate);
				orderDto.setOrdersEndDate(ordersEndDate);
				LinkedHashMap map = (LinkedHashMap)product.get("productDtoFO");				
				ProductDtoFO productDtoFO = new ProductDtoFO();
				productDtoFO.setProductCode(map.get("productCode").toString());
				productDtoFO.setProductName(map.get("productName").toString());
				productDtoFO.setProductPrice((Integer)map.get("productPrice"));
				productDtoFO.setProductEa((Integer)map.get("productEa"));
				ProductBDto productBDto = new ProductBDto();
				LinkedHashMap map2 = (LinkedHashMap)map.get("productBDto");
				productBDto.setProductBCode(map2.get("productBCode").toString());
				productBDto.setProductBName(map2.get("productBName").toString());
				productDtoFO.setProductBDto(productBDto);
				orderDto.setProductDtoFO(productDtoFO);
				orderDto.setBranchDto(branchDto);
				System.out.println(orderDto);
				orderProcess.insert(orderDto);
			});
		return ResponseEntity.ok().build();
	}
	
}