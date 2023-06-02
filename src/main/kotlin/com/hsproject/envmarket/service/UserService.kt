package com.hsproject.envmarket.service

import com.hsproject.envmarket.oauth.User
import com.hsproject.envmarket.repository.UserRepository
import com.hsproject.envmarket.util.JwtProvider
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import javax.persistence.EntityManagerFactory

@Service
class UserService(private val userRepository: UserRepository,
                  private val passwordEncoder: PasswordEncoder,
                  private val jwtProvider: JwtProvider,
                  private val authenticationManager: AuthenticationManager
) {
    fun signUp(email: String, password: String, username: String): User {
        val encodedPassword = passwordEncoder.encode(password)
        val user = User(email = email, password = encodedPassword, userName = username)
        return userRepository.save(user)
    }

    fun login(email: String, password: String): String {
        val authentication = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(email, password)
        )

        // 토큰 생성
        return jwtProvider.generateToken(authentication)
    }

}
