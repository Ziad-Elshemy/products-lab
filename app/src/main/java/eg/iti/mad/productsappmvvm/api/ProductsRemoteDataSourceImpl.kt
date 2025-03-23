package eg.iti.mad.productsappmvvm.api

import com.example.newsapp.api.WebServices
import eg.iti.mad.productsappmvvm.model.ProductsItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class ProductsRemoteDataSourceImpl(private val services: WebServices) : ProductsRemoteDataSource {
    override suspend fun getAllProducts(): Flow<List<ProductsItem>?>{
        val response = services.getProducts().products
        return flowOf(response)
//        return services.getProducts().products
    }
}