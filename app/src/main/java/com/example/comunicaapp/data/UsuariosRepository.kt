package com.example.comunicaapp.data

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.example.comunicaapp.model.Usuario

class UsuariosRepository private constructor() {

    private val firestore = FirebaseFirestore.getInstance()

    fun obtenerUsuarios(
        onSuccess: (List<Usuario>) -> Unit,
        onError: (Exception) -> Unit
    ) {
        firestore.collection(COLECCION_USUARIOS)
            .get()
            .addOnSuccessListener { snapshot ->
                onSuccess(snapshot.documents.mapNotNull { it.toUsuario() })
            }
            .addOnFailureListener(onError)
    }

    fun validar(
        email: String,
        password: String,
        onComplete: (Usuario?) -> Unit,
        onError: (Exception) -> Unit
    ) {
        firestore.collection(COLECCION_USUARIOS)
            .whereEqualTo("email", email.trim())
            .get()
            .addOnSuccessListener { snapshot ->
                onComplete(snapshot.documents
                    .mapNotNull { it.toUsuario() }
                    .firstOrNull { it.password == password })
            }
            .addOnFailureListener(onError)
    }

    fun existeEmail(
        email: String,
        onComplete: (Boolean) -> Unit,
        onError: (Exception) -> Unit
    ) {
        firestore.collection(COLECCION_USUARIOS)
            .whereEqualTo("email", email.trim())
            .limit(1)
            .get()
            .addOnSuccessListener { onComplete(!it.isEmpty) }
            .addOnFailureListener(onError)
    }

    fun registrar(
        usuario: Usuario,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit
    ) {
        firestore.collection(COLECCION_USUARIOS)
            .add(
                mapOf(
                    "nombre" to usuario.nombre,
                    "email" to usuario.email,
                    "password" to usuario.password
                )
            )
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener(onError)
    }

    private fun DocumentSnapshot.toUsuario(): Usuario? {
        val nombre = getString("nombre") ?: getString("name") ?: return null
        val email = getString("email") ?: return null
        val password = getString("password") ?: getString("contrasena") ?: ""
        return Usuario(nombre, email, password)
    }

    companion object {
        const val COLECCION_USUARIOS = "usuarios"
        val instancia: UsuariosRepository by lazy { UsuariosRepository() }
    }
}
