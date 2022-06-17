package com.ahsanfarooq210.music_player

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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


    }
}