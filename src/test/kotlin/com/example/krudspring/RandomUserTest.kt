package com.example.krudspring

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest
import org.springframework.http.MediaType
import org.springframework.http.client.ClientHttpRequest
import org.springframework.test.web.client.MockRestServiceServer
import org.springframework.test.web.client.match.MockRestRequestMatchers
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
            .expect { it.matches("https://randomuser.me/api/") }
            .andRespond { it.withJson(readFrom("user.json")) }

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


fun ClientHttpRequest?.withJson(body: String) =
    withSuccess(body, MediaType.APPLICATION_JSON)
        .createResponse(this)


fun ClientHttpRequest.matches(url: String) {
    MockRestRequestMatchers.requestTo(url)
        .match(this)
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

    response.body().saveTo("user.json")
}


private const val TEST_FOLDER = "src/test/kotlin/com/example/krudspring"

fun readFrom(fileName: String, parent: String = TEST_FOLDER): String = File(parent, fileName).readText()

fun String.saveTo(fileName: String, parent: String = TEST_FOLDER) = File(parent, fileName).writeText(this)
