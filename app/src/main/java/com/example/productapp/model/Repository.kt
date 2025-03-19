package com.example.productapp.model


import android.util.Log
import com.example.productapp.retrofit.ApiService
import javax.inject.Inject

class Repository @Inject constructor(private val apiService: ApiService) {

    suspend fun getAllData(divisionCode: Int): List<ProductModel.ProductModelItem> {
        return try {
            val response = apiService.getTaskProducts(divisionCode = divisionCode)
            if (response.isSuccessful) {

                Log.e("vksuccess", "Error fetching data: ")

                response.body() ?: emptyList()
            } else {
                Log.e("vkempty", "Error fetching data: ")

                emptyList()
            }
        } catch (e: Exception) {
            Log.e("vkexception", "Error fetching data: ${e.message}")
            emptyList()
        }
    }

    suspend fun saveProducts(divisionCode: Int, productRequest: ProductRequest): Boolean {
        return try {
            val response = apiService.saveTaskProducts( divisionCode = divisionCode, request =  productRequest)
            if (response.isSuccessful) {
                Log.d("vkRepository", "Products saved successfully")
                Log.d("vkRepository", "${productRequest}")
                true
            } else {
                Log.e("vkRepository", "Failed to save products: ${response.errorBody()?.string()}")
                false
            }
        } catch (e: Exception) {
            Log.e("vkRepository", "Exception occurred: ${e.message}")
            false
        }
    }

}
