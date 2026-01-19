package dev.cankolay.trash.server.config

import dev.cankolay.trash.server.module.auth.filter.AuthenticateFilter
import dev.cankolay.trash.server.module.auth.filter.SessionValidationFilter
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class FilterConfig {
    @Bean
    fun authenticateFilterRegistration(filter: AuthenticateFilter): FilterRegistrationBean<AuthenticateFilter> {
        return FilterRegistrationBean<AuthenticateFilter>().apply {
            this.setFilter(filter)
        }
    }

    @Bean
    fun sessionValidationFilterRegistration(filter: SessionValidationFilter): FilterRegistrationBean<SessionValidationFilter> {
        return FilterRegistrationBean<SessionValidationFilter>().apply {
            this.setFilter(filter)
        }
    }
}
