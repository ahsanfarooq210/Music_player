package com.ahsanfarooq210.music_player

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ahsanfarooq210.music_player.databinding.ActivityFavouriteBinding

class FavouriteActivity : AppCompatActivity()
{
    private lateinit var binding:ActivityFavouriteBinding
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding=ActivityFavouriteBinding.inflate(layoutInflater)
        setTheme(R.style.coolPink)
        setContentView(binding.root)
    }
}