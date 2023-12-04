package com.example.krudspring

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface Users: CrudRepository<User, String> {}
