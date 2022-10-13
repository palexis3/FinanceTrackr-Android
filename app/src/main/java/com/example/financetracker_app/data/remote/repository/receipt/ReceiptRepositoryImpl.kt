package com.example.financetracker_app.data.remote.repository.receipt

import com.example.financetracker_app.data.models.Receipt
import com.example.financetracker_app.data.models.ReceiptCreate
import com.example.financetracker_app.data.remote.FinanceTrackrApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class ReceiptRepositoryImpl @Inject constructor(
    private val api: FinanceTrackrApi
) : ReceiptRepository {

    override fun getAllReceipts(): Flow<List<Receipt>> =
        flow { api.getAllReceipts() }

    override fun getReceipt(id: String): Flow<Receipt> =
        flow { api.getReceipt(id) }

    override suspend fun createReceipt(receiptCreate: ReceiptCreate): Response<Receipt> =
        api.createReceipt(receiptCreate)
}