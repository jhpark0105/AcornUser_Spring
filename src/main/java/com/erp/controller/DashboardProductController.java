package com.erp.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.erp.dto.ProductDto;
import com.erp.process.DashboardProductProcess;

@RestController
@RequestMapping("/dashboard/product")
@CrossOrigin(origins = "http://localhost:3000")
public class DashboardProductController {
    private final DashboardProductProcess dashboardProductProcess;
	public DashboardProductController (DashboardProductProcess dashboardProductProcess) {
		this.dashboardProductProcess = dashboardProductProcess;
	}
	@GetMapping
	public ResponseEntity<Object> getProductList(@RequestParam(value = "page", required = false, defaultValue = "1" ) int page){
		Page<ProductDto> list= dashboardProductProcess.getProductList(page-1);
		int nowPage = list.getNumber()+1;
		int startPage = 1;
		int endPage = list.getTotalPages();
		Map<String, Object> map = new HashMap<>();
		map.put("list", list);
		map.put("nowPage", nowPage);
		map.put("startPage", startPage);
		map.put("endPage", endPage);
		map.put("page", page);
		return ResponseEntity.ok(map);
	}
}
