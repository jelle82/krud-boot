package com.example.krudspring

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class UserController(
    private val users: Users,
) {


    @GetMapping("/user")
    fun allUsers(model: Model) {

    }
}
