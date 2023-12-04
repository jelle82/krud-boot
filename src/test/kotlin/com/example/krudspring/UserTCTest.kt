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
    fun name() {

        val firstMappedPort = db.getFirstMappedPort()
        users.save(
            User(null, "Jenn")
        )
        val save = users.save(
            User(null, "Mike")
        )


        assertThat(users.findById(save.publicId!!).get().name).isEqualTo("Mike")

    }


}
