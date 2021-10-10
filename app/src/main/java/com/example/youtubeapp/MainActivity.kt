package com.example.youtubeapp

import android.content.Context
import android.content.res.Configuration
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val videos: Array<Array<String>> = arrayOf(
        arrayOf("Numbers Game", "CiFyTc1SwPw"),
        arrayOf("Calculator", "ZbZFMDk69IA"),
        arrayOf("Guess the Phrase", "DU1qMhyKv8g"),
        arrayOf("Username and Password", "G_XYXuC8b9M"),
        arrayOf("GUI Username and Password", "sqJWyPhZkDw"),
        arrayOf("Country Capitals", "yBkRLhoVTmc"),
        arrayOf("Database Module", "E-Kb6FgMbVw"))

    lateinit var youTubePlayerView: YouTubePlayerView
    lateinit var player: YouTubePlayer
    var currentVideo=0
    var timeStamp=0F

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkInternet()

        youTubePlayerView=findViewById(R.id.youtube_player)
        youTubePlayerView.addYouTubePlayerListener( object :AbstractYouTubePlayerListener(){
            override fun onReady(youTubePlayer: YouTubePlayer) {
                super.onReady(youTubePlayer)
                player=youTubePlayer
                player.loadVideo(videos[currentVideo][1],timeStamp)
                initializeRV()
            }
        }
        )//end listener parameter
    }//end onCreate()

    //checkInternet code part

    //step 1: create connectedToInternet() function
    //this function return true if connected and false otherwise
    fun connectedToInternet():Boolean{
        /*2: create Connectivity Manager object
        we need to use Connectivity Manager
        which is a part of our Android system.*/
        val connectivityManager= this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        /* 3: use activeNetworkInfo (Connectivity Manager methode) to get info about connection
         use features of Connectivity Manager
         as one of them return info about a connection
         */
        val activeNetwork:NetworkInfo?= connectivityManager.activeNetworkInfo
        /* 4: check the connection by using isConnectedOrConnecting
        as it returns True if it is connected or connecting and false otherwise.
         */
        return activeNetwork?.isConnectedOrConnecting===true
    }

    //step 5: create checkInternet() function
    //this function decide what to do if internet connected or not
    fun checkInternet(){
        if (!connectedToInternet()){
            AlertDialog.Builder(this)
                .setTitle("Internet Connection Not Found!")
                .setPositiveButton("RETRY"){
                        _, _ -> checkInternet()
                }
                .show()
        }
    }
    //end checkInternet code part

    fun initializeRV(){
        rv_main.adapter=videoAdapter(videos,player)
        rv_main.layoutManager=LinearLayoutManager(this)
        rv_main.setHasFixedSize(true)
    }

    //Override onConfigurationChanged to track device rotation
    //Set video to full screen when in landscape mode, exit full screen in portrait mode
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (newConfig.orientation === Configuration.ORIENTATION_LANDSCAPE) {
            youTubePlayerView.enterFullScreen()
        } else if (newConfig.orientation === Configuration.ORIENTATION_PORTRAIT) {
            youTubePlayerView.exitFullScreen()
        }
    }

    //Save video id and time stamp to allow continuous play after device rotation
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putInt("currentVideo", currentVideo)
        outState.putFloat("timeStamp", timeStamp)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        currentVideo = savedInstanceState.getInt("currentVideo", 0)
        timeStamp = savedInstanceState.getFloat("timeStamp", 0f)
    }

}