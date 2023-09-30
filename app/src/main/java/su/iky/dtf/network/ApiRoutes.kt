package su.iky.dtf.network

import retrofit2.http.GET
import retrofit2.http.Path
import su.iky.dtf.model.DTFFeedAPI

interface Server {
    @GET("{next}")
    suspend fun feed(@Path("next") next: String): DTFFeedAPI
}