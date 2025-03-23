package eg.iti.mad.productsappmvvm

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import eg.iti.mad.productsappmvvm.allproducts.AllProductsActivity
import eg.iti.mad.productsappmvvm.favproducts.AllFavoritesActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            MainScreen(this,onClickFinish= {
                finish()
            })

        }
    }
}

//@Preview
@Composable
fun MainScreen(context: Context, onClickFinish: () -> Unit) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(30.dp))

        Image(
            painter = painterResource(id = R.drawable.app_logo),
            contentDescription = "App Logo",
//            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(280.dp)
                .width(450.dp)
                .padding(bottom = 12.dp)
        )

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                val intent = Intent(context, AllProductsActivity::class.java)
                context.startActivity(intent)
            },
            modifier = Modifier.fillMaxWidth(0.9f),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF3A7A9F)
            )
        ) {
            Text("All Products")
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                val intent = Intent(context, AllFavoritesActivity::class.java)
                context.startActivity(intent)
            },
            modifier = Modifier.fillMaxWidth(0.9f),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF3A7A9F)
            )
        ) {
            Text("Favorite")
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                onClickFinish()
            },
            modifier = Modifier.fillMaxWidth(0.9f),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFC65450)
            )
        ) {
            Text("Exit")
        }

        Spacer(modifier = Modifier.weight(4f)) //weight the space between the top spacer and this bottom spacer
    }
}
