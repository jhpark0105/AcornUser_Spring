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
			if (existedId) return SignUpResponseDto.duplicateId(); // 아이디 중복 시 응답

			String email = dto.getAdminEmail(); // 클라이언트 입력받은 이메일
			boolean existedEmail = adminRepository.existsByAdminEmail(email); // 이메일 중복 체크
			if (existedEmail) return SignUpResponseDto.duplicateEmail(); // 이메일 중복 시 응답

			String phone = dto.getAdminPhone(); // 클라이언트 입력받은 번호
			boolean existedPhone = adminRepository.existsByAdminPhone(phone); // 번호 중복 체크
			if (existedPhone) return SignUpResponseDto.duplicatePhone(); // 번호 중복 시 응답

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

	// 통합 로그인 로직
	@Override
	public ResponseEntity<? super LoginRepsonseDto> login(LoginRequestDto dto) {
		String token = null;

		try {
			String id = dto.getId(); // 클라이언트 입력 아이디
			String password = dto.getPassword(); // 클라이언트 입력 비밀번호

			// 1. 어드민 계정 확인
			Admin admin = adminRepository.findByAdminId(id);
			if (admin != null) {
				boolean isMatched = passwordEncoder.matches(password, admin.getAdminPw());
				if (!isMatched) {
					return LoginRepsonseDto.loginFailed();
				}
				token = jwtProvider.create(id);
				ResponseCookie cookie = createJwtCookie(token);
				return ResponseEntity.ok()
						.header(HttpHeaders.SET_COOKIE, cookie.toString())
						.body(LoginRepsonseDto.success(token));
			}

			// 2. 브랜치 계정 확인
			Branch branch = branchRepository.findByBranchCode(id);
			if (branch != null) {
				boolean isMatched = passwordEncoder.matches(password, branch.getBranchPw());
				if (!isMatched) {
					return LoginRepsonseDto.loginFailed();
				}
				token = jwtProvider.create(id);
				ResponseCookie cookie = createJwtCookie(token);
				return ResponseEntity.ok()
						.header(HttpHeaders.SET_COOKIE, cookie.toString())
						.body(LoginRepsonseDto.success(token));
			}

			// 3. 계정을 찾을 수 없는 경우
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

	// 브랜치 등록
	@Override
	public ResponseEntity<? super SignUpResponseDto> branchSignUp(BranchSignUpRequestDto dto) {
		try {
			String code = dto.getBranchCode(); // 클라이언트 입력 코드
			boolean existedId = branchRepository.existsByBranchCode(code); // 코드 중복 체크
			if (existedId) return SignUpResponseDto.duplicateId(); // 코드 중복 시 응답

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

//	@Override
//	public ResponseEntity<? super LoginRepsonseDto> branchLogin(LoginRequestDto dto) {
//		return null;
//	}
}
