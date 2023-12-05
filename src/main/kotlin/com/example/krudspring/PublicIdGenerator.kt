package com.example.krudspring

import org.springframework.stereotype.Component
import java.util.*

interface PublicIdGenerator {
    fun supply(): String
}

@Component
class UUIDGenerator : PublicIdGenerator {
    override fun supply() = UUID.randomUUID().toString()
}
