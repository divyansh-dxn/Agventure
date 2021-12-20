package com.dxn.seller.ui.fragments.add

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dxn.data.models.CatalogueProduct
import com.dxn.data.models.Product
import com.dxn.data.models.User
import com.dxn.seller.domain.Repository
import com.dxn.seller.ui.fragments.home.HomeViewModel
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
    val products : SharedFlow<List<Product>> get() = _products

    private val _loggedInUser = MutableStateFlow<User?>(null)
    val loggedInUser : SharedFlow<User?> get() = _loggedInUser

    init {
        loadProducts()
        loadUser()
    }

    fun addProduct(product: CatalogueProduct) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addProducts(product)
        }
    }

    private fun loadUser() {
        viewModelScope.launch {
            _loggedInUser.value =  repository.getLoggedInUser()
        }
    }


    private fun loadProducts() {
        Log.d(TAG, "loadProducts: ")
        repository.getAllProducts().onEach {  p ->
            Log.d(TAG, "loadProducts: $p")
            _products.value = p
        }.launchIn(viewModelScope)
    }

    companion object {
        const val TAG = "ADD_VIEW_MODEL"
    }

}