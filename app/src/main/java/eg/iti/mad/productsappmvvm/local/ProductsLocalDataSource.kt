package eg.iti.mad.productsappmvvm.local

import eg.iti.mad.productsappmvvm.model.ProductsItem
import kotlinx.coroutines.flow.Flow


interface ProductsLocalDataSource {
    suspend fun getAllProducts(): Flow<List<ProductsItem>>
    suspend fun insertProduct(product: ProductsItem): Long
    suspend fun deleteProduct(product: ProductsItem?): Int
}