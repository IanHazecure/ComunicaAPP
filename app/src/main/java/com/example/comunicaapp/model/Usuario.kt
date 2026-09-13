package com.example.comunicaapp.model

class Usuario(
    nombre: String,
    email: String,
    val password: String
) : Persona(nombre, email)
