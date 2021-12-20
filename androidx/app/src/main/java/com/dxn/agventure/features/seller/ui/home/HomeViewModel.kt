package com.dxn.agventure.features.seller.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dxn.agventure.features.seller.domain.Repository
import com.dxn.data.models.CatalogueProduct
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
@Inject
constructor(
    private val repository: Repository,
) : ViewModel() {

    private val _products = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val products: StateFlow<HomeUiState> get() = _products


    init {
        loadProducts()
    }

    private fun loadProducts() {
        viewModelScope.launch {
            val user = repository.getLoggedInUser()
            try {
                repository.getProducts(user.phoneNumber).onEach {
                    Log.d(TAG, "loadProducts: $it")
                    _products.value = HomeUiState.Success(it)
                }.launchIn(viewModelScope)
            } catch (e: Exception) {
                _products.value =
                    HomeUiState.Error(if (e.localizedMessage != null) e.localizedMessage else "Something went wrong")
            }
        }

    }


    sealed class HomeUiState {
        object Loading : HomeUiState()
        data class Success(val products: List<CatalogueProduct>) : HomeUiState()
        data class Error(val error: String) : HomeUiState()
    }

    companion object {
        const val TAG = "HOME_VIEW_MODEL"
    }
}