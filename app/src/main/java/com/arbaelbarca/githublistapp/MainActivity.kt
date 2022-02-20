package com.arbaelbarca.githublistapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.arbaelbarca.githublistapp.ui.view.users.FragmentHome
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initial()
    }

    private fun initial() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayout, FragmentHome())
            .commit()
    }
}