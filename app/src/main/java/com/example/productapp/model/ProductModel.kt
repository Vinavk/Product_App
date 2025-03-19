package com.example.productapp.model


class ProductModel : ArrayList<ProductModel.ProductModelItem>() {
    data class ProductModelItem(
        val convQty: String,
        val product_code: String,
        val product_name: String,
        val product_unit: String
    )
}


data class ProductRequest(
    val data: List<ProductItems>
)

data class ProductItems(
    val product_code: String,
    val product_name: String,
    val Product_Qty: Int,
    val Rate: Int,
    val Product_Amount: Int
)


data class ApiResponse(
    val status: String,
    val message: String
)
