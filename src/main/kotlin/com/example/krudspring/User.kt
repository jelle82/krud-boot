package com.example.krudspring

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

data class User(

    @Id
    val publicId: String? = null,
    val name: String,
)

@Table("user")
data class NewUser (
    override val name: String,
): User2 {
    override val publicId: String?
        get() = null
}

@Table("user")
data class ExistingUser (
    override val name: String,
    override val publicId: String,
): User2


sealed interface User2 {
    val name: String
    @get:Id
    val publicId: String?
}

