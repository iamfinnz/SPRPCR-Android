package com.example.aplikasipeminjamanruangan.presentation.ui

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.aplikasipeminjamanruangan.R
import com.example.aplikasipeminjamanruangan.domain.model.PengajuanModel
import com.example.aplikasipeminjamanruangan.presentation.navigation.Home
import com.example.aplikasipeminjamanruangan.presentation.navigation.HomeBottomNavBar
import com.example.aplikasipeminjamanruangan.presentation.navigation.HomeDetail
import com.example.aplikasipeminjamanruangan.presentation.navigation.Lending
import com.example.aplikasipeminjamanruangan.presentation.navigation.LendingForm
import com.example.aplikasipeminjamanruangan.presentation.navigation.NavGraph
import com.example.aplikasipeminjamanruangan.presentation.navigation.Splash
import com.example.aplikasipeminjamanruangan.presentation.navigation.WaitingDetail
import com.example.aplikasipeminjamanruangan.presentation.navigation.listOfTabScreen
import com.example.aplikasipeminjamanruangan.presentation.ui.theme.AplikasiPeminjamanRuanganTheme
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MainApp()
        }
    }

    @Composable
    fun MainApp() {
        AplikasiPeminjamanRuanganTheme {
            // A surface container using the 'background' color from the theme
            val navController = rememberNavController()

            val currentBackStack by navController.currentBackStackEntryAsState()

            // fetch currentDestination
            val currentDestination = currentBackStack?.destination

            val currentScreen = listOfTabScreen.find {
                it.route == currentDestination?.route
            } ?: Home

            Scaffold(bottomBar = {
                AnimatedVisibility(
                    visible = currentDestination?.route != Splash.route && currentDestination?.route != HomeDetail.route && currentDestination?.route != Lending.route && currentDestination?.route != LendingForm.route && currentDestination?.route != Home.route && currentDestination?.route != WaitingDetail.route
                ) {
                    BottomBar(navBackStackEntry = currentBackStack, navController = navController)
                }
            }) { innerPadding ->
                NavGraph(navController = navController, modifier = Modifier.padding(innerPadding))
            }
        }
    }

    @Composable
    fun BottomBar(navBackStackEntry: NavBackStackEntry?, navController: NavHostController) {
        AnimatedVisibility(visible = true) {
            HomeBottomNavBar(
                navBackStackEntry = navBackStackEntry, navController = navController
            )
        }
    }
}

fun generatePDF4(context: Context) {
    val document = PdfDocument()

    try {
        val file = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
            "my_document.pdf"
        )

        val pageInfo = PdfDocument.PageInfo.Builder(600, 800, 1).create()
        val page = document.startPage(pageInfo)

        val canvas: Canvas = page.canvas
        val paint = Paint()

        // Add text
        paint.color = Color.BLACK
        paint.textSize = 30f
        paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        canvas.drawText("Hello, this is my PDF!", 20f, 30f, paint)

        // Add image
        val imageBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.loading_img)
        canvas.drawBitmap(imageBitmap, 20f, 50f, paint)

        document.finishPage(page)

        val outputStream = FileOutputStream(file)
        document.writeTo(outputStream)

        Toast.makeText(context, "PDF created successfully", Toast.LENGTH_SHORT).show()
    } catch (e: IOException) {
        e.printStackTrace()
        Toast.makeText(context, "Error creating PDF", Toast.LENGTH_SHORT).show()
    } finally {
        document.close()
    }
}

fun generatePDF2(context: Context) {
    val pageHeight = 1120
    val pageWidth = 792

    lateinit var scaledbmp: Bitmap

    val pdfDocument: PdfDocument = PdfDocument()

    val paint: Paint = Paint()
    val title: Paint = Paint()

    val bmp: Bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.gallery)
    scaledbmp = Bitmap.createScaledBitmap(bmp, 140, 140, false)

    val myPageInfo: PdfDocument.PageInfo? =
        PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 1).create()

    val myPage: PdfDocument.Page = pdfDocument.startPage(myPageInfo)

    val canvas: Canvas = myPage.canvas

    canvas.drawBitmap(scaledbmp, 56F, 40F, paint)

    title.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL))

    title.textSize = 15F

    title.setColor(ContextCompat.getColor(context, R.color.purple_200))

    canvas.drawText("A portal for IT professionals.", 209F, 100F, title)
    canvas.drawText("Geeks for Geeks", 209F, 80F, title)
    title.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL))
    title.setColor(ContextCompat.getColor(context, R.color.purple_200))
    title.textSize = 15F

    title.textAlign = Paint.Align.CENTER
    canvas.drawText("This is sample document which we have created.", 396F, 560F, title)

    pdfDocument.finishPage(myPage)

    val file: File = File(Environment.getExternalStorageDirectory(), "GFG.pdf")

    try {
        pdfDocument.writeTo(FileOutputStream(file))

        Toast.makeText(context, "PDF file generated..", Toast.LENGTH_SHORT).show()
    } catch (e: Exception) {
        e.printStackTrace()

        Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show()
        Log.d("ilham", e.toString())
    }
    pdfDocument.close()
}

