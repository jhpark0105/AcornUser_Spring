//package com.erp.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.erp.dto.ManagerDto;
//import com.erp.entity.Manager;
//import com.erp.process.ManagerProcess;
//
//@RestController
//@CrossOrigin(origins = "http://localhost:3000")
//@RequestMapping("/manager/mypage")
//public class ManagerController {
//	@Autowired
//	private ManagerProcess managerProcess;
//	
//	// branchCode로 매니저 조회
//	@GetMapping("/{branchCode}")
//	public ManagerDto getManagersByBranchCode(@PathVariable("branchCode") String branchCode) {
//		Manager manager = managerProcess.getManager(branchCode);
//        return ManagerDto.fromEntity(manager);
//	}
//    
//	// 수정
//	@PutMapping("/update/{branchCode}")
//	public String update(@PathVariable("branchCode") String branchCode, @RequestBody ManagerDto dto) {
//		dto.setBranchCode(branchCode);
//	    return managerProcess.update(dto);
//	}
//}

package com.erp.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.erp.dto.ManagerDto;
import com.erp.entity.Manager;
import com.erp.process.ManagerProcess;
import com.erp.provider.JwtProvider;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/manager/mypage")
public class ManagerController {
    @Autowired
    private ManagerProcess managerProcess;

    @Autowired
    private JwtProvider jwtProvider;

    // 쿠키에서 branchCode 추출하여 매니저 조회
    @GetMapping("")
    public ManagerDto getManagerByToken(@CookieValue(name = "accessToken") String token) {
        // 토큰에서 branchCode(id) 추출
        String branchCode = jwtProvider.validate(token);
        
        // 추출한 branchCode로 매니저 조회
        Manager manager = managerProcess.getManager(branchCode);
        return ManagerDto.fromEntity(manager);
    }

    // 수정
    @PutMapping("/update")
    public String update(@CookieValue(name = "accessToken") String token, @RequestBody ManagerDto dto) {
        // 토큰에서 branchCode 추출
        String branchCode = jwtProvider.validate(token);
        dto.setBranchCode(branchCode);
        return managerProcess.update(dto);
    }
}