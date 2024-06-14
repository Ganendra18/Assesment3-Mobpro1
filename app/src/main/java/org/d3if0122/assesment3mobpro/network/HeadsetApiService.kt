package org.d3if0122.assesment3mobpro.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.d3if0122.assesment3mobpro.model.Headset
import org.d3if0122.assesment3mobpro.model.OpStatus
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

private const val BASE_URL = "https://unspoken.my.id/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface HeadsetApiService {
    @GET("api_kalla.php")
    suspend fun getHeadset(
        @Header("Authorization") userId: String
    ): List<Headset>

    @Multipart
    @POST("api_kalla.php")
    suspend fun postHeadset(
        @Header("Authorization") userId: String,
        @Part("nama") nama: RequestBody,
        @Part("merek") merek: RequestBody,
        @Part image: MultipartBody.Part
    ): OpStatus

    @DELETE("api_kalla.php")
    suspend fun deleteHeadset(
        @Header("Authorization") userId: String,
        @Query("id") id: String
    ): OpStatus

}

object HeadsetApi {
    val service: HeadsetApiService by lazy {
        retrofit.create(HeadsetApiService::class.java)
    }

    fun getHeadsetByUrl(imageId: String): String{
        return "${BASE_URL}image.php?id=$imageId"
    }
}

enum class ApiStatus {LOADING, SUCCESS, FAILED}