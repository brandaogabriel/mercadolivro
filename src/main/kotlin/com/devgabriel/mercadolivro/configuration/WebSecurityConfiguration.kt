package com.devgabriel.mercadolivro.configuration

import com.devgabriel.mercadolivro.enums.Role
import com.devgabriel.mercadolivro.repository.CustomerRepository
import com.devgabriel.mercadolivro.security.*
import com.devgabriel.mercadolivro.service.UserDetailsCustomerService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) //enable access to @PreAuthorize validations
class WebSecurityConfiguration(
    private val customerRepository: CustomerRepository,
    private val userDetails: UserDetailsCustomerService,
    private val customEntryPoint: CustomAuthenticationEntryPoint,
    private val customAccessDeniedHandler: CustomAccessDeniedHandler,
    private val jwtUtil: JwtUtil
) : WebSecurityConfigurerAdapter() {

    private val PUBLIC_GET_MATCHERS = arrayOf("/api/v1/books/**")
    private val PUBLIC_POST_MATCHERS = arrayOf("/api/v1/customers")
    private val ADMIN_MATCHERS = arrayOf("/admin/**", "/api/v1/customers")

    @Bean
    fun bCryptPasswordEncoder() = BCryptPasswordEncoder()

    override fun configure(web: WebSecurity) {
        web.ignoring()
            .antMatchers(
                "/v2/api-docs/**",
                "/v3/api-docs/**",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui/**",
                "/swagger-ui.html",
                "/webjars/**"
            )
    }

    @Bean
    fun corsConfig(): CorsFilter {
        val source = UrlBasedCorsConfigurationSource()
        val config = CorsConfiguration()
        config.allowCredentials = true
        config.addAllowedOriginPattern("*")
        config.addAllowedHeader("*")
        config.addAllowedMethod("*")
        source.registerCorsConfiguration("/**", config)
        return CorsFilter(source)
    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(userDetails).passwordEncoder(bCryptPasswordEncoder())
    }

    override fun configure(http: HttpSecurity) {
        http.cors().and().csrf().disable()

        http.authorizeRequests()
            .antMatchers(HttpMethod.GET, *PUBLIC_GET_MATCHERS).permitAll()
            .antMatchers(HttpMethod.POST, *PUBLIC_POST_MATCHERS).permitAll()
            .antMatchers(*ADMIN_MATCHERS).hasAuthority(Role.ADMIN.description)
            .anyRequest().authenticated()

        http.addFilter(AuthenticationFilter(authenticationManager(), customerRepository, jwtUtil))
        http.addFilter(AuthorizationFilter(authenticationManager(), userDetails, jwtUtil))
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        http.exceptionHandling().authenticationEntryPoint(customEntryPoint)
        http.exceptionHandling().accessDeniedHandler(customAccessDeniedHandler)
    }

}