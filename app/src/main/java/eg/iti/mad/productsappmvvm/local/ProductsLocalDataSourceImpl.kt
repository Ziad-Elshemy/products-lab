package eg.iti.mad.productsappmvvm.local

import eg.iti.mad.coroutineswithretrofitandroomapp.database.ProductsDao
import eg.iti.mad.productsappmvvm.model.ProductsItem
import kotlinx.coroutines.flow.Flow

class ProductsLocalDataSourceImpl(private val dao:ProductsDao) : ProductsLocalDataSource {
    override suspend fun getAllProducts():Flow<List<ProductsItem>>{
        return dao.getAllProducts()
    }

    override suspend fun insertProduct(product: ProductsItem):Long{
        return dao.insert(product)
    }

    override suspend fun deleteProduct(product: ProductsItem?):Int{
        return if (product != null){
            dao.delete(product)
        }else{
            -1
        }
    }
}