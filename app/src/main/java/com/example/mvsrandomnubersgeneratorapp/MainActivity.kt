package com.example.mvsrandomnubersgeneratorapp

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.BarGraphSeries
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import java.util.*
import java.util.Map.Entry.comparingByValue
import kotlin.collections.HashMap
import kotlin.math.absoluteValue
import kotlin.math.roundToInt


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        repeat(10){
//            Log.e("TAG", "Number: ${MyRandom().generateRandomGaussianNumber()}" )
            Log.e("TAG", "Number: ${Random().nextGaussian() / 2}")
        }

        val graph = findViewById<GraphView>(R.id.graph)


        //Генерация массива Гаусовой числовой последовательности
        val gaussianArray = arrayOfNulls<Int>(100)
        for (i in gaussianArray.indices){
            gaussianArray[i] =  (Random().nextGaussian().absoluteValue*5).roundToInt()
        }
        //Мэпа количества каждого значения
        val myMap:MutableMap<Int, Int> = HashMap()
        gaussianArray.forEach {
            if (!myMap.containsKey(it)){
                myMap.put(it!!,1)
            } else {
                myMap.put(it!!, myMap[it]!! +1)
            }
        }
        //Создание массива точек
        val dotsArray = arrayOf<DataPoint>()
        myMap.entries.stream().sorted()
        for (i in 0 until myMap.size){
            dotsArray[i] = DataPoint(i.toDouble(), myMap[i]!!.toDouble())
        }

        // Применение массива к гистограмме
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