package com.example.aplicacionmuebles;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.util.TypedValue;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    LinearLayout listaSillas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        listaSillas = findViewById(R.id.lista_sillas);


    }

    public void annadirBoton(View view) {
        // Crear el botón
        Button btnMueble = new Button(this);
        btnMueble.setText("Nueva silla");
        btnMueble.setBackground(getResources().getDrawable(R.drawable.fondo_boton));

        // Convertir dp a píxeles para el tamaño del botón
        int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 320, getResources().getDisplayMetrics());
        int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 120, getResources().getDisplayMetrics());

        // Convertir dp a píxeles para el margen inferior
        int marginBottom = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, getResources().getDisplayMetrics());

        // Establecer las dimensiones y el margen del botón
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height);
        params.setMargins(0, 0, 0, marginBottom);  // Margen superior, izquierdo, derecho e inferior

        btnMueble.setLayoutParams(params);

        // Definir el comportamiento al hacer clic en el botón
        btnMueble.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Silla 2");
            builder.setMessage("Soy la silla 2 y tengo 4 patas.");

            // Botón positivo (Cerrar)
            builder.setPositiveButton("Cerrar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Acciones al presionar "Cerrar"
                    dialog.dismiss();
                }
            });

            // Mostrar el AlertDialog
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        });

        // Añadir el botón al contenedor (listaSillas)
        listaSillas.addView(btnMueble);
    }



}

