package com.example.mediaplayerapp

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.service.controls.actions.FloatAction
import android.widget.SeekBar
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    lateinit var floatingPlay: FloatingActionButton
    lateinit var floatingStop: FloatingActionButton
    lateinit var floatingPause: FloatingActionButton

    lateinit var seekBar: SeekBar

    private var currentSong = mutableListOf(R.raw.draw, R.raw.draw_two)

    private var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        floatingStop = findViewById(R.id.floatStop)
        floatingPause = findViewById(R.id.floatPause)
        floatingPlay = findViewById(R.id.floatingPlay)

        seekBar = findViewById(R.id.seekBar)

        controlSong(currentSong[0])     // starting from the first song
    }

    private fun controlSong(songId: Int) {
        floatingPlay.setOnClickListener {

            if (mediaPlayer == null){
                mediaPlayer = MediaPlayer.create(this, songId)
            }
            mediaPlayer?.start()
            initialiseSeekBar()

        }

        floatingPause.setOnClickListener {
            if (mediaPlayer!== null) mediaPlayer?.pause()
        }

        floatingStop.setOnClickListener {
            if(mediaPlayer !== null){
                mediaPlayer?.stop()
                mediaPlayer?.reset()
                mediaPlayer?.release()
                mediaPlayer = null
            }
        }

        seekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if(fromUser) // if from user = true
                    mediaPlayer?.seekTo(progress)  // with this code if you change the seekbar position the play time will be changed
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })
    }

    private fun initialiseSeekBar() {
        seekBar.max = mediaPlayer!!.duration

        val letSeekBarMoveWithMediaPlayer_handler = Handler()
        letSeekBarMoveWithMediaPlayer_handler.postDelayed(object: Runnable{
            override fun run() {
                try {
                    seekBar.progress = mediaPlayer!!.currentPosition
                    letSeekBarMoveWithMediaPlayer_handler.postDelayed(this, 1000)
                }
                catch (e: Exception){
                    seekBar.progress = 0
                }

            }

        }, 0)
    }
}