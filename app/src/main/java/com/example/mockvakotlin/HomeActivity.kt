package com.example.mockvakotlin


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationBarView
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    private val onBottomBarSelected = NavigationBarView.OnItemSelectedListener{
        item ->   when (item.itemId) {
        R.id.itHome -> {
            val fragment = HomeFragment.newInstance()
            addFragment(fragment)
            val actionbar = supportActionBar
            actionbar!!.title = "Home"
            return@OnItemSelectedListener true
        }
        R.id.itTransfer -> {
            val fragment = TransferFragment.newInstance()
            addFragment(fragment)
            val actionbar = supportActionBar
            actionbar!!.title = "Transfer"
            return@OnItemSelectedListener true
        }
        R.id.itHistory -> {
            val fragment = HistoryFragment.newInstance()
            addFragment(fragment)
            val actionbar = supportActionBar
            actionbar!!.title = "History"
            return@OnItemSelectedListener true
        }
        R.id.itAccount -> {
            val fragment = AccountFragment.newInstance()
            addFragment(fragment)
            val actionbar = supportActionBar
            actionbar!!.title = "Account"
            return@OnItemSelectedListener true
        }
    }
        false
    }

    private fun addFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.content, fragment, fragment.javaClass.simpleName)
            .commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        navigation.setOnItemSelectedListener(onBottomBarSelected)
        val fragment = HomeFragment.newInstance()
        addFragment(fragment)
        val actionbar = supportActionBar
        actionbar!!.title = "Home"
    }
}