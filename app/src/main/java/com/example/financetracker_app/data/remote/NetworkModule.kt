package com.example.financetracker_app.data.remote

import com.example.financetracker_app.BuildConfig
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideFinanceTrackrApi(): FinanceTrackrApi {
        val moshi = Moshi.Builder().build()

        return Retrofit.Builder()
            .baseUrl(BuildConfig.FINANCE_TRACKR_API)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(FinanceTrackrApi::class.java)
    }
}
