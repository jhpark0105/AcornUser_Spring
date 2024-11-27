package com.erp.process;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.erp.entity.Admin;
import com.erp.repository.AdminRepository;

@Service
public class CustomUserDetailsProcess implements UserDetailsService {
	// UserDetailsService : Spring Security에서 사용자의 정보를 가져오는 인터페이스
	@Autowired
	private AdminRepository adminRepository;
	
	@Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Admin 엔티티에서 사용자 정보 조회
        Admin admin = adminRepository.findByAdminId(username);
        
        if (admin == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }

        // UserDetails 객체로 반환
        return User.builder()
                .username(admin.getAdminId())
                .password(admin.getAdminPw())
                .roles(admin.getAdminRole())
                .build();
    }
	
	private Collection<? extends GrantedAuthority> grantedAuthorities(String role) {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role));
    }
}