package com.example.krudspring

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.relational.core.mapping.event.BeforeConvertCallback
import org.springframework.stereotype.Component
import java.util.*

@Configuration
class CrudRepositoryConfiguration {

    @Bean
    fun customPublicIdForNewUser(publicIdGenerator: PublicIdGenerator): BeforeConvertCallback<DBEntity> {
        return BeforeConvertCallback<DBEntity> {
            if (it.publicId == null) {
                it.copyEntity(publicId = publicIdGenerator.supply())
            } else {
                it
            }
        }
    }
}

interface PublicIdGenerator {
    fun supply(): String
}

@Component
class UUIDGenerator : PublicIdGenerator {
    override fun supply() = UUID.randomUUID().toString()
}
