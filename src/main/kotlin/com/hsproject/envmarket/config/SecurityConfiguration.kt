package com.hsproject.envmarket.config

import com.hsproject.envmarket.service.UserDetailsServiceImpl
import com.hsproject.envmarket.util.JwtAuthenticationEntryPoint
import com.hsproject.envmarket.util.JwtAuthorizationFilter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter


@Configuration
@EnableWebSecurity
class SecurityConfig : WebSecurityConfigurerAdapter() {

    @Autowired
    private lateinit var userDetailsService: UserDetailsServiceImpl

    @Autowired
    private lateinit var jwtConfig: JwtConfiguration

    @Autowired
    private lateinit var jwtAuthenticationEntryPoint: JwtAuthenticationEntryPoint

    @Bean
    fun corsFilter(): CorsFilter {
        val source = UrlBasedCorsConfigurationSource()
        val config = CorsConfiguration()
        config.allowCredentials = true
        config.addAllowedOrigin("*")
        config.addAllowedHeader("*")
        config.addAllowedMethod("*")
        source.registerCorsConfiguration("/**", config)
        return CorsFilter(source)
    }

    @Bean
    fun jwtAuthorizationFilter(): JwtAuthorizationFilter {
        return JwtAuthorizationFilter(authenticationManager(), jwtConfig, userDetailsService)
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Autowired
    fun configureGlobal(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder())
    }

    override fun configure(httpSecurity: HttpSecurity) {

        httpSecurity
                // 웹 애플리케이션에 대한 보안 설정
                .addFilter(corsFilter())
                .addFilterBefore(jwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter::class.java)
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/auth/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
        // JWT 및 세션 사용 제거
    }
}



