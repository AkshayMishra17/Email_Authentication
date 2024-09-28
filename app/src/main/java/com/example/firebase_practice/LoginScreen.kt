package com.example.firebase_practice

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.google.firebase.auth.FirebaseAuth

@Composable
fun LoginScreen(auth: FirebaseAuth) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current

   

    Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
        ) {
        TextField(value = email, onValueChange = { email = it }, label = { Text("Email") })
        TextField(value = password, onValueChange = { password = it }, label = { Text("Password") })

        // Sign In button with validation
        Button(onClick = {
            if (email.isNotBlank() && password.isNotBlank()) {
                signInUser(auth, email, password, context)
            } else {
                Toast.makeText(context, "Please enter both email and password", Toast.LENGTH_SHORT).show()
            }
        }) {
            Text("Sign In")
        }

        // Sign Up button with validation
        Button(onClick = {
            if (email.isNotBlank() && password.isNotBlank()) {
                signUpUser(auth, email, password, context)
            } else {
                Toast.makeText(context, "Please enter both email and password", Toast.LENGTH_SHORT).show()
            }
        }) {
            Text("Sign Up")
        }
    }
}

fun signInUser(auth: FirebaseAuth, email: String, password: String, context: android.content.Context) {
    auth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Sign-in successful
                Toast.makeText(context, "Welcome back!", Toast.LENGTH_SHORT).show()
            } else {
                // Sign-in failed
                Toast.makeText(context, "Login Failed: ${task.exception?.message}", Toast.LENGTH_LONG).show()
            }
        }
}

fun signUpUser(auth: FirebaseAuth, email: String, password: String, context: android.content.Context) {
    auth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // User created successfully
                Toast.makeText(context, "User Created", Toast.LENGTH_SHORT).show()
            } else {
                // If sign up fails
                Toast.makeText(context, "Sign Up Failed: ${task.exception?.message}", Toast.LENGTH_LONG).show()
            }
        }
}
