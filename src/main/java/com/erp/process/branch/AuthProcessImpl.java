package com.erp.process.branch;

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
	
	// 비밀번호 암호화를 위한 인코더
	private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	// 어드민 회원가입
	@Override
	public ResponseEntity<? super SignUpResponseDto> signUp(SignUpRequestDto dto) {
		
		try {
			
			String id = dto.getAdminId(); // 클라이언트 입력받은 아이디
			boolean existedId = adminRepository.existsByAdminId(id); // 아이디 중복 체크
			if(existedId) return SignUpResponseDto.duplicateId(); // 아이디 중복 시 응답
			
			String email = dto.getAdminEmail(); // 클라이언트 입력받은 이메일
			boolean existedEmail = adminRepository.existsByAdminEmail(email); // 이메일 중복 체크
			if(existedEmail) return SignUpResponseDto.duplicateEmail(); // 이메일 중복 시 응답
			
			String phone = dto.getAdminPhone(); // 클라이언트 입력받은 번호
			boolean existedPhone = adminRepository.existsByAdminPhone(phone); // 번호 중복 체크
			if(existedPhone) return SignUpResponseDto.duplicatePhone(); // 번호 중복 시 응답
			
			String password = dto.getAdminPw(); // 클라이언트 입력 패스워드
			String encodedPassword = passwordEncoder.encode(password); // 암호화된 패스워드로 변환
			dto.setAdminPw(encodedPassword); // 암호화된 패스워드 넣어줌
			
			// SignUpRequestDto로 받아온 데이터 저장
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
			return ResponseDto.databaseError(); // 에러 시 데이터베이스 오류 응답
		}
		return SignUpResponseDto.success(); // 성공 응답
	}
	
	// 어드민 로그인
	@Override
	public ResponseEntity<? super LoginRepsonseDto> login(LoginRequestDto dto) {
		
		String token = null; // JWT 토큰 초기화
		
		try {
			String id = dto.getId(); // 클라이언트 입력 아이디
			Admin admin = adminRepository.findByAdminId(id); // 해당 어드민 계정 정보 조회
			if(admin == null) return LoginRepsonseDto.loginFailed(); // 계정 없을 시 로그인 실패 응답
			
			String password = dto.getPassword(); // 클라이언트 입력 패스워드
			String encodedPassword = admin.getAdminPw(); // 데이터베이스에 저장되어있는 암호화된 패스워드
			boolean isMatched = passwordEncoder.matches(password, encodedPassword); // 암호화된 패스워드와 입력 패스워드를 검증
			if(!isMatched) return LoginRepsonseDto.loginFailed(); // 패스워드가 같지 않다면 로그인 실패 응답
			
			token = jwtProvider.create(id); // 로그인 성공 시 JWT 토큰 생성
			
			 // JWT를 쿠키에 설정
            ResponseCookie cookie = ResponseCookie.from("accessToken", token)
                    .httpOnly(true)    // 클라이언트 스크립트에서 접근 불가
                    .secure(true)      // HTTPS에서만 쿠키 전송(true), HTTP도 가능(false)
                    .path("/")         // 쿠키가 유효한 경로 설정
                    .maxAge(3600)      // 쿠키 만료 시간 (초 단위)
                    .sameSite("None")  // CSRF 보호를 위한 SameSite 설정, 크로스 도메인에서 쿠키 전송 허용(None), 동일 도메인(Strict), 상호작용이 필요한 상황에만 쿠키 전송(Lax)
                    .build();
            
            System.out.println(cookie); // 쿠키 정보 로그

            return ResponseEntity.ok() // 로그인 성공 시 응답
                    .header(HttpHeaders.SET_COOKIE, cookie.toString())  // 쿠키를 응답 헤더에 포함
                    .body(LoginRepsonseDto.success(token));  // 로그인 성공 응답 반환
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseDto.databaseError(); // 실패 시 데이터베이스 오류 응답
		}
	}
	
	// 브랜치 등록
	@Override
	public ResponseEntity<? super SignUpResponseDto> branchSignUp(BranchSignUpRequestDto dto) {
		try {
			
			String code = dto.getBranchCode(); // 클라이언트 입력 코드
			boolean existedId = branchRepository.existsByBranchCode(code); // 코드 중복 체크
			if(existedId) return SignUpResponseDto.duplicateId(); // 코드 중복 시 응답
			
			String password = dto.getBranchPw(); // 클라이언트 입력 패스워드
			String encodedPassword = passwordEncoder.encode(password); // 암호화된 패스워드로 변환
			dto.setBranchPw(encodedPassword); // 암호화된 패스워드 넣어줌
			
			// SignUpRequestDto로 받아온 데이터 저장
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
			return ResponseDto.databaseError(); // 에러 시 데이터베이스 오류 응답
		}
		return SignUpResponseDto.success(); // 성공 응답
	}
	
	// 브랜치 로그인
	@Override
	public ResponseEntity<? super LoginRepsonseDto> branchLogin(LoginRequestDto dto) {
		String token = null;
		
		try {
			String id = dto.getId(); // 클라이언트 입력 아이디
			Branch branch = branchRepository.findByBranchCode(id); // 해당 브랜치 정보 조회
			if(branch == null) return LoginRepsonseDto.loginFailed(); // 계정 없을 시 로그인 실패 응답
			
			String password = dto.getPassword(); // 클라이언트 입력 패스워드
			String encodedPassword = branch.getBranchPw(); // 데이터베이스에 저장되어있는 암호화된 패스워드
			boolean isMatched = passwordEncoder.matches(password, encodedPassword); // 암호화된 패스워드와 입력 패스워드를 검증
			if(!isMatched) return LoginRepsonseDto.loginFailed(); // 패스워드가 같지 않다면 로그인 실패 응답
			
			token = jwtProvider.create(id); // 로그인 성공 시 JWT 토큰 생성
			
			 // JWT를 쿠키에 설정
            ResponseCookie cookie = ResponseCookie.from("accessToken", token)
                    .httpOnly(true)   // 클라이언트 스크립트에서 접근 불가
                    .secure(true)     // HTTPS에서만 쿠키 전송(true), HTTP도 가능(false)
                    .path("/")        // 쿠키가 유효한 경로 설정
                    .maxAge(86400)     // 쿠키 만료 시간 (초 단위) (3600에서 24시간으로 바꿈)
                    .sameSite("None") // CSRF 보호를 위한 SameSite 설정, 크로스 도메인에서 쿠키 전송 허용(None), 동일 도메인(Strict), 상호작용이 필요한 상황에만 쿠키 전송(Lax)
                    .build();
            
            System.out.println(cookie); // 쿠키 정보 로그

            return ResponseEntity.ok() // 로그인 성공 시 응답
                    .header(HttpHeaders.SET_COOKIE, cookie.toString())  // 쿠키를 응답 헤더에 포함
                    .body(LoginRepsonseDto.success(token));  // 로그인 성공 응답 반환
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseDto.databaseError(); // 실패 시 데이터베이스 오류 응답
		}
	}
}
