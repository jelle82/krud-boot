package com.example.krudspring

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface Users : CrudRepository<User, String> {

    fun findByName(name: String): Optional<User>

}
