package com.example.krudspring

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.groups.Tuple
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.testcontainers.containers.MariaDBContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers


@SpringBootTest
@Testcontainers
class UserTCTest {

    @Autowired
    private lateinit var users: Users

    companion object {
        @Container
        @ServiceConnection
        var db: MariaDBContainer<*> = MariaDBContainer("mariadb:10.6.16").apply {
            setPortBindings(listOf("50001:3306"))
        }

    }

    @Test
    fun `save and update`() {

        val save = users.save(User(null, "Mike", "unhashed", roles = listOf(Role.ADMIN)))
        assertThat(users.findById(save.publicId!!).get().name).isEqualTo("Mike")
        users.save(User(save.publicId!!, "John", "unhashed", roles = listOf(Role.USER)))
        assertThat(users.findById(save.publicId!!).get().name).isEqualTo("John")
        assertThat(users.findById(save.publicId!!).get().password).isEqualTo("unhashed")
        assertThat(users.findById(save.publicId!!).get().roles)
            .map({ it.role })
            .containsExactlyInAnyOrder(
                Tuple.tuple(Role.USER)
            )
    }

    @Test
    fun `find by name`() {
        val mike = users.save(User(null, "Mike", "unhashed", role = Role.ADMIN))
        assertThat(users.findByName("Mike").get()).isEqualTo(mike)
        assertThat(users.findByName("Mike").get().roles)
            .map({ it.role })
            .containsExactlyInAnyOrder(
                Tuple.tuple(Role.ADMIN)
            )
    }

}


