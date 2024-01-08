package com.kaushalvasava.apps.xml_and_jetpackcompose_interoperability.ui.screen

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.view.View
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.zxing.ResultPoint
import com.google.zxing.client.android.BeepManager
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult
import com.journeyapps.barcodescanner.camera.CameraSettings
import com.kaushalvasava.apps.xml_and_jetpackcompose_interoperability.R
import com.kaushalvasava.apps.xml_and_jetpackcompose_interoperability.databinding.BarcodeLayoutBinding
import com.kaushalvasava.apps.xml_and_jetpackcompose_interoperability.ui.theme.ComposeAppTheme

@Composable
fun ScanScreen() {
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val context = LocalContext.current
        AndroidView(factory = {
            View.inflate(it, R.layout.barcode_layout, null)
        },
            update = {
                val beepManager = BeepManager(context.findActivity())
                beepManager.isBeepEnabled = true
                beepManager.isVibrateEnabled = true
                val binding = BarcodeLayoutBinding.bind(it)
                binding.barcodeView.resume()
                val s = CameraSettings()
                s.requestedCameraId = 0 // front/back/etc
                binding.barcodeView.cameraSettings = s
                binding.barcodeView.decodeSingle(object : BarcodeCallback {
                    override fun barcodeResult(result: BarcodeResult?) {
                        beepManager.playBeepSound()
                        Toast.makeText(context, "${result?.text}", Toast.LENGTH_SHORT).show()
                        try {
                            binding.barcodeView.pause()
                        } catch (e: Exception) {
                            Toast.makeText(context, "Invalid code", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun possibleResultPoints(resultPoints: MutableList<ResultPoint>?) {
                        super.possibleResultPoints(resultPoints)
                    }
                })
            }
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text("OR")
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = {
            // no-op
        }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_image),
                contentDescription = null,
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(stringResource(R.string.select_code_from_device))
        }
    }
}

fun Context.findActivity(): Activity {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return context
        context = context.baseContext
    }
    throw IllegalStateException("no activity")
}

@Composable
@Preview
fun Preview() {
    ComposeAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            ScanScreen()
        }
    }
}
