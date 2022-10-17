package com.example.mockvakotlin

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.mockvakotlin.retrofit.ApiService
import com.example.mockvakotlin.sharedpref.EncryptSharedPref
import kotlinx.android.synthetic.main.fragment_history.*
import model.LogTransactionResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HistoryFragment : Fragment() {

    private lateinit var encryptSharedPref: EncryptSharedPref

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        encryptSharedPref = EncryptSharedPref(view.context)

        val sessionId = encryptSharedPref.getSessionId()
        val accountId = encryptSharedPref.getAccountId()

        ApiService.endpoint.logTransaction(sessionId, accountId)
            .enqueue(object : Callback<List<LogTransactionResponse>> {

                override fun onResponse(
                    call: Call<List<LogTransactionResponse>>,
                    response: Response<List<LogTransactionResponse>>
                ) {
                    if (response.isSuccessful) {
                        val listHistory = response.body() ?: emptyList()

                        setDataToAdapter(listHistory.sortedByDescending { it.transactionTimestamp })
                    } else {
                        Toast.makeText(
                            view.context,
                            response.errorBody()?.string(),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

                override fun onFailure(call: Call<List<LogTransactionResponse>>, t: Throwable) {
                    Log.e("onFailure", t.cause.toString())
                }

            })

    }

    private fun setDataToAdapter(transaction: List<LogTransactionResponse>) {
        rvHistoryList.adapter = HistoryViewAdapter(transaction) {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    companion object {
        fun newInstance(): HistoryFragment {
            val fragment = HistoryFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}