package eg.iti.mad.productsappmvvm.favproducts

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import eg.iti.mad.productsappmvvm.model.ProductsItem
import eg.iti.mad.productsappmvvm.model.Response
import eg.iti.mad.productsappmvvm.repo.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class AllFavoritesViewModel(private val repo: Repository) : ViewModel() {
    private val TAG = "AllProductsViewModel"
    private val mutableProducts = MutableStateFlow<Response>(Response.Loading)
    val products = mutableProducts.asStateFlow()

    private val mutableMessage = MutableSharedFlow<String>()
    val message = mutableMessage.asSharedFlow()

//    private val mutableIsLoading: MutableLiveData<Boolean> = MutableLiveData()
//    val isLoading: LiveData<Boolean> = mutableIsLoading

    fun getProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
//                mutableIsLoading.postValue(true)
                val result = repo.getAllProducts(false)
                if (result != null) {

//                    val list: List<ProductsItem> = result
//                    mutableIsLoading.postValue(false)
//                    mutableProducts.postValue(list)

                    result
                        .catch { ex ->
                            mutableProducts.value = Response.Failure(ex)
                            mutableMessage.emit("Error From DB ${ex.message}")
                        }
                        .collect {
//                        mutableIsLoading.postValue(false)
                            mutableProducts.value = Response.Success(it!!)
                        }

                } else {
//                    mutableIsLoading.postValue(false)
//                    mutableMessage.postValue("Try Again")

                    mutableMessage.emit("Try Again")
                }

            } catch (ex: Exception) {
//                mutableIsLoading.postValue(false)
//                mutableMessage.postValue("Error ${ex.message}")
                Log.e(TAG, "AllProductsViewModel: Error ${ex.message}")

                mutableMessage.emit("Error from coroutines ${ex.message}")
            }
        }
    }

    fun deleteProductFromFav(product: ProductsItem?) {
        viewModelScope.launch(Dispatchers.IO) {
            if (product != null) {

                try {
                    val result = repo.removeProduct(product)
                    if (result > 0) {
                        mutableMessage.emit("${product.title} Deleted Successfully!")

                        // no need after using flows
//                        val updatedList = repo.getAllProducts(false)
//                        if (updatedList!=null){
//                            val list: List<ProductsItem> = updatedList
//                            mutableProducts.postValue(list)
//                        }

                    } else {
                        mutableMessage.emit("Product is already deleted res: $result")
                    }
                } catch (ex: Exception) {
                    mutableMessage.emit("Couldn't delete product :${ex.message}")
                }

            } else {
                mutableMessage.emit("Couldn't delete product :Missing data")
            }
        }
    }
}

class AllFavoritesFactory(private val repo: Repository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AllFavoritesViewModel(repo) as T
    }

}