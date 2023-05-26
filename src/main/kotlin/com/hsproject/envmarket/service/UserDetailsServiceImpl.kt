package com.hsproject.envmarket.service

import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl : UserDetailsService {
    override fun loadUserByUsername(username: String?): UserDetails {
        // 본 예시에서는 하드코딩된 사용자 정보를 사용하며, 실제 구현에서는 데이터베이스 등에서 사용자 정보를 불러와야 합니다.
        return User.withUsername("testUser")
                .password("testPassword")
                .roles("USER")
                .build()
    }
}
