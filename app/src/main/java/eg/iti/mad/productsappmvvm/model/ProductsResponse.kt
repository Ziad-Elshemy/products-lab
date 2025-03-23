package eg.iti.mad.productsappmvvm.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class ProductsResponse(

	@field:SerializedName("total")
	val total: Int? = null,

	@field:SerializedName("limit")
	val limit: Int? = null,

	@field:SerializedName("skip")
	val skip: Int? = null,

	@field:SerializedName("products")
	val products: List<ProductsItem>? = null
)

data class Dimensions(

	@field:SerializedName("depth")
	val depth: Any? = null,

	@field:SerializedName("width")
	val width: Any? = null,

	@field:SerializedName("height")
	val height: Any? = null
)

data class ReviewsItem(

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("reviewerName")
	val reviewerName: String? = null,

	@field:SerializedName("reviewerEmail")
	val reviewerEmail: String? = null,

	@field:SerializedName("rating")
	val rating: Int? = null,

	@field:SerializedName("comment")
	val comment: String? = null
)

@Entity(tableName = "products_table")
data class ProductsItem(

	@Ignore
	@field:SerializedName("images")
	val images: List<String?>? = null,

	@field:SerializedName("thumbnail")
	var thumbnail: String? = null,

	@field:SerializedName("minimumOrderQuantity")
	var minimumOrderQuantity: Int? = null,

	@field:SerializedName("rating")
	var rating: Float? = null,

	@field:SerializedName("returnPolicy")
	var returnPolicy: String? = null,

	@field:SerializedName("description")
	var description: String? = null,

	@field:SerializedName("weight")
	var weight: Int? = null,

	@field:SerializedName("warrantyInformation")
	var warrantyInformation: String? = null,

	@field:SerializedName("title")
	var title: String? = null,

	@Ignore
	@field:SerializedName("tags")
	val tags: List<String?>? = null,

	@field:SerializedName("discountPercentage")
	var discountPercentage: Float? = null,

	@Ignore
	@field:SerializedName("reviews")
	val reviews: List<ReviewsItem?>? = null,

	@field:SerializedName("price")
	var price: Float? = null,

	@Ignore
	@field:SerializedName("meta")
	val meta: Meta? = null,

	@field:SerializedName("shippingInformation")
	var shippingInformation: String? = null,

	@PrimaryKey
	@field:SerializedName("id")
	var id: Int? = null,

	@field:SerializedName("availabilityStatus")
	var availabilityStatus: String? = null,

	@field:SerializedName("category")
	var category: String? = null,

	@field:SerializedName("stock")
	var stock: Int? = null,

	@field:SerializedName("sku")
	var sku: String? = null,

	@Ignore
	@field:SerializedName("dimensions")
	val dimensions: Dimensions? = null,

	@field:SerializedName("brand")
	var brand: String? = null
)



data class Meta(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("qrCode")
	val qrCode: String? = null,

	@field:SerializedName("barcode")
	val barcode: String? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)
