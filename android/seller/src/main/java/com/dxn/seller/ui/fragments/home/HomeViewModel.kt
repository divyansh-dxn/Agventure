package com.dxn.seller.ui.fragments.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dxn.data.models.CatalogueProduct
import com.dxn.seller.domain.Repository
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.lang.Exception
import javax.inject.Inject


@HiltViewModel
class HomeViewModel
@Inject
constructor(
    private val repository: Repository,
    private val auth: FirebaseAuth
) : ViewModel() {

    private val _products = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val products: StateFlow<HomeUiState> get() = _products

    init {
        loadProducts()
    }

    private fun loadProducts() {
        try {
            repository.getProducts(auth.uid!!).onEach {
                _products.value = HomeUiState.Success(it)
            }.launchIn(viewModelScope)
        } catch (e: Exception) {
            _products.value =
                HomeUiState.Error(if (e.localizedMessage != null) e.localizedMessage else "Something went wrong")
        }
    }


    sealed class HomeUiState {
        object Loading : HomeUiState()
        data class Success(val movies: List<CatalogueProduct>) : HomeUiState()
        data class Error(val error: String) : HomeUiState()
    }
}