package com.shop.process.user;

import com.shop.dto.*;
import com.shop.entity.Customer;
import com.shop.provider.JwtProvider;
import com.shop.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthProcessImpl implements AuthProcess {

	private final CustomerRepository customerRepository;
	private final JwtProvider jwtProvider;

	// 비밀번호 암호화를 위한 인코더
	private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	// 고객 회원가입
	@Override
	public ResponseEntity<? super SignUpResponseDto> signUp(SignUpRequestDto dto) {
		try {
			String id = dto.getCustomerShopid(); // 클라이언트 입력받은 아이디
			boolean existedId = customerRepository.existsByCustomerShopid(id); // 아이디 중복 체크
			if (existedId) return SignUpResponseDto.duplicateId(); // 아이디 중복 시 응답

			String email = dto.getCustomerMail(); // 클라이언트 입력받은 이메일
			boolean existedEmail = customerRepository.existsByCustomerMail(email); // 이메일 중복 체크
			if (existedEmail) return SignUpResponseDto.duplicateEmail(); // 이메일 중복 시 응답

			String tel = dto.getCustomerTel(); // 클라이언트 입력받은 번호
			boolean existedTel = customerRepository.existsByCustomerTel(tel);// 번호 중복 체크
			if (existedTel) return SignUpResponseDto.duplicatePhone(); // 번호 중복 시 응답

			String password = dto.getCustomerShoppw(); // 클라이언트 입력 패스워드
			String encodedPassword = passwordEncoder.encode(password); // 암호화된 패스워드로 변환
			dto.setCustomerShoppw(encodedPassword); // 암호화된 패스워드 넣어줌

			// SignUpRequestDto로 받아온 데이터 저장
			Customer customer = new Customer();
			customer.setCustomerShopid(dto.getCustomerShopid());
			customer.setCustomerShoppw(encodedPassword);
			customer.setCustomerName(dto.getCustomerName());

			customer.setCustomerTel(dto.getCustomerTel());
			customer.setCustomerMail(dto.getCustomerMail());
			customer.setCustomerGender(dto.getCustomerGender());
			customer.setCustomerPostcode(dto.getCustomerPostcode());
			customer.setCustomerAddr1(dto.getCustomerAddr1());
			customer.setCustomerAddr2(dto.getCustomerAddr2());
			customerRepository.save(customer);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseDto.databaseError(); // 에러 시 데이터베이스 오류 응답
		}
		return SignUpResponseDto.success(); // 성공 응답
	}

	// 통합 로그인 로직
	@Override
	public ResponseEntity<? super LoginRepsonseDto> login(LoginRequestDto dto) {
		String token = null;

		try {
			String id = dto.getId(); // 클라이언트 입력 아이디
			String password = dto.getPassword(); // 클라이언트 입력 비밀번호

			// 1. 고객 계정 확인
			Customer customer = customerRepository.findByCustomerShopid(id);
			if (customer != null) {
				boolean isMatched = passwordEncoder.matches(password, customer.getCustomerShoppw());
				if (!isMatched) {
					return LoginRepsonseDto.loginFailed();
				}
				token = jwtProvider.create(id);
				ResponseCookie cookie = createJwtCookie(token);
				return ResponseEntity.ok()
						.header(HttpHeaders.SET_COOKIE, cookie.toString())
						.body(LoginRepsonseDto.success(token));
			}

			// 2. 고객 계정을 찾을 수 없는 경우
			return LoginRepsonseDto.loginFailed();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseDto.databaseError(); // 데이터베이스 오류
		}
	}

	private ResponseCookie createJwtCookie(String token) {
		return ResponseCookie.from("accessToken", token)
				.httpOnly(true)   // 클라이언트 스크립트에서 접근 불가
				.secure(true)     // HTTPS에서만 전송
				.path("/")        // 쿠키의 유효 경로
				.maxAge(3600)     // 만료 시간 (1시간)
				.sameSite("None") // 크로스 도메인 쿠키 전송 허용
				.build();
	}
}
