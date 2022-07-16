package com.ahsanfarooq210.music_player

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.app.ActivityCompat
import com.ahsanfarooq210.music_player.databinding.ActivityMainBinding
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity()
{
    private lateinit var binding:ActivityMainBinding
    private lateinit var storagePermission:ActivityResultLauncher<String>
    private lateinit var toggle:ActionBarDrawerToggle

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
        //for nav drawer
        toggle= ActionBarDrawerToggle(this@MainActivity,binding.root,R.string.open,R.string.close)
        binding.root.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

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
        binding.navView.setNavigationItemSelectedListener {
            when(it.itemId)
            {
                R.id.navFeedback-> Toast.makeText(this@MainActivity, "Feedback", Toast.LENGTH_SHORT).show()
                R.id.navSettings-> Toast.makeText(this@MainActivity, "Settings", Toast.LENGTH_SHORT).show()
                R.id.navAbout-> Toast.makeText(this@MainActivity, "About", Toast.LENGTH_SHORT).show()
                R.id.navExit-> exitProcess(1)
            }
            true
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean
    {
        if(toggle.onOptionsItemSelected(item))
        {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}