package eg.iti.mad.coroutineswithretrofitandroomapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import eg.iti.mad.productsappmvvm.model.ProductsItem

@Database(entities = arrayOf(ProductsItem::class), version = 1)
abstract class MyDatabase: RoomDatabase() {
    abstract fun getProductsDao(): ProductsDao
    companion object{
        @Volatile
        private var INSTANCE: MyDatabase? = null

        fun getInstance(context: Context):MyDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context, MyDatabase::class.java, "products_db")
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}