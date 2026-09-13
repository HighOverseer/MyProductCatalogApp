package com.fajar.myproductcatalogapp.product_detail.presentation

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fajar.myproductcatalogapp.core.domain.model.Result
import com.fajar.myproductcatalogapp.core.ui.mapper.DefaultErrorMapper
import com.fajar.myproductcatalogapp.core.ui.model.UIText
import com.fajar.myproductcatalogapp.product_detail.domain.ProductDetailRepository
import com.fajar.myproductcatalogapp.product_detail.presentation.mapper.DUIMapper
import com.fajar.myproductcatalogapp.product_detail.presentation.model.ProductDetailDUI
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@Immutable
internal data class ProductDetailUIState(
    val isLoading: Boolean = false,
    val productDetailDUI: ProductDetailDUI = ProductDetailDUI(),
    val errorUserMessage: UIText? = null
)

internal class ProductDetailViewModel(
    private val repository: ProductDetailRepository,
    private val duiMapper: DUIMapper,
    private val defaultErrorMapper: DefaultErrorMapper,
    private val productId: Long
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProductDetailUIState())
    val uiState = _uiState.asStateFlow()

    @Volatile
    private var hasBeenInitialized = false
    fun initializeIfNeeded() {
        if (hasBeenInitialized) return

        hasBeenInitialized = true
        loadProductDetail()
    }

    private var loadProductDetailJob: Job? = null
    private fun loadProductDetail() {
        if (loadProductDetailJob?.isActive == true) return

        loadProductDetailJob = viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            when (val result = repository.getProductDetail(productId)) {
                is Result.Success -> {
                    val productDetailDUI = duiMapper
                        .mapProductDetailToDUI(result.data)
                    _uiState.update { it.copy(productDetailDUI = productDetailDUI) }
                }

                is Result.Error -> {
                    val errorUserMessage = defaultErrorMapper
                        .getErrorStringResource(result)
                    _uiState.update { it.copy(errorUserMessage = errorUserMessage) }
                }
            }

        }.also { job ->
            job.invokeOnCompletion {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }
}