package com.hsproject.envmarket.service

import com.hsproject.envmarket.repository.UserRepository
import lombok.RequiredArgsConstructor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Service

@Service
@RequiredArgsConstructor
class UserDetailsServiceImpl @Autowired constructor(private val userRepository: UserRepository) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByEmail(username)
                ?: throw UsernameNotFoundException("User not found with email: $username")

//        // 현재 사용자의 권한을 가져옵니다. 필요에 따라 데이터베이스에서 가져오도록 변경하세요.
//        val authorities = mutableListOf(SimpleGrantedAuthority("ROLE_USER"))

        val authorities = user.roles.map { role -> SimpleGrantedAuthority("ROLE_${role.name}") }

        return User
                .withUsername(user.email)
                .password(user.password)
                .authorities(authorities)
                .build()
    }
}

