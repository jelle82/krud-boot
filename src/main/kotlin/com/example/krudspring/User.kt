package com.example.krudspring

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("user")
data class User(
    @Id
    override val publicId: String? = null,
    val name: String,
    val roles: Set<RoleRef> = emptySet(),
    val password: String,
) : DBEntity {

    constructor(publicId: String?, name: String, password: String, role: Role) : this(
        publicId,
        name,
        setOf(RoleRef(role)),
        password
    )

    constructor(publicId: String?, name: String, password: String, roles: List<Role>) : this(
        publicId,
        name,
        roles.map { RoleRef(it) }.toSet(), password
    )

    override fun copyEntity(publicId: String): DBEntity = this.copy(publicId = publicId)
}

interface DBEntity {
    val publicId: String?
    fun copyEntity(publicId: String): DBEntity
}

@JvmInline
@Table("user_role")
value class RoleRef(val role: Role)

enum class Role {
    ADMIN, USER
}
