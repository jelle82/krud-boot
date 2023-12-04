package com.example.krudspring

import org.assertj.core.api.Assertions.assertThat
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
        var db: MariaDBContainer<*> = MariaDBContainer("mariadb:10.5.5").apply {
            setPortBindings(listOf("50001:3306"))
        }

    }

    @Test
    fun `save and update`() {

        val save = users.save(aNewUser("Mike"))
        assertThat(users.findById(save.publicId!!).get().name).isEqualTo("Mike")
        users.save(anExistingUser(save.publicId!!, "John"))
        assertThat(users.findById(save.publicId!!).get().name).isEqualTo("John")
    }

    @Test
    fun `find by name`() {
        val mike = users.save(aNewUser("Mike"))
        assertThat(users.findByName("Mike").get()).isEqualTo(mike)
    }

}


private fun anExistingUser(publicId: String, name: String) = User(publicId, name)
private fun aNewUser(name: String) = User(null, name)
