package com.erp.controller.branch;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.erp.dto.BranchDto;
import com.erp.dto.OrderDto;
import com.erp.dto.ProductBDto;
import com.erp.dto.ProductDtoFO;
import com.erp.process.branch.BranchProcess;
import com.erp.process.branch.OrderProcess;
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
	//branch별 발주 목록 요청
	@GetMapping("/{branchCode}")
	public ResponseEntity<Object> getBranchOrders(@PathVariable("branchCode") String branchCode){
		System.out.println(1);
		List<OrderDto> list= orderProcess.getBranchOrders(branchCode);
		return ResponseEntity.ok(list);
	}
	@PostMapping
	public ResponseEntity<Object> insertOrder(@RequestBody Map<String, Object> req){
		//요청으로 들어온 json타입 데이터을 insert하기 위해 OrderDto에 담는 과정  
		//Order 엔티티의 날짜 타입(LocalDate)로의 캐스팅이 필요하다
		String date1 = (String) req.get("ordersApplyDate");
		LocalDate ordersApplyDate = LocalDate.parse(date1);	//발주 등록일
		String date2 = (String) req.get("ordersEndDate");
		LocalDate ordersEndDate = LocalDate.parse(date2);	//발주 마감일
		/*발주 요청은 각 branch별로 들어오기 때문에 한 번의 발주 요청에서 각 order의 branch정보는 동일하므로
		 *OrderDto.branchDto필드에 넣을 branch정보를 요청에서의 branchCode를 통해 얻는다 
		*/
		BranchDto branchDto= branchProcess.getBranch(req.get("branchCode").toString());
		/*db에서 orders,product테이블이 one-to-one관계이기 때문에 한 번의 발주요청의 cart에 담겨온 상품 목록을
		 * 반복문을 통해 각각 jpa insert하는 작업이 요구된다.
		 * cart는 배열인데 axios 요청으로 서버로 넘어오면 자동으로 ArrayList타입으로 들어오고
		 * cart의 각 요소(객체)는 자동으로 LinkedHashMap타입으로 전달된다.(아마도 default로 설정된듯하다)
		 * cart의 각 요소를 db에 저장만 하면 되므로 stream을 사용할 수 있는 그릇인
		 * ArrayList<LinkedHashMap<String, Object>> cartArray를 생성한다.
		 * */
		ArrayList<LinkedHashMap<String, Object>> cartArray=(ArrayList<LinkedHashMap<String, Object>>)req.get("cart");
		//각 요소별로 insert작업만 진행하고 따로 반환할 필요가 없으니 forEach를 사용한다. 
		cartArray.stream()
			.forEach(product-> {
				//cart의 각 요소(객체)를 담아줄 그릇인 OrderDto를 생성한다.
				OrderDto orderDto = new OrderDto();				
				orderDto.setOrdersEa((Integer)product.get("ordersEa"));
				orderDto.setOrdersPrice((Integer)product.get("ordersPrice"));
				orderDto.setOrdersApplyDate(ordersApplyDate);
				orderDto.setOrdersEndDate(ordersEndDate);
				//각 요소의 productDtoFO를 orderDto의 productDtoFO에 넣기 위해 ProductDtoFO생성
				LinkedHashMap map = (LinkedHashMap)product.get("productDtoFO");				
				ProductDtoFO productDtoFO = new ProductDtoFO();
				productDtoFO.setProductCode(map.get("productCode").toString());
				productDtoFO.setProductName(map.get("productName").toString());
				productDtoFO.setProductPrice((Integer)map.get("productPrice"));
				productDtoFO.setProductEa((Integer)map.get("productEa"));
				//productDtoFO의 productBDto에 데이터를 넣기 위해 ProductBDto생성
				ProductBDto productBDto = new ProductBDto();
				LinkedHashMap map2 = (LinkedHashMap)map.get("productBDto");
				productBDto.setProductBCode(map2.get("productBCode").toString());
				productBDto.setProductBName(map2.get("productBName").toString());
				//productDtoFO의 productBDto에 ProductBDto 할당
				productDtoFO.setProductBDto(productBDto);
				//orderDto의 productDtoFO에 productDtoFO 할당
				orderDto.setProductDtoFO(productDtoFO);
				//orderDto의 branchDto에 branchDto 할당
				orderDto.setBranchDto(branchDto);
				//insert 실행
				orderProcess.insert(orderDto);
			});
		return ResponseEntity.ok().build();
	}
	
}