package com.example.mvsrandomnubersgeneratorapp.ViewHolder

import com.example.mvsrandomnubersgeneratorapp.GraphSettings
import com.jjoe64.graphview.series.DataPoint

object MainActivityHolder {

    var array: Array<Int?> = arrayOfNulls(0)
    var dotsArray: Array<DataPoint?> = arrayOfNulls(0)
    var graphSettings: Array<GraphSettings> = arrayOf(GraphSettings(), GraphSettings())

}