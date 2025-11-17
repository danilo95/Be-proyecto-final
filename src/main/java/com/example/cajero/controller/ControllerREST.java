package com.example.cajero.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
class TestController {

    @GetMapping("/test")
    public String test() {
        return "✅ Backend funcionando correctamente - " + new java.util.Date();
    }

    @GetMapping("/health")
    public String health() {
        return "🟢 Servicio activo - Banco Agricultura - " + new java.util.Date();
    }
}