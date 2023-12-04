package com.example.krudspring

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.relational.core.mapping.event.BeforeConvertCallback
import java.util.*


@Configuration
class UserCrudRepositoryConfiguration {

    @Bean
    fun beforeConvertCallback(): BeforeConvertCallback<User> {
        return BeforeConvertCallback<User> {
            when (it.publicId) {
                null -> it.copy(publicId = UUID.randomUUID().toString())
                else -> it
            }
        }
    }


}
