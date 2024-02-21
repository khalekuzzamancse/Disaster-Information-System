package com.khalekuzzaman.just.cse.datacollect

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.onUpload
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.client.statement.HttpResponse
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.launch

@Composable
fun PickImage() {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val launcher = rememberLauncherForActivityResult(
        contract = (ActivityResultContracts.GetContent()),
        onResult = { uri ->
            uri?.let {
                scope.launch {
                    val inputStream = context.contentResolver.openInputStream(it)
                    val imageByteArray = inputStream?.readBytes()
                    inputStream?.close()
                    uploadImage(
                        text = "", byteArray = imageByteArray
                    )

                }
            }

        }
    )

    Button(onClick = {
        launcher.launch("image/jpeg")
    }) {
        Text(text = "Upload Image")
    }


}

suspend fun uploadImage(text: String, byteArray: ByteArray?): Boolean {
    return try {
        if (byteArray != null) {
            val httpClient = HttpClient {
                install(ContentNegotiation) {
                    json()
                }
            }
            println("UploadRequest:$byteArray")
            val response: HttpResponse = httpClient.submitFormWithBinaryData(
                url = "",
                formData = formData {
                    //the line below is just an example of how to send other parameters in the same request
                    append("text", text)
                    append("image", byteArray, Headers.build {
                        append(HttpHeaders.ContentType, "image/jpeg")
                        append(HttpHeaders.ContentDisposition, "filename=image.png")
                    })
                }
            ) {
                onUpload { bytesSentTotal, contentLength ->
                    println("Sent $bytesSentTotal bytes from $contentLength")
                }
            }
        }
        true
    } catch (ex: Exception) {
//im using timber for logs, you can always replace this with Log.d
        Log.d("ONError", "error ${ex.message}")
        false
    }
}
