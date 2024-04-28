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
import android.widget.Button;
import android.widget.Toast;

import java.util.Date;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    final static int REQUEST_CODE = 1232;
    Button btnCrearPdf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        askForPermissions();
        btnCrearPdf = findViewById(R.id.btnCrearPdf);
        btnCrearPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreatePDF();
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


    }
