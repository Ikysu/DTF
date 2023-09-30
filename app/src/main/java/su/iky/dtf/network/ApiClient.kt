package su.iky.dtf.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import su.iky.dtf.model.Entry


var nextPage = ""
val retrofit: Retrofit = Retrofit.Builder()
    .baseUrl("https://dtf.iky.su")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val service: Server = retrofit.create(Server::class.java)

suspend fun getFeed(): List<Entry> {
    val feed = service.feed(nextPage)
    nextPage=feed.next
    return feed.items
}