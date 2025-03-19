package com.example.productapp.retrofit

import com.example.productapp.model.ApiResponse
import com.example.productapp.model.ProductModel
import com.example.productapp.model.ProductRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


interface ApiService {

    @GET("native_Db_V13.php")
    suspend fun getTaskProducts(
        @Query("axn") action: String = "get/taskproducts",
        @Query("divisionCode") divisionCode: Int
    ): Response<ProductModel>

   @POST("native_Db_V13.php")
   suspend fun saveTaskProducts(
       @Query("axn") axn: String = "save/taskproddets",
       @Query("divisionCode") divisionCode: Int,
       @Body request: ProductRequest
   ): Response<ApiResponse>

}
