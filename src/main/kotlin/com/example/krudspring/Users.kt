package com.example.krudspring

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.relational.core.mapping.event.BeforeConvertCallback
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface Users : CrudRepository<User, String> {
    fun findByName(name: String): Optional<User>
}

@Configuration
class UserCrudRepositoryConfiguration {

    @Bean
    fun customPublicIdForNewUser(): BeforeConvertCallback<User> {
        return BeforeConvertCallback<User> {
            when (it.publicId) {
                null -> it.copy(publicId = UUID.randomUUID().toString())
                else -> it
            }
        }
    }
}
