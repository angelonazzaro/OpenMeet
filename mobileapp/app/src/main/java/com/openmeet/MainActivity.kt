package com.openmeet

import Meeter
import Report
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.openmeet.ui.theme.OpenMeetTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OpenMeetTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    //Greeting("Android | IOS")
                    val meeter = Meeter()
                    meeter.name = "Franceschino"
                    val segn = Report()
                    segn.reason = "Non ha fatto nulla"
                    MessageCard(Message("//Autore: Francesco Granozio", segn.toString()))



                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

data class Message(val author: String, val body: String)

@Composable
fun MessageCard(msg: Message) {

    Column {
        Text(text = msg.author)
        Text(text = msg.body)
    }

}

@Preview
@Composable
fun PreviewMessageCard(){
    MessageCard(msg = Message("//Autore: Francesco Granozio", "Test bellissimo"))
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    OpenMeetTheme {
        Greeting("Android")
    }
}