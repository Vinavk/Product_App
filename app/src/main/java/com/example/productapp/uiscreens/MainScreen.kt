package com.example.productapp.uiscreens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.productapp.model.ProductItems
import com.example.productapp.model.ProductModel
import com.example.productapp.model.ProductRequest
import com.example.productapp.ui.theme.Purple80
import com.example.productapp.ui.theme.Delbtnclr
import com.example.productapp.ui.theme.Savebtnclr
import com.example.productapp.viewmodel.ProductViewModel
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductScreen(viewModel: ProductViewModel ) {
    val productList by viewModel.productList.collectAsState()
    val isLoading = remember { mutableStateOf(true) }
    val productItems = remember { mutableStateListOf<ProductModel.ProductModelItem>() }
    var context = LocalContext.current

    LaunchedEffect(true) {
        viewModel.fetchProducts(divisionCode = 258)
    }

    LaunchedEffect(productList) {
        if (productList.isNotEmpty()) {
            productItems.clear()
            productItems.addAll(productList)
            isLoading.value = false
        }
    }

    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .background(
                        Brush.horizontalGradient(
                            colors = listOf(
                                Color(0xFFCBABF7),
                                Color(0xFF82D8F3)
                            )
                        )
                    )
            ) {
                TopAppBar(
                    title = { Text("Products") },
                    colors = TopAppBarColors(containerColor = Color.Transparent, actionIconContentColor = Color.Transparent, navigationIconContentColor = Color.Transparent,
                        scrolledContainerColor = Color.Transparent, titleContentColor = Color.Black)

                )
            }
        },
        bottomBar = {
            Button(
                onClick = {
                    if(productItems.isNotEmpty()){
                        val updatedList = productItems.map { product ->
                            var randomAmount = Random.nextInt(100,2500)
                            ProductItems(
                                product_code = product.product_code,
                                product_name = product.product_name,
                                Product_Qty = product.convQty.toIntOrNull()
                                    ?: 0,
                                Rate = randomAmount,
                                Product_Amount = (product.convQty.toIntOrNull()
                                    ?: 0) * randomAmount
                            )
                        }

                        val productRequest = ProductRequest(data = updatedList)

                        viewModel.saveProducts(divisionCode = 258, productRequest = productRequest)

                    }
                    else{
                        Toast.makeText(context,"Nothing to Save", Toast.LENGTH_LONG).show()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                ,
                colors = ButtonDefaults.buttonColors(containerColor = Savebtnclr)
            ) {
                Text("Save")
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            when {
                isLoading.value -> {
                    CircularProgressIndicator()
                }

                productItems.isNotEmpty() -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(productItems) { product ->
                            ProductItem(product, onQuantityChange = { newQty ->
                                val index = productItems.indexOf(product)
                                if (index != -1) {
                                    productItems[index] = product.copy(convQty = newQty.toString())
                                }
                            },

                                onDelete = {
                                    productItems.remove(product)
                                })
                        }
                    }
                }

                else -> {
                    Text(" Products are Not Available", fontSize = 18.sp)
                }
            }
        }
    }
}

@Composable
fun ProductItem(
    product: ProductModel.ProductModelItem,
    onQuantityChange: (Int) -> Unit,
    onDelete: () -> Unit,
) {
    var incrementValue by remember(product.product_code) { mutableStateOf(0) }
    var decrementValue by remember(product.product_code) { mutableStateOf(0) }
    var quantity by remember(product.product_code) {
        mutableStateOf(product.convQty.toIntOrNull() ?: 0)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 6.dp,
                shape = RoundedCornerShape(8.dp),
                spotColor = Color.Gray,
                ambientColor = Color.Black.copy(alpha = 0.2f)
            )
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(Purple80, Color(0xFF9575CD))
                ),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = product.product_name,
            color = Color.White,
            fontSize = 12.sp,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.width(100.dp)
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            modifier = Modifier
                .weight(1f)
                .background(Color.Transparent)
        ) {
            Text(
                text = decrementValue.toString(),
                color = Color.White,
                fontSize = 14.sp,
                modifier = Modifier.width(30.dp),
                textAlign = TextAlign.Center
            )

            Button(
                onClick = {
                    if (quantity > 0) {
                        quantity -= 1
                        decrementValue += 1
                        onQuantityChange(quantity)
                    }
                },
                modifier = Modifier.size(40.dp),
                contentPadding = PaddingValues(0.dp),
                shape = CircleShape
            ) {
                Text("-", fontSize = 18.sp)
            }

            Text(
                text = quantity.toString(),
                color = Color.White,
                fontSize = 16.sp,
                modifier = Modifier.width(30.dp),
                textAlign = TextAlign.Center
            )

            Button(
                onClick = {
                    quantity += 1
                    incrementValue += 1
                    onQuantityChange(quantity)
                },
                modifier = Modifier.size(40.dp),
                contentPadding = PaddingValues(0.dp),
                shape = CircleShape
            ) {
                Text("+", fontSize = 18.sp)
            }

            Text(
                text = incrementValue.toString(),
                color = Color.White,
                fontSize = 14.sp,
                modifier = Modifier.width(30.dp),
                textAlign = TextAlign.Center
            )
        }


        Button(
            onClick = {
                incrementValue = 0
                decrementValue = 0
                onDelete()
            },
            colors = ButtonDefaults.buttonColors(Delbtnclr),
            modifier = Modifier
                .width(80.dp)
                .height(35.dp)
        ) {
            Text("Delete", fontSize = 11.sp, color = Color.White)
        }
    }
}
