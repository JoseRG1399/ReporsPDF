package com.example.reportes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    final static int REQUEST_CODE = 1232;
    Button btnCrearPdf;

    Button btnFirmar;
    EditText txtFirma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        askForPermissions();

        setupSpinnerBasic();
        btnFirmar = findViewById(R.id.btnFirmar);
        btnCrearPdf = findViewById(R.id.btnCrearPdf);
        txtFirma = findViewById(R.id.txtFirma);

        btnCrearPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreatePDF();
            }
        });
        btnFirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firma = txtFirma.getText().toString();
                if (!firma.isEmpty()) {
                    obtenerNombreVendedora(firma);
                    Toast.makeText(getApplicationContext(), "Tu firma es: "+ firma, Toast.LENGTH_SHORT).show();

                } else {
                    // El campo está vacío, puedes mostrar un mensaje al usuario o realizar otra acción
                    Toast.makeText(getApplicationContext(), "Ingrese una firma", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }




    private void askForPermissions(){
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);

    }


        private void CreatePDF(){
            PdfDocument document = new PdfDocument();
            PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(2480, 3508, 1).create();
            PdfDocument.Page page = document.startPage(pageInfo);

            Canvas canvas = page.getCanvas();
            Paint paint = new Paint();
            paint.setColor(Color.BLACK);
            paint.setTextSize(42);


            // Cambiar el título (en la parte superior izquierda)
            String title = "COMERCIALIZADORA SARON SHEKINA S.A DE C.V";
            paint.setTextSize(62); // Tamaño grande para el título
            paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD)); // Negrita

            float titleX = 50; // Alineado a la izquierda
            float titleY = 100; // Alineado arriba
            canvas.drawText(title, titleX, titleY, paint);



            // Agregar salto de línea
            titleY += paint.getFontSpacing(); // Añadir espacio entre líneas

            // Subtítulo
            String subtitle = "Reporte de mantenimiento de equipos de refrigeración";
            paint.setTextSize(46); // Tamaño para el subtítulo
            paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD)); // Negrita
            canvas.drawText(subtitle, titleX, titleY, paint);

// Cuerpo de la sección
            paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD_ITALIC)); // Negrita
            paint.setTextSize(36); // Tamaño para el cuerpo del texto
            float bodyX = 50; // Alineado a la izquierda
            float bodyY = titleY + paint.getFontSpacing() + 80; // Alineado debajo del subtítulo y con espacio adicional

// Texto: Sucursal
            String sucursal = "Sucursal: Nombre de la sucursal";
            canvas.drawText(sucursal, bodyX, bodyY, paint);
            bodyY += paint.getFontSpacing() + 50 ; // Añadir espacio entre líneas

// Texto: Encargada de ventas
            String encargadaVentas = "Encargada de ventas: Nombre de la persona";
            canvas.drawText(encargadaVentas, bodyX, bodyY, paint);
            bodyY += paint.getFontSpacing() + 50 ; // Añadir espacio entre líneas

// Texto: Equipo
            String equipo = "Equipo: Nombre del equipo";
            canvas.drawText(equipo, bodyX, bodyY, paint);
            bodyY += paint.getFontSpacing() + 50 ; // Añadir espacio entre líneas

// Texto: Comentarios del mantenimiento
            String comentariosMantenimiento = "Comentarios del mantenimiento: ";
            canvas.drawText(comentariosMantenimiento, bodyX, bodyY, paint);
            bodyY += paint.getFontSpacing() + 50 ; // Añadir espacio entre líneas

// Texto: Firma electrónica
            String firmaElectronica = "Firma electrónica: ";
            canvas.drawText(firmaElectronica, bodyX, bodyY, paint);
            bodyY += paint.getFontSpacing() + 50 ; // Añadir espacio entre líneas




// Fecha actual (en la parte superior derecha)
            String currentDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
            paint.setTextSize(54); // Tamaño más pequeño para la fecha
            float dateX = pageInfo.getPageWidth() - 50; // Alineado a la derecha
            float dateY = 100; // Alineado arriba
            paint.setTextAlign(Paint.Align.RIGHT);
            canvas.drawText(currentDate, dateX, dateY, paint);







            document.finishPage(page);


            File downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
            String fileName = "Example_" + timeStamp + ".pdf";
            File file = new File(downloadsDir,fileName);
            try {
                FileOutputStream fos = new FileOutputStream(file);
                document.writeTo(fos);
                document.close();
                fos.close();
                Toast.makeText(this, "written Successfuly!!!!",Toast.LENGTH_SHORT).show();

            } catch (FileNotFoundException e) {
                Log.d("mylog","Error while writing"+ e.toString());
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        }

    private void setupSpinnerBasic() {
        Spinner spinner = findViewById(R.id.spinner_basic);

        // Crea un ArrayAdapter sin elementos y con el layout del hint
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        // Agrega el hint como primer elemento
        List<CharSequence> items = new ArrayList<>();
        items.add(getString(R.string.spinner_hint));
        items.addAll(Arrays.asList(getResources().getTextArray(R.array.Sucursales)));
        adapter.addAll(items);

        // Selecciona el hint como elemento inicial
        spinner.setSelection(0);

        // Escucha los eventos para deshabilitar la selección del hint
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Si se selecciona el hint, no se realiza ninguna acción
                if (position == 0) {
                    spinner.setSelection(0);
                    Toast.makeText(getApplicationContext(), "Por favor selecciona una sucursal", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // No se realiza ninguna acción
            }
        });
    }

    public static String obtenerNombreVendedora(String matricula) {
        String[][] vendedoras = new String[][]{
                {"1987", "DIANA ESTHELA"},
                {"8809", "DORIS ALMANZA"},
                {"6601", "MARY CASTRO"},
                // Agrega el resto de las matrículas y nombres aquí
        };

        for (String[] vendedora : vendedoras) {
            if (vendedora[0].equals(matricula)) {
                return vendedora[1];
            }
        }
        return "Matrícula no encontrada";
    }




}
