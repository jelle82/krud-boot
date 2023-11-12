package com.example.krudspring

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping

@Controller
class UserController {


    @GetMapping
    fun all(model: Model): String {
        model["users"] = listOf(User(
            id = "1",
            name ="John Doe",
            age = 42
        ))
        return "all"
    }
}
