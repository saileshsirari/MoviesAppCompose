package apps.sai.com.movieapp.api

import apps.sai.com.compose.model.MovieResponse
import apps.sai.com.movieapp.data.*
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {
    @GET("movie/now_playing")
    suspend fun nowPlaying(
        @Query("page") page: Int,
    ): MovieResponse

    @GET("movie/popular")
    suspend fun popular(
        @Query("page") page: Int,
    ): MovieResponse

    @GET("movie/top_rated")
    suspend fun topRated(
        @Query("page") page: Int,
    ): MovieResponse

    @GET("movie/upcoming")
    suspend fun upcoming(
        @Query("page") page: Int,
    ): MovieResponse

    @GET("genre/movie/list")
    suspend fun genres(): GenreResponse

    @GET("search/movie")
    suspend fun search(
        @Query("page") page: Int,
        @Query("query") query: String,
    ): MovieResponse

    @GET("movie/{id}")
    suspend fun movieDetails(@Path("id") id: Int?): Movie

    companion object {
        private const val BASE_URL =
            "https://api.themoviedb.org/3/"

        fun create(apiKey: String): MovieApi {
            val loggingInterceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

            val okHttpClient = OkHttpClient().newBuilder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(provideAccessTokenInterceptor(apiKey))
                .build()

            return Retrofit.Builder()
                .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .build()
                .create(MovieApi::class.java)
        }

        private fun provideAccessTokenInterceptor(apiKey: String): Interceptor =
            object : Interceptor {
                override fun intercept(chain: Interceptor.Chain): okhttp3.Response {

                    chain.run {
                        val builder = request().newBuilder()
                        val url =
                            request().url.newBuilder().addQueryParameter("api_key", apiKey).build()
                        return proceed(builder.url(url).build())
                    }
                }
            }
    }
}
