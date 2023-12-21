package com.example.krudspring

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest
import org.springframework.http.MediaType
import org.springframework.test.web.client.MockRestServiceServer
import org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess
import java.io.File
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse


@RestClientTest(RandomUser::class)
class RandomUserTest {

    @Autowired
    private lateinit var randomUser: RandomUser

    @Autowired
    private lateinit var server: MockRestServiceServer

    @Test
    fun `fetch person from random user api`() {
        server
            .expect { "https://randomuser.me/api/" }
            .andRespond {
                withSuccess(readFromFile("user.json"), MediaType.APPLICATION_JSON)
                    .createResponse(it)
            }

        val people = randomUser.fetchUser()

        assertThat(people).isNotNull
        assertThat(people).hasSize(1)
        assertThat(people.get(0).name).isEqualTo(
            Name(
                "Mr", "Gumesindo", "Souza"
            )
        )
    }

}


fun saveJson() {
    val client = HttpClient.newHttpClient()

    val request = HttpRequest
        .newBuilder()
        .header("Accept", "application/json")
        .uri(URI.create("https://randomuser.me/api/"))
        .GET()
        .build()
    val response = client.send(request, HttpResponse.BodyHandlers.ofString())

    saveToFile(response.body(), "user.json")
}


private const val TEST_FOLDER = "src/test/kotlin/com/example/krudspring"

fun readFromFile(fileName: String): String = File(TEST_FOLDER, fileName).readText()

fun saveToFile(content: String, fileName: String) = File(TEST_FOLDER, fileName).writeText(content)
