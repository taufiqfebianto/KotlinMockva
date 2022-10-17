package com.example.mockvakotlin

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.mockvakotlin.retrofit.ApiService
import com.example.mockvakotlin.sharedpref.EncryptSharedPref
import kotlinx.android.synthetic.main.fragment_home.*
import model.GetAccountDetailResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    private lateinit var encryptSharedPref: EncryptSharedPref

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        encryptSharedPref = EncryptSharedPref(view.context)
        val sessionId = encryptSharedPref.getSessionId()
        val accountId = encryptSharedPref.getAccountId()

        ApiService.endpoint.getAccountDetail(sessionId, accountId)
            .enqueue(object : Callback<GetAccountDetailResponse> {
                override fun onResponse(
                    call: Call<GetAccountDetailResponse>,
                    response: Response<GetAccountDetailResponse>
                ) {
                    if (response.isSuccessful) {
                        etAccountNumber.setText(response.body()?.id.toString())
                        etName.setText(response.body()?.username.toString())
                        etBalance.setText(response.body()?.balance.toString())
                    } else {
                        Toast.makeText(
                            view.context,
                            response.errorBody()?.string(),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

                override fun onFailure(call: Call<GetAccountDetailResponse>, t: Throwable) {
                    Log.e("onFailure", t.cause.toString())
                }

            })
    }


    companion object {

        fun newInstance(): HomeFragment {
            val fragment = HomeFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

}