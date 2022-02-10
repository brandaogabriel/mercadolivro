package com.devgabriel.mercadolivro.configuration

import com.devgabriel.mercadolivro.repository.CustomerRepository
import com.devgabriel.mercadolivro.security.AuthenticationFilter
import com.devgabriel.mercadolivro.security.JwtUtil
import com.devgabriel.mercadolivro.service.UserDetailsCustomerService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Configuration
@EnableWebSecurity
class WebSecurityConfiguration(
    private val customerRepository: CustomerRepository,
    private val userDetails: UserDetailsCustomerService,
    private val jwtUtil: JwtUtil
) : WebSecurityConfigurerAdapter() {

    private val PUBLIC_MATCHERS = arrayOf<String>()
    private val PUBLIC_POST_MATCHERS = arrayOf("/api/v1/customers")

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(userDetails).passwordEncoder(bCryptPasswordEncoder())
    }

    override fun configure(http: HttpSecurity) {
        http.cors().and().csrf().disable()

        http.authorizeRequests()
            .antMatchers(*PUBLIC_MATCHERS).permitAll()
            .antMatchers(HttpMethod.POST, *PUBLIC_POST_MATCHERS).permitAll()
            .anyRequest().authenticated()

        http.addFilter(AuthenticationFilter(authenticationManager(), customerRepository, jwtUtil))
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    }

    @Bean
    fun bCryptPasswordEncoder() = BCryptPasswordEncoder()
}