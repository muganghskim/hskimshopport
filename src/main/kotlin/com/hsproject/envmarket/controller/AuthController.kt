package com.hsproject.envmarket.controller

import com.hsproject.envmarket.oauth.User
import com.hsproject.envmarket.service.UserService
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(
        private val userService: UserService,
        private val authenticationManager: AuthenticationManager
) {
    data class SignUpRequest(val email: String, val password: String, val username: String)
    data class LoginRequest(val email: String, val password: String)

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    fun signUp(@RequestBody signUpRequest: SignUpRequest): User {
        return userService.signUp(signUpRequest.email, signUpRequest.password, signUpRequest.username)
    }

    @PostMapping("/login")
    fun login(@RequestBody loginRequest: LoginRequest): Authentication {
        val authentication = authenticationManager.authenticate(UsernamePasswordAuthenticationToken(loginRequest.email, loginRequest.password))
        SecurityContextHolder.getContext().authentication = authentication
        return authentication
    }

    @PostMapping("/logout")
    fun logout(request: HttpServletRequest, response: HttpServletResponse) {
        SecurityContextHolder.clearContext()
        request.logout()
        response.status = HttpServletResponse.SC_OK
    }
}
