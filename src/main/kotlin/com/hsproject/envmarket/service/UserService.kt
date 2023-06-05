package com.hsproject.envmarket.service

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
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import javax.naming.AuthenticationException

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


    fun login(email: String, password: String): String {
        val authentication = authenticationManager.authenticate(UsernamePasswordAuthenticationToken(email, password))
        logger.info("인증: $authentication")

        SecurityContextHolder.getContext().authentication = authentication
        val token = jwtProvider.generateToken(authentication)
        logger.info("토큰 생성: $token")

        return token
    }


}
