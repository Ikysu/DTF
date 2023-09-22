package su.iky.dtf

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import su.iky.dtf.ui.theme.DTFTheme

interface Server {
    @GET("{next}")
    fun feed(@Path("next") next: String): Call<DTFFeed>
}


var nextPage = "";
val retrofit = Retrofit.Builder()
    .baseUrl("https://dtf-web-view.ikysu.workers.dev/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()
val service: Server =
    retrofit.create(Server::class.java)

fun getPage() {
    val output: Call<DTFFeed> =
        service.feed(nextPage);
    output.enqueue(object : Callback<DTFFeed> {
        override fun onResponse(
            call: Call<DTFFeed>,
            response: Response<DTFFeed>
        ) {
            val body = response.body()!!;
            nextPage = body.next;


        }

        override fun onFailure(
            call: Call<DTFFeed>,
            t: Throwable
        ) {
            Log.d("GGGGGGGGGGGG", "dfgdfg");
        }
    })
}

@SuppressLint("StaticFieldLeak")
var ctx: Context? = null;

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ctx = applicationContext;
        setContent {
            DTFTheme {
                App()
            }
        }
    }
}

//@Composable
//fun Greeting(name: String, modifier: Modifier = Modifier) {
//    Text(
//            text = "Hello $name!",
//            modifier = modifier
//    )
//}

@Preview()
@Composable
fun App() {
    DTFTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            PostCard("Hi")
        }
    }
}


@Composable
fun PostCard(nd: NewsData) {
    Row {
        AsyncImage(
            model = nd.author.avatar,
            contentDescription = "",
        )

        Column {
            Text(text = nd.author.name)
            Text(text = nd.title)
        }

    }

}

interface DTFFeed {
    val items: NewsData;
    val next: String;
}

interface NewsData {
    val author: Author;
    val theme: Theme;
    val title: String;
    val media: Media;
}

interface Author {
    val name: String;
    val avatar: String;
}

interface Theme {
    val name: String;
}

interface Media {
    val preview: String;
    val url: String;
    val type: String;
}

