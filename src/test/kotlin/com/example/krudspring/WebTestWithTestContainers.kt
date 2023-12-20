package com.example.krudspring

import com.oneeyedmen.okeydoke.Approver
import com.oneeyedmen.okeydoke.junit5.ApprovalsExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.testcontainers.containers.MariaDBContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Testcontainers
@ExtendWith(ApprovalsExtension::class)
class WebTestWithTestContainers {

    companion object {
        @Container
        @ServiceConnection
        var db: MariaDBContainer<*> = MariaDBContainer("mariadb:10.6.16").apply {
            setPortBindings(listOf("50001:3306"))
        }

    }

    @Autowired
    private lateinit var mvc: MockMvc

    @Autowired
    private lateinit var users: Users

    @Test
    fun name(approver: Approver) {
        users.save(User(null, "Mike", "unhashed", roles = listOf(Role.ADMIN)))
        users.save(User(null, "John", "unhashed", roles = listOf(Role.ADMIN)))


        val mvcResult = mvc.get("/user").andExpect { status { isOk() } }.andReturn()
        val html = mvcResult.response.contentAsString
        approver.assertApproved(html)
    }

    @Test
    fun name2(approver: Approver) {
        val user = users.save(User(null, "John", "unhashed", roles = listOf(Role.USER)))

        val mvcResult = mvc.get("/user/${user.publicId}").andExpect { status { isOk() } }.andReturn()

        approver.assertApproved(mvcResult.response.contentAsString)
    }

    @Test
    fun name3(approver: Approver) {
        val mvcResult = mvc.get("/user/aabbcc").andExpect { status { isOk() } }.andReturn()
        approver.assertApproved(mvcResult.response.contentAsString)
    }
}
