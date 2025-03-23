package eg.iti.mad.productsappmvvm.repo

import eg.iti.mad.productsappmvvm.api.ProductsRemoteDataSource
import eg.iti.mad.productsappmvvm.api.ProductsRemoteDataSourceImpl
import eg.iti.mad.productsappmvvm.local.ProductsLocalDataSource
import eg.iti.mad.productsappmvvm.local.ProductsLocalDataSourceImpl
import eg.iti.mad.productsappmvvm.model.ProductsItem
import kotlinx.coroutines.flow.Flow

class RepositoryImpl private constructor(
    private val remoteDataSource: ProductsRemoteDataSource,
    private val localDataSource: ProductsLocalDataSource
) : Repository {
    override suspend fun getAllProducts(isOnline :Boolean): Flow<List<ProductsItem>?> {
        if (isOnline){
            return remoteDataSource.getAllProducts()
//            return localDataSource.getAllProducts()

        }else{
            return localDataSource.getAllProducts()
        }
    }

    override suspend fun addProduct(product: ProductsItem): Long{
        return localDataSource.insertProduct(product)
    }
    override suspend fun removeProduct(product: ProductsItem): Int{
        return localDataSource.deleteProduct(product)
    }

    companion object {
        private var INSTANCE: RepositoryImpl? = null
        fun getInstance(remoteDataSource: ProductsRemoteDataSourceImpl, localDataSource: ProductsLocalDataSourceImpl): RepositoryImpl{
            return INSTANCE ?: synchronized(this){
                val instance = RepositoryImpl(remoteDataSource,localDataSource)
                INSTANCE = instance
                instance
            }
        }
    }

}