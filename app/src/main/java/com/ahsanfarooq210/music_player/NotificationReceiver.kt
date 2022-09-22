package com.ahsanfarooq210.music_player

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import kotlin.system.exitProcess

class NotificationReceiver : BroadcastReceiver()
{
    override fun onReceive(context: Context?, intent: Intent?)
    {
        when(intent?.action)
        {
            ApplicationClass.previous->{
                Toast.makeText(context, "previous clicked", Toast.LENGTH_SHORT).show()
            }

            ApplicationClass.NEXT->{
                Toast.makeText(context, "next clicked", Toast.LENGTH_SHORT).show()
            }

            ApplicationClass.PLAY->{
                Toast.makeText(context, "play clicked", Toast.LENGTH_SHORT).show()
            }
            ApplicationClass.EXIT->{
                exitProcess(1)
            }
        }

    }
}