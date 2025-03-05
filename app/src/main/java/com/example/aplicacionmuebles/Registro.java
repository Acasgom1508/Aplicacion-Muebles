package com.example.aplicacionmuebles;

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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Registro extends AppCompatActivity {

    private EditText nombre, usuario, pass, pass2;
    FirebaseAuth auth;
    FirebaseFirestore db;
    boolean esVisible = false;
    boolean esVisible2 = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registro);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Obtener referencias a los campos de entrada
        nombre = findViewById(R.id.nombre_pt);
        usuario = findViewById(R.id.usuario_pt);
        pass = findViewById(R.id.contra_pt);
        pass2 = findViewById(R.id.contra_pt);
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }

    public void registrarse(View view) {
        String correoUsuario = usuario.getText().toString().trim();
        String passUsuario = pass.getText().toString().trim();
        String passUsuario2 = pass2.getText().toString().trim();
        String nombreUsuario = nombre.getText().toString().trim();

        if (correoUsuario.isEmpty() || passUsuario.isEmpty() || passUsuario2.isEmpty() || nombreUsuario.isEmpty()) {
            Toast.makeText(this, "Complete todos los campos", Toast.LENGTH_SHORT).show();
        } else {
            if (passUsuario.equals(passUsuario2)){
                registroUsuario(correoUsuario, passUsuario, nombreUsuario);
            }
        }
    }

    private void registroUsuario(String correoUsuario, String passUsuario, String nombre) {
        auth.createUserWithEmailAndPassword(correoUsuario, passUsuario).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    String id = auth.getCurrentUser().getUid();
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", id);
                    map.put("nombre", nombre);
                    map.put("correo", correoUsuario);

                    db.collection("usuarios").document(id).set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(Registro.this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Registro.this, "Error al registrarse", Toast.LENGTH_SHORT).show();
                        }
                    });
                }else{
                    Toast.makeText(Registro.this, "Error al registrarse", Toast.LENGTH_SHORT).show();
                }
            }
        });
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

    public void ocultar2(View view) {
        if (esVisible) {
            pass2.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            esVisible2 = false;
        } else {
            pass2.setInputType(InputType.TYPE_CLASS_TEXT);
            esVisible2 = true;
        }
    }
}