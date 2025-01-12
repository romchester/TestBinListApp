package com.example.binlistapp2.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.binlistapp2.data.BinInfoEntity
import com.example.binlistapp2.data.BinRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(private val repository: BinRepository) : ViewModel() {

    private val _binInfo = MutableStateFlow<BinInfoEntity?>(null)
    val binInfo: StateFlow<BinInfoEntity?> = _binInfo

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _history = MutableStateFlow<List<BinInfoEntity>>(emptyList())
    val history: StateFlow<List<BinInfoEntity>> = _history

    fun fetchBinInfo(bin: String) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val info = repository.getBinInfo(bin)
                if (info != null) {
                    _binInfo.value = BinInfoEntity(
                        bin = bin,
                        scheme = info.scheme,
                        type = info.type,
                        brand = info.brand,
                        prepaid = info.prepaid,
                        countryName = info.country.name,
                        bankName = info.bank.name,
                        bankUrl = info.bank.url,
                        bankPhone = info.bank.phone,
                        bankCity = info.bank.city
                    )
                    repository.saveBinInfoToDatabase(_binInfo.value!!)
                }
                _errorMessage.value = null
            } catch (e: Exception) {
                _errorMessage.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun loadHistory() {
        viewModelScope.launch {
            _history.value = repository.getAllBinInfos()
        }
    }
}

class MainViewModelFactory(private val repository: BinRepository) : androidx.lifecycle.ViewModelProvider.Factory {
    override fun <T : androidx.lifecycle.ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}