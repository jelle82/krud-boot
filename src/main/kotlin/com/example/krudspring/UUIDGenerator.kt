package com.example.krudspring

import org.springframework.stereotype.Component
import java.util.*

@Component
class UUIDGenerator : PublicIdGenerator {
    override fun supply(): String {
        return UUID.randomUUID().toString()
    }
}
