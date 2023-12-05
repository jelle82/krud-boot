package com.example.krudspring

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.relational.core.mapping.event.BeforeConvertCallback

@Configuration
class CrudRepositoryConfiguration {

    @Bean
    fun customPublicIdForNewUser(publicIdGenerator: PublicIdGenerator): BeforeConvertCallback<User> {
        return BeforeConvertCallback<User> {
            when (it.publicId) {
                null -> it.copy(publicId = publicIdGenerator.supply())
                else -> it
            }
        }
    }
}
