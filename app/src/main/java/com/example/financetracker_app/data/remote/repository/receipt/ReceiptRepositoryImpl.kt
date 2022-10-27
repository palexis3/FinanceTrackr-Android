package com.example.financetracker_app.data.remote.repository.receipt

import android.util.Log
import com.example.financetracker_app.data.models.Receipt
import com.example.financetracker_app.data.models.ReceiptCreate
import com.example.financetracker_app.data.remote.FinanceTrackrApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

private const val TAG = "ReceiptRepositoryImpl"

class ReceiptRepositoryImpl @Inject constructor(
    private val api: FinanceTrackrApi
) : ReceiptRepository {

    override fun getAllReceipts(): Flow<List<Receipt>> =
        flow {
            while (true) {
                val response = api.getAllReceipts()
                // TODO: API should respond back with an empty list if there's no receipt
                val items = if (response.body() != null) {
                    response.body()?.items ?: listOf()
                } else listOf()

                emit(items)
                delay(5_000)
            }
        }.catch { exception ->
            Log.d(TAG, "$TAG getAllReceipts() exception: $exception")
        }

    override fun getReceipt(id: String): Flow<Receipt> =
        flow { api.getReceipt(id) }

    override suspend fun createReceipt(receiptCreate: ReceiptCreate): Boolean {
        val response = api.createReceipt(receiptCreate)
        return response.isSuccessful
    }
}
