package com.example.krudspring

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("user")
data class User(

    @Id
    override val publicId: String? = null,
    val name: String,
) : DBEntity {
    override fun copyEntity(publicId: String): User = this.copy(publicId = publicId)
}

interface DBEntity {
    val publicId: String?
    fun copyEntity(publicId: String): DBEntity
}
