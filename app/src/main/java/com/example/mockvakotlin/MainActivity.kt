package com.example.mockvakotlin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mockvakotlin.retrofit.ApiService
import com.example.mockvakotlin.sharedpref.EncryptSharedPref
import com.scottyab.rootbeer.RootBeer
import kotlinx.android.synthetic.main.activity_main.*
import model.LoginRequest
import model.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    var rootBeer = RootBeer(this)

    //    lateinit var prefHelper: PrefHelper
    lateinit var encryptSharedPref: EncryptSharedPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        encryptSharedPref = EncryptSharedPref(this)

        if (rootBeer.isRootedWithBusyBoxCheck || rootBeer.isRooted) {
            Toast.makeText(
                applicationContext,
                "DEVICE ROOTED",
                Toast.LENGTH_LONG
            ).show()
            finish()
        } else {
            Toast.makeText(
                applicationContext,
                "DEVICE OK",
                Toast.LENGTH_LONG
            ).show()

        }

        btnlogin.setOnClickListener {

            val username = etUsername.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (username.isEmpty()) {
                etUsername.error = "Username is required"
                etUsername.requestFocus()
                return@setOnClickListener
            } else if (password.isEmpty()) {
                etPassword.error = "Password is required"
                etPassword.requestFocus()
                return@setOnClickListener
            }

            val loginInfo = LoginRequest(username, password)

            ApiService.endpoint.login(loginInfo)
                .enqueue(object : Callback<LoginResponse> {
                    override fun onResponse(
                        call: Call<LoginResponse>,
                        response: Response<LoginResponse>
                    ) {

                        if (response.isSuccessful) {
                            saveSessionLogin(
                                response.body()?.id.toString(),
                                response.body()?.accountId.toString(),
                                true
                            )

                            val i = Intent(applicationContext, HomeActivity::class.java)
                            i.flags =
                                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(i)

                        } else {

                            Toast.makeText(
                                applicationContext,
                                response.errorBody()?.string(),
                                Toast.LENGTH_LONG
                            ).show()

                        }

                    }

                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        Log.e("TAG", "CEK ${t.message.toString()}")
                    }

                })
        }
    }

    override fun onStart() {
        super.onStart()
        Log.i("TAG", "onStart")
    }

    override fun onPause() {
        super.onPause()
        Log.i("TAG", "onPause")
    }

    override fun onResume() {
        super.onResume()
        Log.i("TAG", "onResume")
    }

    override fun onStop() {
        super.onStop()
        Log.i("TAG", "onStop")
    }

    override fun onRestart() {
        super.onRestart()
        Log.i("TAG", "onRestart")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("TAG", "onDestroy")
    }

    private fun saveSessionLogin(id: String, accountId: String, statusLogin: Boolean) {
        encryptSharedPref.setSessionId(id)
        encryptSharedPref.setAccountId(accountId)
    }

}