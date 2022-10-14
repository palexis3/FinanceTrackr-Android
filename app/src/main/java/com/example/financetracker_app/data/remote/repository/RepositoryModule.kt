package com.example.financetracker_app.data.remote.repository

import com.example.financetracker_app.data.remote.repository.images.ImageRepository
import com.example.financetracker_app.data.remote.repository.images.ImageRepositoryImpl
import com.example.financetracker_app.data.remote.repository.product.ProductRepository
import com.example.financetracker_app.data.remote.repository.product.ProductRepositoryImpl
import com.example.financetracker_app.data.remote.repository.receipt.ReceiptRepository
import com.example.financetracker_app.data.remote.repository.receipt.ReceiptRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
interface RepositoryModule {
    @Binds
    @ViewModelScoped
    fun bindImageRepository(repositoryImpl: ImageRepositoryImpl): ImageRepository

    @Binds
    @ViewModelScoped
    fun bindProductRepository(repositoryImpl: ProductRepositoryImpl): ProductRepository

    @Binds
    @ViewModelScoped
    fun bindReceiptRepository(repositoryImpl: ReceiptRepositoryImpl): ReceiptRepository
}
