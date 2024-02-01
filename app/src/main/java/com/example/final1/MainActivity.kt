package com.example.final1

/*
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Final1Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                }
            }
        }
    }
}*/
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.final1.ui.theme.Final1Theme
import com.google.android.gms.vision.Frame
import com.google.android.gms.vision.text.TextRecognizer
import java.io.InputStream


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Final1Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val bitmap = loadBitmapFromRes(R.drawable.img2)
                    val recognizedText = performTextRecognition(bitmap)
                    DisplayText(recognizedText)
                }
            }
        }
    }

    private fun loadBitmapFromRes(resId: Int): android.graphics.Bitmap {
        val inputStream: InputStream = resources.openRawResource(resId)
        return BitmapFactory.decodeStream(inputStream)
    }

    private fun performTextRecognition(bitmap: android.graphics.Bitmap): String {
        val textRecognizer = TextRecognizer.Builder(applicationContext).build()
        val frame = Frame.Builder().setBitmap(bitmap).build()
        val textBlocks = textRecognizer.detect(frame)

        val recognizedText = StringBuilder()
        for (i in 0 until textBlocks.size()) {
            val textBlock = textBlocks.valueAt(i)
            recognizedText.append(textBlock.value)
        }
        textRecognizer.release() // Release resources
        return recognizedText.toString()
    }

    @Composable
    fun DisplayText(text: String) {
        Column(
            modifier = Modifier.padding(16.dp)
                .background(Color.Blue)
        ) {
            Image(
                painter = painterResource(id = R.drawable.img2),
                contentDescription = "Image with recognized text",
                modifier = Modifier.fillMaxWidth()
            )
            Text(text = text)
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        Final1Theme {
            DisplayText("Recognized Text Here")
        }
    }
}
