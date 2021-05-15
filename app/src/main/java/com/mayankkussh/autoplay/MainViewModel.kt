package com.mayankkussh.autoplay

import android.os.CountDownTimer
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mayankkussh.autoplay.model.VideoItem

class MainViewModel: ViewModel() {

    private val videosUrl = mutableListOf<VideoItem>().apply {
        add(VideoItem("https://www.rmp-streaming.com/media/big-buck-bunny-360p.mp4"))
        add(VideoItem("https://www.rmp-streaming.com/media/big-buck-bunny-360p.mp4"))
        add(VideoItem("https://www.rmp-streaming.com/media/big-buck-bunny-360p.mp4"))
        add(VideoItem("https://www.rmp-streaming.com/media/big-buck-bunny-360p.mp4"))
        add(VideoItem("https://www.rmp-streaming.com/media/big-buck-bunny-360p.mp4"))
        add(VideoItem("https://www.rmp-streaming.com/media/big-buck-bunny-360p.mp4"))
        add(VideoItem("https://www.rmp-streaming.com/media/big-buck-bunny-360p.mp4"))
        add(VideoItem("https://www.rmp-streaming.com/media/big-buck-bunny-360p.mp4"))
        add(VideoItem("https://www.rmp-streaming.com/media/big-buck-bunny-360p.mp4"))
        add(VideoItem("https://www.rmp-streaming.com/media/big-buck-bunny-360p.mp4"))
        add(VideoItem("https://www.rmp-streaming.com/media/big-buck-bunny-360p.mp4"))
        add(VideoItem("https://www.rmp-streaming.com/media/big-buck-bunny-360p.mp4"))
    }

    private val queue = ArrayDeque<Int>()
    private var currentPlayingIndex : Int? = null
    private lateinit var autoPlayTimer: CountDownTimer

    val listLiveData : MutableLiveData<List<VideoItem>> by lazy {
        MutableLiveData<List<VideoItem>>()
    }

    fun onViewCreated(){
        listLiveData.postValue(videosUrl)
        autoPlayTimer = object : CountDownTimer(1000000, 8000) {
            override fun onFinish() {
                // Do nothing
            }

            override fun onTick(millisUntilFinished: Long) {
                onAutoPlayTimerTick()
            }
        }.start()
    }

    private fun onAutoPlayTimerTick() {
        currentPlayingIndex?.run {
            videosUrl.getOrNull(this)?.autoPlayAction?.stopVideo()
        }
        val position = queue.removeFirstOrNull()
        currentPlayingIndex = position
        position?.run {
            currentPlayingIndex = this
            videosUrl.getOrNull(this)?.autoPlayAction?.playVideo()
        }
    }


    fun setVisiblePositionsInRecyclerView(visiblePositions: List<Int>) {
        queue.clear()
        queue.addAll(visiblePositions)
    }

    fun onRecyclerViewStartScrolling(){
        currentPlayingIndex?.run {
            videosUrl.getOrNull(this)?.autoPlayAction?.stopVideo()
        }
    }

    override fun onCleared() {
        super.onCleared()
        autoPlayTimer.cancel()
    }
}