fun generatePDF(context: Context, data: PengajuanModel) {
    val dataPengajuan = mapOf(
        "Peminjam" to data.nama,
        "NIM" to data.nim,
        "Program Studi" to data.prodi,
        "Ruangan" to data.ruangan,
        "Tanggal" to data.tanggal,
        "Jam Mulai" to data.jmulai,
        "Jam Selesai" to data.jselesai,
        "Unit" to data.unit,
        "Penanggung Jawab" to data.penanggungJawab,
        "Keperluan" to data.keperluan,
        "Status" to data.pengajuanDiterima,
        "Catatan" to data.catatan
    )
    val document = PdfDocument()
    val pageHeight = 1120
    val pageWidth = 792
    try {
        val pageInfo = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 1).create()
        val page = document.startPage(pageInfo)
        val paint = Paint()
        val canvas: Canvas = page.canvas

        val imageBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.logo_psti)
        BitmapFactory.Options().inSampleSize
        val scaledBmp = Bitmap.createScaledBitmap(imageBitmap, 125, 125, false)
        canvas.drawBitmap(scaledBmp, 30F, 30F, paint)

        text(
            paint = paint,
            canvas = canvas,
            color = Color.BLACK,
            text = "Bukti Peminjaman Ruangan PCR",
            textSize = 28f,
            typeFace = Typeface.create(Typeface.DEFAULT, Typeface.BOLD),
            xPos = 180f,
            yPos = 106f
        )

        paint.color = Color.BLACK
        canvas.drawLine(
            0f,
            160f,
            792f,
            160f,
            paint
        )

        val startXPos = 30f
        val endXPos = pageWidth - startXPos
        var startYPos = 240f
        val verticalLine = 250f

        dataPengajuan.forEach {
            text(
                paint = paint,
                canvas = canvas,
                color = Color.BLACK,
                text = it.key,
                textSize = 24f,
                typeFace = Typeface.create(Typeface.DEFAULT, Typeface.BOLD),
                xPos = startXPos,
                yPos = startYPos
            )
            canvas.drawLine(startXPos, startYPos + 5, endXPos, startYPos + 5, paint)
            text(
                paint = paint,
                canvas = canvas,
                color = Color.BLACK,
                text = it.value!!,
                textSize = 18f,
                typeFace = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL),
                xPos = verticalLine + 10,
                yPos = startYPos
            )
            startYPos += 40
        }

        canvas.drawLine(verticalLine, 220f , verticalLine, startYPos - 35f, paint)

        document.finishPage(page)

        // Save the PDF using MediaStore.createWriteRequest
        val displayName = "${data.nama} - ${data.ruangan}.pdf"
        val mimeType = "application/pdf"
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, displayName)
            put(MediaStore.MediaColumns.MIME_TYPE, mimeType)
            put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
        }

        val resolver = context.contentResolver
        val uri = resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)

        if (uri != null) {
            val outputStream = resolver.openOutputStream(uri)
            outputStream?.use {
                document.writeTo(it)
                Toast.makeText(context, "Berhasil Download PDF Bukti Peminjaman", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(context, "Error saat mendownload PDF", Toast.LENGTH_SHORT).show()
        }
    } catch (e: IOException) {
        e.printStackTrace()
        Toast.makeText(context, "Error saat mendownload PDF", Toast.LENGTH_SHORT).show()
    } finally {
        document.close()
    }
}

fun text(
    paint: Paint,
    canvas: Canvas,
    color: Int,
    text: String,
    textSize: Float,
    typeFace: Typeface,
    xPos: Float,
    yPos: Float
) {
    paint.color = color
    paint.textSize = textSize
    paint.typeface = typeFace
    canvas.drawText(text, xPos, yPos, paint)
}