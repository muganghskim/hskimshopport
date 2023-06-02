package com.hsproject.envmarket.controller

import com.hsproject.envmarket.oauth.User
import com.hsproject.envmarket.service.UserService
import com.hsproject.envmarket.util.JwtProvider
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
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

//    @PostMapping("/login")
//    fun login(@RequestBody loginRequest: LoginRequest): Authentication {
//        val authentication = authenticationManager.authenticate(UsernamePasswordAuthenticationToken(loginRequest.email, loginRequest.password))
//        SecurityContextHolder.getContext().authentication = authentication
//        return authentication
//    }
    @PostMapping("/login")
    fun Login(@RequestBody loginRequest: LoginRequest): ResponseEntity<String> {
        return try {
            val token = userService.login(loginRequest.email, loginRequest.password)

            // 토큰 반환
            ResponseEntity.ok(token)
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
        }
    }



    @PostMapping("/logout")
    fun logout(request: HttpServletRequest, response: HttpServletResponse) {
        SecurityContextHolder.clearContext()
        request.logout()
        response.status = HttpServletResponse.SC_OK
    }
}
