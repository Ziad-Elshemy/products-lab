package eg.iti.mad.productsappmvvm.repo

import eg.iti.mad.productsappmvvm.model.ProductsItem
import kotlinx.coroutines.flow.Flow


interface Repository {
    suspend fun getAllProducts(isOnline : Boolean): Flow<List<ProductsItem>?>

    suspend fun addProduct(product: ProductsItem): Long
    suspend fun removeProduct(product: ProductsItem): Int
}