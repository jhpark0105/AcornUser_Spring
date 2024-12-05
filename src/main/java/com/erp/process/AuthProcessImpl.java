package com.erp.process;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.erp.dto.BranchSignUpRequestDto;
import com.erp.dto.LoginRepsonseDto;
import com.erp.dto.LoginRequestDto;
import com.erp.dto.ResponseDto;
import com.erp.dto.SignUpRequestDto;
import com.erp.dto.SignUpResponseDto;
import com.erp.entity.Admin;
import com.erp.entity.Branch;
import com.erp.provider.JwtProvider;
import com.erp.repository.AdminRepository;
import com.erp.repository.BranchRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthProcessImpl implements AuthProcess {
	
	private final AdminRepository adminRepository;
	private final BranchRepository branchRepository;
	private final JwtProvider jwtProvider;
	
	private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	@Override
	public ResponseEntity<? super SignUpResponseDto> signUp(SignUpRequestDto dto) {
		
		try {

			String id = dto.getAdminId();
			boolean existedId = adminRepository.existsByAdminId(id);
			if(existedId) return SignUpResponseDto.duplicateId();
			
			String email = dto.getAdminEmail();
			boolean existedEmail = adminRepository.existsByAdminEmail(email);
			if(existedEmail) return SignUpResponseDto.duplicateEmail();
			
			String phone = dto.getAdminPhone();
			boolean existedPhone = adminRepository.existsByAdminPhone(phone);
			if(existedPhone) return SignUpResponseDto.duplicatePhone();
			
			String password = dto.getAdminPw(); // 패스워드
			String encodedPassword = passwordEncoder.encode(password); // 암호화된 패스워드로 변환
			dto.setAdminPw(encodedPassword);
			
			Admin admin = new Admin();
			admin.setAdminId(dto.getAdminId());
			admin.setAdminPw(encodedPassword);
			admin.setAdminRole("ROLE_ADMIN");
			admin.setAdminName(dto.getAdminName());
			admin.setAdminBirth(dto.getAdminBirth());
			admin.setAdminPhone(dto.getAdminPhone());
			admin.setAdminEmail(dto.getAdminEmail());
			admin.setAdminPostcode(dto.getAdminPostcode());
			admin.setAdminAddress1(dto.getAdminAddress1());
			admin.setAdminAddress2(dto.getAdminAddress2());
			admin.setAdminTerm1(dto.getAdminTerm1());
			admin.setAdminTerm2(dto.getAdminTerm2());
			admin.setAdminTerm3(dto.getAdminTerm3());
			adminRepository.save(admin);
			
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseDto.databaseError();
		}
		return SignUpResponseDto.success();
	}
	
	@Override
	public ResponseEntity<? super LoginRepsonseDto> login(LoginRequestDto dto) {
		
		String token = null;
		
		try {
			String id = dto.getId();
			Admin admin = adminRepository.findByAdminId(id);
			if(admin == null) return LoginRepsonseDto.loginFailed();
			
			String password = dto.getPassword();
			String encodedPassword = admin.getAdminPw();
			boolean isMatched = passwordEncoder.matches(password, encodedPassword); // 암호화된 패스워드와 입력 패스워드를 검증
			if(!isMatched) return LoginRepsonseDto.loginFailed();
			
			token = jwtProvider.create(id);
			
			 // JWT를 쿠키에 설정
            ResponseCookie cookie = ResponseCookie.from("accessToken", token)
                    .httpOnly(true)    // 클라이언트 JS에서 쿠키를 읽을 수 없게 설정
                    .secure(true)      // HTTPS에서만 쿠키 전송(true), HTTP도 가능(false)
                    .path("/")         // 쿠키가 유효한 경로 설정
                    .maxAge(3600)      // 쿠키 만료 시간 (초 단위)
                    .sameSite("None") // CSRF 보호를 위한 SameSite 설정,  크로스 도메인에서 쿠키 전송 허용(None), 동일 도메인(Strict), 상호작용이 필요한 상황에만 쿠키 전송(Lax)
                    .build();
            
            System.out.println(cookie);

            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, cookie.toString())  // 쿠키를 응답 헤더에 포함
                    .body(LoginRepsonseDto.success(token));  // 로그인 성공 응답 반환
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseDto.databaseError();
		}
		//return LoginRepsonseDto.success(token);
	}
	// branch 등록
	@Override
	public ResponseEntity<? super SignUpResponseDto> branchSignUp(BranchSignUpRequestDto dto) {
		try {
			
			String password = dto.getBranchPw(); // 패스워드
			String encodedPassword = passwordEncoder.encode(password); // 암호화된 패스워드로 변환
			dto.setBranchPw(encodedPassword);
			
			Branch branch = new Branch();
			branch.setBranchCode(dto.getBranchCode());
			branch.setBranchPw(encodedPassword);
			branch.setBranchName(dto.getBranchName());
			branch.setBranchAddress(dto.getBranchAddress());
			branch.setBranchTel(dto.getBranchTel());
			branch.setBranchNote(dto.getBranchNote());
			branch.setBranchRole("ROLE_BRANCH");
			branchRepository.save(branch);
			
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseDto.databaseError();
		}
		return SignUpResponseDto.success();
	}
	
	// branch 로그인
	@Override
	public ResponseEntity<? super LoginRepsonseDto> branchLogin(LoginRequestDto dto) {
		String token = null;
		
		try {
			String id = dto.getId();
			Branch branch = branchRepository.findByBranchCode(id);
			if(branch == null) return LoginRepsonseDto.loginFailed();
			
			String password = dto.getPassword();
			String encodedPassword = branch.getBranchPw();
			boolean isMatched = passwordEncoder.matches(password, encodedPassword); // 암호화된 패스워드와 입력 패스워드를 검증
			if(!isMatched) return LoginRepsonseDto.loginFailed();
			
			token = jwtProvider.create(id);
			
			 // JWT를 쿠키에 설정
            ResponseCookie cookie = ResponseCookie.from("accessToken", token)
                    .httpOnly(true)  // 클라이언트 JS에서 쿠키를 읽을 수 없게 설정
                    .secure(true)    // HTTPS에서만 쿠키 전송(true), HTTP도 가능(false)
                    .path("/")       // 쿠키가 유효한 경로 설정
                    .maxAge(3600)    // 쿠키 만료 시간 (초 단위)
                    .sameSite("None") // CSRF 보호를 위한 SameSite 설정,  크로스 도메인에서 쿠키 전송 허용(None), 동일 도메인(Strict), 상호작용이 필요한 상황에만 쿠키 전송(Lax)
                    .build();
            
            System.out.println(cookie);

            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, cookie.toString())  // 쿠키를 응답 헤더에 포함
                    .body(LoginRepsonseDto.success(token));  // 로그인 성공 응답 반환
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseDto.databaseError();
		}
	}
}
