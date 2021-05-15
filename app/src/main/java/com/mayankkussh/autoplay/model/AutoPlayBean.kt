package com.mayankkussh.autoplay.model

interface AutoPlayBean {
    val videoUrl: String
    var autoPlayAction: AutoPlayAction?
}

interface AutoPlayAction{
    fun playVideo()
    fun stopVideo()
}