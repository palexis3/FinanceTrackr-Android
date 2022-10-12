package com.example.financetracker_app.data.remote.repository.receipt

import com.example.financetracker_app.data.models.Receipt
import com.example.financetracker_app.data.models.ReceiptCreate
import kotlinx.coroutines.flow.Flow

interface ReceiptRepository {
    fun getAllReceipts(): Flow<List<Receipt>>
    fun getReceipt(id: String): Flow<Receipt>
    fun createReceipt(receiptCreate: ReceiptCreate): Flow<Receipt>
}