package com.example.mvsrandomnubersgeneratorapp

import java.io.Serializable

data class GraphSettings(
    var startX: Int = 0,
    var endX: Int = 2560,
    var startY: Int = 0,
    var endY: Int = 1024,
    var scrollable: Boolean = true,
    var zoomable: Boolean = false
) : Serializable
