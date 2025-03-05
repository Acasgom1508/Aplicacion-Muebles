package com.example.aplicacionmuebles;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class InicioSesion extends AppCompatActivity {

    private EditText correo, pass;
    FirebaseAuth auth;
    boolean esVisible = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_inicio_sesion);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Obtener referencias a los campos de entrada
        correo = findViewById(R.id.usuario_pt);
        pass = findViewById(R.id.contra_pt);
        auth = FirebaseAuth.getInstance();

        // Verificar si el usuario ya está autenticado
        if (auth.getCurrentUser() != null) {
            Intent irMain = new Intent(this, MainActivity.class);
            startActivity(irMain);
        }
    }

    public void iniciarSesion(View view) {
        String correoUsuario = correo.getText().toString().trim();
        String passUsuario = pass.getText().toString().trim();

        if (correoUsuario.isEmpty() || passUsuario.isEmpty()) {
            Toast.makeText(this, "Complete todos los campos", Toast.LENGTH_SHORT).show();
        } else {
            inicioUsuario(correoUsuario, passUsuario);
        }

    }

    private void inicioUsuario(String correoUsuario, String passUsuario) {
        auth.signInWithEmailAndPassword(correoUsuario, passUsuario).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Intent irMain = new Intent(InicioSesion.this, MainActivity.class);
                    startActivity(irMain);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(InicioSesion.this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void irRegistro(View view) {
        Intent irRegistro = new Intent(this, Registro.class);
        startActivity(irRegistro);
    }
    public void ocultar(View view) {
        if (esVisible) {
            pass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            esVisible = false;
        } else {
            pass.setInputType(InputType.TYPE_CLASS_TEXT);
            esVisible = true;
        }

    }



}