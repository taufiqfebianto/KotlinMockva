package com.example.mockvakotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mockvakotlin.sharedpref.Constant
import kotlinx.android.synthetic.main.activity_transfer_receipt.*
import model.TransferConfirmationResponse

class TransferReceipt : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transfer_receipt)
        val actionbar = supportActionBar
        actionbar!!.title = "Transfer"

        loadData()

        btnClose.setOnClickListener {
            val i = Intent(applicationContext, HomeActivity::class.java)
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(i)
        }

    }

    private fun loadData() {
        val dataConfirmation =
            intent?.getParcelableExtra<TransferConfirmationResponse>(Constant.PARCELIZE_TRF_CONFIRMATION)
        tvAccSource.setText(dataConfirmation?.accountSrcId)
        tvAccSourceName.setText(intent.getStringExtra(Constant.ACC_SRC_NAME))
        tvAccDestination.setText(dataConfirmation?.accountDstId)
        tvAccDestinationName.setText(intent.getStringExtra(Constant.ACC_DST_NAME))
        tvAmount.setText(dataConfirmation?.amount.toString())
        tvRefNumber.setText(dataConfirmation?.clientRef)
        tvTransactionTime.setText(dataConfirmation?.transactionTimestamp)
        tvStatus.setText("SUCCESS")
    }
}