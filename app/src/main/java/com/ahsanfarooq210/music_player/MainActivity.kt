package com.ahsanfarooq210.music_player

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.ahsanfarooq210.music_player.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity()
{
    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setTheme(R.style.Theme_Music_player)
        setContentView(binding.root)

        initViews()

    }

    private fun initViews()
    {
        binding.shuffleBtn.setOnClickListener {
            Toast.makeText(this@MainActivity,"button clicked ",Toast.LENGTH_SHORT).show()
        }
    }
}