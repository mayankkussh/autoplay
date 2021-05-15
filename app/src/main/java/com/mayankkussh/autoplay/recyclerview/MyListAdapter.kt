package com.mayankkussh.autoplay.recyclerview

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.util.MimeTypes
import com.mayankkussh.autoplay.databinding.ItemMyListBinding
import com.mayankkussh.autoplay.model.AutoPlayAction
import com.mayankkussh.autoplay.model.VideoItem


class MyListAdapter : RecyclerView.Adapter<MyListAdapter.MyListViewHolder>() {

    private var displayItems: List<VideoItem> = emptyList()

    override fun onViewAttachedToWindow(holder: MyListViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.attachResources()
    }

    override fun onViewDetachedFromWindow(holder: MyListViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.releaseResources()
    }

    fun setItems(items: List<VideoItem>) {
        displayItems = items;
        notifyDataSetChanged()
    }

    class MyListViewHolder(private val binding: ItemMyListBinding) : RecyclerView.ViewHolder(binding.root), AutoPlayAction {

        private lateinit var videoPlayer: SimpleExoPlayer
        private lateinit var videoItem: VideoItem

        fun bind(videoItem: VideoItem) {
            this.videoItem = videoItem
            this.videoItem.autoPlayAction = this

        }

        override fun playVideo(){
            Log.d("AUTOPLAY", "playVideo $position")
            videoPlayer.playWhenReady = true
        }

        override fun stopVideo(){
            Log.d("AUTOPLAY", "stopVideo $position")
            videoPlayer.stop()
        }

        fun attachResources() {
            Log.d("AUTOPLAY", "attachResources $position")
            videoPlayer = SimpleExoPlayer.Builder(itemView.context).build()
            binding.playerViewExo.player = videoPlayer
            val mediaItem: MediaItem = MediaItem.Builder().setMimeType(MimeTypes.APPLICATION_M3U8).setUri(videoItem.videoUrl)
                .build()
            videoPlayer.setMediaItem(mediaItem)
            videoPlayer.prepare()
        }

        fun releaseResources() {
            Log.d("AUTOPLAY", "releaseResources $position")
            videoPlayer.release()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyListViewHolder {
        return MyListViewHolder(
            ItemMyListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyListViewHolder, position: Int) {
        holder.bind(displayItems[position])
    }

    override fun getItemCount(): Int {
        return displayItems.size
    }
}