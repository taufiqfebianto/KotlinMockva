package com.example.mockvakotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.mockvakotlin.retrofit.ApiService
import com.example.mockvakotlin.sharedpref.Constant
import com.example.mockvakotlin.sharedpref.EncryptSharedPref
import kotlinx.android.synthetic.main.activity_transfer_confirmation.*
import model.TransferConfirmationRequest
import model.TransferConfirmationResponse
import model.TransferInquiryResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private lateinit var encryptSharedPref: EncryptSharedPref

class TransferConfirmation : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transfer_confirmation)
        val actionbar = supportActionBar
        actionbar!!.title = "Transfer"
        actionbar.setDisplayHomeAsUpEnabled(true)

        encryptSharedPref = EncryptSharedPref(this)
        val sessionId = encryptSharedPref.getSessionId()

        val dataInquiry =
            intent?.getParcelableExtra<TransferInquiryResponse>(Constant.PARCELIZE_TRF_INQUIRY)

        loadData()

        btnConfirm.setOnClickListener {

            val content = TransferConfirmationRequest(
                dataInquiry?.accountSrcId,
                dataInquiry?.accountDstId,
                dataInquiry?.amount,
                dataInquiry?.inquiryId
            )

            ApiService.endpoint.transferConfirmation(content, sessionId)
                .enqueue(object : Callback<TransferConfirmationResponse> {
                    override fun onResponse(
                        call: Call<TransferConfirmationResponse>,
                        response: Response<TransferConfirmationResponse>
                    ) {
                        if (response.isSuccessful) {
                            val i = Intent(applicationContext, TransferReceipt::class.java)
//                            i.flags =
//                                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            i.putExtra(Constant.ACC_SRC_NAME, etAccountSourceName.text.toString())
                            i.putExtra(
                                Constant.ACC_DST_NAME,
                                etAccountDestinationName.text.toString()
                            )
                            i.putExtra(Constant.PARCELIZE_TRF_CONFIRMATION, response.body())
                            startActivity(i)
                        } else {
                            Toast.makeText(
                                applicationContext,
                                response.errorBody()?.string(),
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<TransferConfirmationResponse>, t: Throwable) {
                        Log.e("onFailure", t.cause.toString())
                    }
                })
        }


    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun loadData() {
        val dataItem =
            intent?.getParcelableExtra<TransferInquiryResponse>(Constant.PARCELIZE_TRF_INQUIRY)
        etAccountSource.setText(dataItem?.accountSrcId.toString())
        etAccountSourceName.setText(dataItem?.accountSrcName.toString())
        etAccountDestination.setText(dataItem?.accountDstId.toString())
        etAccountDestinationName.setText(dataItem?.accountDstName.toString())
        etAmount.setText(dataItem?.amount.toString())
    }

}