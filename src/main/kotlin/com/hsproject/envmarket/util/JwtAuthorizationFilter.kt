package com.hsproject.envmarket.util

import com.hsproject.envmarket.config.JwtConfiguration
import io.jsonwebtoken.*
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtAuthorizationFilter(
        authenticationManager: AuthenticationManager,
        private val jwtConfig: JwtConfiguration, // JWT 설정을 담은 클래스
        private val userDetailsService: UserDetailsService
) : BasicAuthenticationFilter(authenticationManager) {

    @Throws(IOException::class, ServletException::class)
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        val header = request.getHeader("Authorization")

        if (header == null || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response)
            return
        }

        val authenticationToken = getAuthentication(request)
        if (authenticationToken != null) {
            SecurityContextHolder.getContext().authentication = authenticationToken
        }
        chain.doFilter(request, response)
    }

    private fun getAuthentication(request: HttpServletRequest): UsernamePasswordAuthenticationToken? {
        val token = request.getHeader("Authorization")?.removePrefix("Bearer ")

        if (!token.isNullOrEmpty()) {
            try {
                val claims: Claims = Jwts.parserBuilder()
                        .setSigningKey(jwtConfig.secret) // 설정한 비공개 키를 사용해 토큰 검증
                        .build()
                        .parseClaimsJws(token)
                        .body

                val username = claims["sub"] as String

                if (!username.isNullOrEmpty()) {
                    val userDetails = userDetailsService.loadUserByUsername(username)
                    return UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.authorities
                    ).apply {
                        details = WebAuthenticationDetailsSource().buildDetails(request)
                    }
                }
            } catch (e: JwtException) {
                // 토큰 파싱에 실패한 경우 로그를 남기고 인증 실패 처리
            }
        }

        return null
    }
}
