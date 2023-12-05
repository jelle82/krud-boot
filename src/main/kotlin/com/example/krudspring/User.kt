package com.example.krudspring

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("user")
data class User(

    @Id
    val publicId: String? = null,
    val name: String,
)
