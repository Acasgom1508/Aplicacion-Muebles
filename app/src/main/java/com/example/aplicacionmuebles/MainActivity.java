package com.example.aplicacionmuebles;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class MainActivity extends AppCompatActivity {

    LinearLayout listaSillas;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listaSillas = findViewById(R.id.lista_sillas);
        db = FirebaseFirestore.getInstance();

        // Obtener los datos de la colección "sillas" en Firebase
        db.collection("sillas").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    String nombre = document.getString("nombre");
                    double precio = document.getLong("precio");
                    String descripcion = document.getString("desc");

                    // Crear un botón para cada silla
                    Button btnMueble = new Button(this);
                    btnMueble.setText(nombre + " - " + precio + "€");
                    btnMueble.setBackground(getResources().getDrawable(R.drawable.fondo_boton));

                    // Convertir dp a píxeles para el tamaño del botón
                    int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 320, getResources().getDisplayMetrics());
                    int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 120, getResources().getDisplayMetrics());

                    // Convertir dp a píxeles para el margen inferior
                    int marginBottom = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, getResources().getDisplayMetrics());

                    // Establecer las dimensiones y el margen del botón
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height);
                    params.setMargins(0, 0, 0, marginBottom);

                    btnMueble.setLayoutParams(params);

                    // Configurar el click listener para mostrar el alertDialog con la descripción
                    btnMueble.setOnClickListener(v -> {
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setTitle(nombre + " - " + precio + "€");
                        builder.setMessage(descripcion);

                        // Botón positivo (Cerrar)
                        builder.setPositiveButton("Cerrar", (dialog, which) -> dialog.dismiss());

                        // Mostrar el AlertDialog
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    });

                    // Añadir el botón al contenedor (listaSillas)
                    listaSillas.addView(btnMueble);
                }
            } else {
                // Manejo de errores al obtener datos
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Error");
                builder.setMessage("No se pudo cargar la lista de sillas.");
                builder.setPositiveButton("Cerrar", (dialog, which) -> dialog.dismiss());
                builder.create().show();
            }
        });
    }
}
