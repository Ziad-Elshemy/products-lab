package eg.iti.mad.coroutineswithretrofitandroomapp.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import eg.iti.mad.productsappmvvm.model.ProductsItem
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductsDao {
    @Query("select * from products_tabLe")
    fun getAllProducts(): Flow<List<ProductsItem>> // Listening

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(product: ProductsItem):Long

    @Update
    suspend fun update(product: ProductsItem)

    @Delete
    suspend fun delete(product: ProductsItem):Int
}