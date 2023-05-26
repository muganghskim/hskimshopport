package com.hsproject.envmarket.service

import com.hsproject.envmarket.oauth.User
import com.hsproject.envmarket.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository, private val passwordEncoder: PasswordEncoder) {

    fun signUp(email: String, password: String, username: String): User {
        val encodedPassword = passwordEncoder.encode(password)
        val user = User(email = email, password = encodedPassword, userName = username)
        return userRepository.save(user)
    }

}
