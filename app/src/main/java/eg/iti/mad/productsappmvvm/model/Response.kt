package eg.iti.mad.productsappmvvm.model

sealed class Response {

    data object Loading: Response()
    data class Success(val data: List<ProductsItem>): Response()
    data class Failure(val error: Throwable): Response()

}