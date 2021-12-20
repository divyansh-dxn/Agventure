package com.dxn.agventure.features.seller.ui.add

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dxn.agventure.features.seller.domain.Repository
import com.dxn.data.models.CatalogueProduct
import com.dxn.data.models.Product
import com.dxn.data.models.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddViewModel
@Inject
constructor(
    private val repository: Repository
) : ViewModel() {

    private val _products = MutableStateFlow<List<Product>>(listOf())
    val products : StateFlow<List<Product>> get() = _products

    private val _isLoading = MutableStateFlow(false)
    val isLoading : StateFlow<Boolean> = _isLoading

    init {
        loadProducts()
    }

    fun addProduct(product: CatalogueProduct) {
        _isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val user = getLoggedInUser()
            repository.addProducts(user.phoneNumber,product)
            _isLoading.value = false
        }
    }

    suspend fun getLoggedInUser() = repository.getLoggedInUser()

    private fun loadProducts() {
        _isLoading.value = true
        repository.getAllProducts().onEach {  p ->
            Log.d(TAG, "loadProducts: $p")
            _products.value = p
        }.launchIn(viewModelScope)
        _isLoading.value = false
    }

    companion object {
        const val TAG = "ADD_VIEW_MODEL"
    }

}