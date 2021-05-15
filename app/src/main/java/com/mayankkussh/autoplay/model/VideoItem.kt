package com.mayankkussh.autoplay.model

data class VideoItem(
    override val videoUrl: String,
) : AutoPlayBean {
    override var autoPlayAction: AutoPlayAction? = null
}