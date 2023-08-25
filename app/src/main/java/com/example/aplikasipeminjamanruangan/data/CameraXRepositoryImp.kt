package com.example.aplikasipeminjamanruangan.data

import android.content.ContentValues
import android.content.Context
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.example.aplikasipeminjamanruangan.domain.model.CameraXModel
import com.example.aplikasipeminjamanruangan.domain.repository.ICustomCameraRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

class CameraXRepositoryImp @Inject constructor(
    private val cameraProvider: ProcessCameraProvider,
    private val selector: CameraSelector,
    private val preview: Preview,
    private val imageAnalysis: ImageAnalysis,
    private val imageCapture: ImageCapture
) : ICustomCameraRepository {
    override suspend fun captureAndSaveImage(context: Context): Flow<Resource<CameraXModel>> =
        callbackFlow {
            trySend(Resource.Loading)
            //for file name
            val name = SimpleDateFormat(
                "yyyy-MM-dd-HH-mm-ss-SSS",
                Locale.US
            ).format(System.currentTimeMillis())


            // for storing
            val contentValues = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, "$name.jpg")
                put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
                if (Build.VERSION.SDK_INT > 28) {
                    put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/My-Camera-App-Images")
                }
            }

            // for capture output
            val outputOptions = ImageCapture.OutputFileOptions
                .Builder(
                    context.contentResolver,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    contentValues
                ).build()

            imageCapture.takePicture(
                outputOptions,
                ContextCompat.getMainExecutor(context),
                object : ImageCapture.OnImageSavedCallback {
                    override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                        val uri = outputFileResults.savedUri
                        Toast.makeText(
                            context,
                            "Saved image ${uri?.path}",
                            Toast.LENGTH_LONG
                        ).show()
                        Log.d("URL FROM CAMERA",uri?.path!!)
                        trySend(
                            Resource.Success(
                                CameraXModel(
                                    uri = uri
                                )
                            )
                        )
                    }

                    override fun onError(exception: ImageCaptureException) {
                        Toast.makeText(
                            context,
                            "some error occurred ${exception.message}",
                            Toast.LENGTH_LONG
                        ).show()
                        trySend(Resource.Error(Throwable(exception.message)))
                    }

                }
            )
            awaitClose { close() }
        }

    override suspend fun showCameraPreview(
        previewView: PreviewView,
        lifecycleOwner: LifecycleOwner
    ) {
        preview.setSurfaceProvider(previewView.surfaceProvider)
        try {
            cameraProvider.unbindAll()
            cameraProvider.bindToLifecycle(
                lifecycleOwner,
                selector,
                preview,
                imageAnalysis,
                imageCapture
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}