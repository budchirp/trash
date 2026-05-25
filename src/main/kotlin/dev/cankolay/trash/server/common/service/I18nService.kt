package dev.cankolay.trash.server.common.service

import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.stereotype.Service

@Service
class I18nService(private val messageSource: MessageSource) {
    fun get(key: String): String = try {
        messageSource.getMessage(key, null, LocaleContextHolder.getLocale())
    } catch (_: Exception) {
        key
    }

    fun getNullable(key: String): String? = try {
        messageSource.getMessage(key, null, LocaleContextHolder.getLocale())
    } catch (_: Exception) {
        null
    }
}
