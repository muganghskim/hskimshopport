package com.hsproject.envmarket.service

import com.hsproject.envmarket.oauth.RoleName
import com.hsproject.envmarket.oauth.User
import com.hsproject.envmarket.repository.UserRepository
import com.hsproject.envmarket.util.JwtProvider
import lombok.extern.slf4j.Slf4j
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import java.security.Principal

@Slf4j
@Service
class UserService(private val userRepository: UserRepository,
                  private val passwordEncoder: PasswordEncoder,
                  private val jwtProvider: JwtProvider,
                  private val authenticationManager: AuthenticationManager
) {
    val logger: Logger = LoggerFactory.getLogger(UserService::class.java)
    fun signUp(email: String, password: String, username: String): User {
        val encodedPassword = passwordEncoder.encode(password)
        val user = User(email = email, password = encodedPassword, userName = username)
        return userRepository.save(user)
    }

    fun getUserByEmail(email: String): User {
        return userRepository.findByEmail(email)
    }


    fun login(email: String, password: String): String {
        val authentication = authenticationManager.authenticate(UsernamePasswordAuthenticationToken(email, password))
        logger.info("인증: $authentication")

        SecurityContextHolder.getContext().authentication = authentication
        val token = jwtProvider.generateToken(authentication)
        logger.info("토큰 생성: $token")

        // 사용자의 역할 확인
        val userDetails = authentication.principal as UserDetails
        val roles = userDetails.authorities.map { it.authority }
        val isAdmin = roles.contains("ROLE_${RoleName.ADMIN}")

        if (isAdmin) {
            logger.info("관리자 권한 사용자")
        } else {
            logger.info("일반 사용자 권한")
        }

        return token
    }



}
