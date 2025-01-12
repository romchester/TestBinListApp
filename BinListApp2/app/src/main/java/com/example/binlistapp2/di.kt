package com.example.binlistapp2

import com.example.binlistapp2.data.BinRepository
import com.example.binlistapp2.data.BinRepositoryImpl
import com.example.binlistapp2.viewmodel.MainViewModel
import org.koin.dsl.module

val appModule = module {
    single<BinRepository> { BinRepositoryImpl() }
    viewModel { MainViewModel(get()) }
}