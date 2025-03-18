package com.example.productapp.viewmodel

import android.util.Log
import com.example.productapp.model.ProductModel
import com.example.productapp.retrofit.ApiService
import javax.inject.Inject

class Repository @Inject constructor(private val apiService: ApiService) {

    suspend fun getAllData(divisionCode: Int): List<ProductModel.ProductModelItem> {
        return try {
            val response = apiService.getTaskProducts(divisionCode = divisionCode)
            if (response.isSuccessful) {

                Log.e("vinasuccess", "Error fetching data: ")

                response.body() ?: emptyList()
            } else {
                Log.e("vinaempty", "Error fetching data: ")

                emptyList()
            }
        } catch (e: Exception) {
            Log.e("vinaexception", "Error fetching data: ${e.message}")
            emptyList()
        }
    }
}
