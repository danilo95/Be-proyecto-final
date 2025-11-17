package com.example.proyectofinal.sucursal.EXCEPTIONS;


//elementos centralizan la gestión de errores HTTP,
// devuelven códigos HTTP consistentes y mensajes claros.
public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
