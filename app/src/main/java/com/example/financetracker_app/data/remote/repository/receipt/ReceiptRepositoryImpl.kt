package com.example.financetracker_app.data.remote.repository.receipt

import com.example.financetracker_app.data.models.Receipt
import com.example.financetracker_app.data.models.ReceiptCreate
import com.example.financetracker_app.data.remote.FinanceTrackrApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReceiptRepositoryImpl @Inject constructor(
    private val api: FinanceTrackrApi
) : ReceiptRepository {

    override fun getAllReceipts(): Flow<List<Receipt>> {
        TODO("Not yet implemented")
    }

    override fun getReceipt(id: String): Flow<Receipt> {
        TODO("Not yet implemented")
    }

    override fun createReceipt(receiptCreate: ReceiptCreate): Flow<Receipt> {
        TODO("Not yet implemented")
    }
}