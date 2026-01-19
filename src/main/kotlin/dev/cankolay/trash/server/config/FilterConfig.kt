package dev.cankolay.trash.server.config

import dev.cankolay.trash.server.module.auth.filter.AuthenticateFilter
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
}
