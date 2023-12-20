package com.example.krudspring

import com.oneeyedmen.okeydoke.Approver
import com.oneeyedmen.okeydoke.junit5.ApprovalsExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@WebMvcTest
@AutoConfigureMockMvc
@ExtendWith(ApprovalsExtension::class)
class WebTestWithWebMvcTest {


    @Autowired
    private lateinit var mvc: MockMvc

    @MockBean
    private lateinit var users: Users

    @Test
    fun name(approver: Approver) {

        Mockito.`when`(users.findAll()).thenReturn(
            listOf(
                User(null, "Mike", "unhashed", roles = listOf(Role.ADMIN)),
                User(null, "John", "unhashed", roles = listOf(Role.USER))
            )
        )

        val mvcResult = mvc.get("/user").andExpect { status { isOk() } }.andReturn()
        val html = mvcResult.response.contentAsString
        approver.assertApproved(html)
    }

}
