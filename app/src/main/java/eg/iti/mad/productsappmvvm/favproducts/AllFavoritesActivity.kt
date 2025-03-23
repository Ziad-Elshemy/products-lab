package eg.iti.mad.productsappmvvm.favproducts

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.newsapp.api.ApiManager
import com.skydoves.landscapist.glide.GlideImage
import eg.iti.mad.coroutineswithretrofitandroomapp.database.MyDatabase
import eg.iti.mad.productsappmvvm.api.ProductsRemoteDataSourceImpl
import eg.iti.mad.productsappmvvm.local.ProductsLocalDataSourceImpl
import eg.iti.mad.productsappmvvm.model.ProductsItem
import eg.iti.mad.productsappmvvm.model.Response
import eg.iti.mad.productsappmvvm.repo.RepositoryImpl


class AllFavoritesActivity : ComponentActivity() {
    private val TAG = "AllFavoritesActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            FavoritesScreen(
                ViewModelProvider(
                    this, factory = AllFavoritesFactory(
                        RepositoryImpl.getInstance(
                            ProductsRemoteDataSourceImpl(ApiManager.getApis()),
                            ProductsLocalDataSourceImpl(
                                MyDatabase.getInstance(this@AllFavoritesActivity).getProductsDao()
                            )
                        )
                    )
                ).get(AllFavoritesViewModel::class.java)
            )
        }

    }
}


@Composable
fun FavoritesScreen(viewModel: AllFavoritesViewModel) {
    viewModel.getProducts()
//    val productsState = viewModel.products.observeAsState()
//    val messageState = viewModel.message.observeAsState()
//    val isLoadingState = viewModel.isLoading.observeAsState()

    val uiState by viewModel.products.collectAsStateWithLifecycle()

    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    Scaffold(
        snackbarHost = { SnackbarHost(snackBarHostState) }
    ) {contentPadding->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(contentPadding)
            .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            when(uiState){
                is Response.Loading ->{
                    LoadingIndicator()
                }

                is Response.Success -> {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(/*productsState.value?.size ?: 0*/(uiState as Response.Success).data.size) { index ->
                            FavoriteItem(/*isLoadingState.value?:false,*/
                                (uiState as Response.Success).data.get(index),
                                onDeleteFromFavClicked = {
                                    viewModel.deleteProductFromFav((uiState as Response.Success).data?.get(index))
                                })
                        }
                    }
                }
                is Response.Failure -> {
                    Text(
                        text = "sorry there is an error",
                        modifier = Modifier.fillMaxSize().wrapContentSize(),
                        fontSize = 22.sp
                    )
                }
            }

            LaunchedEffect(/*messageState.value*/Unit) {
                viewModel.message.collect{message->
                    if (!message.isNullOrBlank()){
                        snackBarHostState.showSnackbar(
                            message = message,
                            duration = SnackbarDuration.Short
                        )
                    }
                }
//                if (!messageState.value.isNullOrBlank()){
//                    snackBarHostState.showSnackbar(
//                        message = messageState.value.toString(),
//                        duration = SnackbarDuration.Short
//                    )
//                }
            }
        }

    }
}

@Composable
fun LoadingIndicator() {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize()
    ){
        CircularProgressIndicator()
    }

}

@Composable
fun FavoriteItem(
    /*isLoading: Boolean,*/
    productsItem: ProductsItem?,
    onDeleteFromFavClicked: ()->Unit
) {
    /*if (isLoading) {
        CircularProgressIndicator()
    } else {*/
        productsItem?.let {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFDFF3FC)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
            Row(
                modifier = Modifier
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                GlideImage(
                    imageModel = { it.thumbnail },
                    modifier = Modifier
                        .height(160.dp)
                        .width(120.dp)
                        .clip(RoundedCornerShape(10.dp))
                )
                Column(modifier = Modifier.padding(10.dp)) {

                    Text(
                        text = it.title ?: "test title", color = Color.Black,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = it.brand ?: "Unknown",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = it.description ?: "test description",
                        maxLines = 2,
                        color = Color.Gray,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "$${it.price}",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF4CAF50)
                    )

                    Button(
                        onClick = onDeleteFromFavClicked, modifier = Modifier
                            .align(alignment = Alignment.CenterHorizontally)
                            .fillMaxSize(fraction = 0.6f),
                        colors = ButtonDefaults.buttonColors(
//                            containerColor = Color.Red
                            containerColor = Color(0xFFE91E63)
                        )
                    ) {
                        Text(
                            text = "Delete",
                            color = Color.White
                        )
                    }
                }

            }
        }
        }
//    }
}