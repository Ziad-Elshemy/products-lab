package eg.iti.mad.productsappmvvm.api

import eg.iti.mad.productsappmvvm.model.ProductsItem
import kotlinx.coroutines.flow.Flow

interface ProductsRemoteDataSource {
    suspend fun getAllProducts(): Flow<List<ProductsItem>?>
}