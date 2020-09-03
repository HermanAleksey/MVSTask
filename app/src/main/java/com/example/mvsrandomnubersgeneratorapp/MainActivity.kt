package com.example.mvsrandomnubersgeneratorapp

import android.graphics.Color
import android.graphics.ColorSpace
import android.graphics.DashPathEffect
import android.graphics.Paint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.BarGraphSeries
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import kotlin.random.Random


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val graph = findViewById<GraphView>(R.id.graph)
        /*val dotsArray = arrayOf(
            DataPoint(0.0, 1.0),
            DataPoint(1.0, 5.0),
            DataPoint(2.0, 3.0),
            DataPoint(3.0, 2.0),
            DataPoint(4.0, 6.0)
        )*/

        val dotsArray = arrayOfNulls<DataPoint>(100)
        for (i in dotsArray.indices){
            dotsArray[i] = DataPoint(i.toDouble(), Random.nextDouble(10.0))
        }
        val series1 = LineGraphSeries(dotsArray)
        val series2 = BarGraphSeries(dotsArray)
        series1.color = Color.BLACK
        series2.color = Color.GRAY

        graph.viewport.isScrollable = true
        graph.addSeries(series2)
        graph.addSeries(series1)

        series1.setDrawDataPoints(true);
        series1.setDataPointsRadius(10.0F);
        series1.setThickness(8);

        //size of View Port
        graph.viewport.isXAxisBoundsManual = true
        graph.viewport.setMinX(0.0)
        graph.viewport.setMaxX(10.0)

        graph.viewport.isYAxisBoundsManual = true
        graph.viewport.setMinY(0.0)
        graph.viewport.setMaxY(15.0)


        // custom paint to make a dotted line
//        val paint = Paint()
//        paint.setStyle(Paint.)
//        series1.setCustomPaint(paint)

//        series.isDrawValuesOnTop = true
//        series.valuesOnTopColor = Color.RED


        /*
        appendData

        This methods adds a single data set to the current data. There's also a flag "scrollToEnd",
        that will scroll the GraphView automatically to the last X value.
         */

    }
}