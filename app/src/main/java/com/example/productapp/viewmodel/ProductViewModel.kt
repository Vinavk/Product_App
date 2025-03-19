package com.example.productapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.productapp.model.ProductModel
import com.example.productapp.model.ProductRequest
import com.example.productapp.model.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val _productList = MutableStateFlow<List<ProductModel.ProductModelItem>>(emptyList())
    val productList: StateFlow<List<ProductModel.ProductModelItem>> = _productList.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _saveSuccess = MutableStateFlow<Boolean?>(null)
    val saveSuccess: StateFlow<Boolean?> = _saveSuccess.asStateFlow()

    fun fetchProducts(divisionCode: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            val products = repository.getAllData(divisionCode)
            _productList.value = products
            _isLoading.value = false
        }
    }

    fun saveProducts(productRequest: ProductRequest, divisionCode: Int) {
        viewModelScope.launch {
            val success = repository.saveProducts(divisionCode, productRequest)
            if (success) {
                Log.d("ViewModel", "Data saved successfully")
            } else {
                Log.e("ViewModel", "Data saving failed")
            }
        }
    }
}

