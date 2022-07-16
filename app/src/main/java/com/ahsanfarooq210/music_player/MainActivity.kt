package com.ahsanfarooq210.music_player

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import com.ahsanfarooq210.music_player.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity()
{
    private lateinit var binding:ActivityMainBinding
    private lateinit var storagePermission:ActivityResultLauncher<String>

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        requestRuntimePermission()
        binding=ActivityMainBinding.inflate(layoutInflater)
        setTheme(R.style.Theme_Music_player)
        setContentView(binding.root)


        initViews()


    }

    /**
     * this method is to initialize all the views
     * */
    private fun initViews()
    {
        binding.shuffleBtn.setOnClickListener {
            val intent= Intent(this@MainActivity,PlayerActivity::class.java)
            startActivity(intent)
        }
        binding.playlistBtn.setOnClickListener {
            val intent= Intent(this@MainActivity,PlaylistActivity::class.java)
            startActivity(intent)
        }
        binding.favoutireBtn.setOnClickListener {
            val intent= Intent(this@MainActivity,FavouriteActivity::class.java)
            startActivity(intent)
        }
    }

    /**
     * for requesting permisssions
     * */
    private fun requestRuntimePermission()
    {
        if(ActivityCompat.checkSelfPermission(this@MainActivity,android.Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED)
        {
            Toast.makeText(this@MainActivity, "denied", Toast.LENGTH_SHORT).show()
            initiatePermissionContract()
            storagePermission.launch(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
    }

    /**
     * function to initiate the permission contracts
     * */
    private fun initiatePermissionContract()
    {
        storagePermission=registerForActivityResult(ActivityResultContracts.RequestPermission())
        {
            if(it)
            {
                Toast.makeText(this@MainActivity, "Permission Granted", Toast.LENGTH_SHORT).show()
            }
            else
            {
                Toast.makeText(this@MainActivity, "Permission Granted", Toast.LENGTH_SHORT).show()
            }
        }
    }
}