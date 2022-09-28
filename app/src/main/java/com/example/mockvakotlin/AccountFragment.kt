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
import com.example.mockvakotlin.sharedpref.PrefHelper
import kotlinx.android.synthetic.main.fragment_account.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AccountFragment : Fragment() {

    private lateinit var sharedPref: PrefHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPref = PrefHelper(view.context)
        val sessionId = sharedPref.getSessionId()

        btnLogout.setOnClickListener {
//
            ApiService.endpoint.logout(sessionId)
                .enqueue(object : Callback<Unit> {
                    override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                        if (response.isSuccessful) {
                            sharedPref.clear()
                            context.let { ctx ->
                                val i = Intent(ctx, MainActivity::class.java)
                                i.flags =
                                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
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

                    override fun onFailure(call: Call<Unit>, t: Throwable) {
                        Log.e("onFailure", t.cause.toString())
                    }
                })
//                .enqueue(object : Call <Unit>{
//
//                })

        }
    }

    companion object {
        fun newInstance(): AccountFragment {
            val fragment = AccountFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}