package com.example.mockvakotlin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.mockvakotlin.retrofit.ApiService
import com.example.mockvakotlin.sharedpref.Constant
import com.example.mockvakotlin.sharedpref.PrefHelper
import kotlinx.android.synthetic.main.fragment_transfer.*
import model.TransferInquiryRequest
import model.TransferInquiryResponse
import retrofit2.*

class TransferFragment : Fragment() {

    private lateinit var sharedPref: PrefHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_transfer, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        sharedPref = PrefHelper(view.context)

        val sessionId = sharedPref.getSessionId()
        val accountId = sharedPref.getAccountId()

        etAccountDestination.setText("8258200409103259")

        btnTransfer.setOnClickListener {

            val accountDstId = etAccountDestination.text.toString().trim()
            val amount = etAmount.text.toString().trim()

//            val accountDstId = "8258200409103259"
//            val accountDstId = "82582004091"
//            val amount = "1000"

            if (accountDstId.isEmpty()) {
                etAccountDestination.error = "Account destination is required"
                etAccountDestination.requestFocus()
                return@setOnClickListener
            } else if (amount.isEmpty()) {
                etAmount.error = "Amount is required"
                etAmount.requestFocus()
                return@setOnClickListener
            }
            val amountDouble = amount.toDouble()
            val content = TransferInquiryRequest(accountId, accountDstId, amountDouble)
            ApiService.endpoint.transferInquiry(content, sessionId)
                .enqueue(object : Callback<TransferInquiryResponse> {
                    override fun onResponse(
                        call: Call<TransferInquiryResponse>,
                        response: Response<TransferInquiryResponse>
                    ) {
                        if (response.isSuccessful) {
                            context.let { ctx ->
                                val i = Intent(ctx, TransferConfirmation::class.java)
//                            i.flags =
//                                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                i.putExtra(Constant.PARCELIZE_TRF_INQUIRY, response.body())
                                startActivity(i)
                            }

                        } else {
                            Toast.makeText(
                                view.context,
                                response.errorBody()?.string(),
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<TransferInquiryResponse>, t: Throwable) {
                        Log.e("onFailure", t.cause.toString())
                    }
                })

        }
    }

    companion object {
        fun newInstance(): TransferFragment {
            val fragment = TransferFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

}


