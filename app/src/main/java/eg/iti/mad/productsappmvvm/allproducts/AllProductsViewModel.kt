package eg.iti.mad.productsappmvvm.allproducts

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
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

class AllProductsViewModel(private val repo:Repository): ViewModel() {
    private val TAG = "AllProductsViewModel"
    private val _productsList = MutableStateFlow<Response>(Response.Loading)
    val productsList = _productsList.asStateFlow()

    private val mutableMessage = MutableSharedFlow<String>()
    val message = mutableMessage.asSharedFlow()

    fun getProducts(){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = repo.getAllProducts(true)
                Log.d(TAG, "API Response: ${Gson().toJson(result)}")
                if (result != null){

                    result
                        .catch {ex ->
                            _productsList.value = Response.Failure(ex)
                            mutableMessage.emit("Error From Api ${ex.message}")
                        }
                        .collect{
                            if (it!=null){
                                _productsList.value = Response.Success(it)
                                Log.d(TAG, "getProducts: success")
                            }

                        }

                }else{
                    mutableMessage.emit("Try Again")
                }

            }catch (ex:Exception){
                mutableMessage.emit("Error from coroutines ${ex.message}")
                Log.e(TAG, "AllProductsViewModel: Error ${ex.message}" )
            }
        }
    }

    fun addProductToFav(product: ProductsItem?){
        viewModelScope.launch(Dispatchers.IO) {
            if (product!=null){
                try {
                    val result = repo.addProduct(product)
                    if (result>0){
                        mutableMessage.emit("${product.title} Added Successfully!")
                        Log.d(TAG, "addProductToFav: success")
                    }else{
                        mutableMessage.emit("Product is already in Fav")
                        Log.d(TAG, "addProductToFav: already in Fav")
                    }
                }catch (ex:Exception){
                    mutableMessage.emit("Couldn't added product :${ex.message}")
                    Log.d(TAG, "addProductToFav: Couldn't added")
                }

            }else{
                mutableMessage.emit("Couldn't added product :Missing data")
                Log.d(TAG, "addProductToFav: :Missing data")
            }
        }
    }
}

class AllProductsFactory(private val repo: Repository): ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AllProductsViewModel(repo) as T
    }

}