package com.example.krudspring

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@Controller
class UserController(
    private val users: Users,
) {


    @GetMapping("/user")
    fun allUsers(model: Model): String {
        model["users"] = users.findAll()
        return "users"
    }

    @GetMapping("/user/{publicId}")
    fun userById(@PathVariable publicId: String, model: Model): String {
        val userOptional = users.findById(publicId)

        if (userOptional.isEmpty) {
            return "user-not-found"
        } else {
            model["user"] = userOptional.get()
            return "user"
        }
    }
}
