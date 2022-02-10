package com.devgabriel.mercadolivro.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/admin")
class AdminController {

    @GetMapping("/report")
    fun reportToAdmins(): String {
        return "Report to only users with ADMIN_ROLE"
    }
}