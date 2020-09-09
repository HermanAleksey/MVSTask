package com.example.mvsrandomnubersgeneratorapp.Activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mvsrandomnubersgeneratorapp.AsyncShowBarChat
import com.example.mvsrandomnubersgeneratorapp.DataWorker
import com.example.mvsrandomnubersgeneratorapp.MyRandom
import com.example.mvsrandomnubersgeneratorapp.R
import com.example.mvsrandomnubersgeneratorapp.ViewHolder.MainActivityHolder
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    val TAG = "TAG"
    val CONFIGURE_FIRST_GRAPH:Int = 1
    val CONFIGURE_SECOND_GRAPH:Int  = 2

    lateinit var dotsArray: Array<DataPoint?>
    lateinit var array: Array<Int?>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setTitle("Koshi")

        //Генерация массива Гаусовой числовой последовательности
        array = arrayOfNulls(6553)
        DataWorker().generateRandomNumbersSequence(array, "Koshi", 1000)

        //Мэпа количества каждого значения
        val myMap: MutableMap<Int, Int> = HashMap()
        DataWorker().convertToMap(array, myMap)

        //Создание массива точек
        val sortedMap = myMap.toSortedMap()
        val arrayKeys = sortedMap.keys.toIntArray()
        val arrayValues = sortedMap.values.toIntArray()

        dotsArray = arrayOfNulls(arrayKeys.size)
        DataWorker().fillDataPointsArray(dotsArray, arrayKeys, arrayValues)

        configureGraph(1)
        configureGraph(2)
//        setBarChat(dotsArray,graph)

        //Первое значение второго массива - количество элементов выборки
        //Второе - начальное знаечние для доставания выборки из изначального массива
        button_mainactivity_generate_graph.setOnClickListener {
            AsyncShowBarChat().execute(array)
            val series1 = LineGraphSeries(MainActivityHolder.dotsArray)
            graphview_mainactivity_graph.addSeries(series1)
        }
        button_mainactivity_generate_sub_graph.setOnClickListener {
            AsyncShowBarChat().execute(array)
            val series1 = LineGraphSeries(MainActivityHolder.dotsArray)
            graphview_mainactivity_graph.addSeries(series1)
        }
        graphview_mainactivity_graph.setOnLongClickListener {
            val intent = Intent(this, GraphSettingsActivity::class.java)
            intent.putExtra("graphNumber",CONFIGURE_FIRST_GRAPH)
            startActivityForResult(
                intent, CONFIGURE_FIRST_GRAPH
            )
            return@setOnLongClickListener true
        }
        graphview_mainactivity_subgraph.setOnLongClickListener {
            intent.putExtra("graphNumber",CONFIGURE_SECOND_GRAPH)
            startActivityForResult(
                intent, CONFIGURE_SECOND_GRAPH
            )
            return@setOnLongClickListener true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        MainActivityHolder.array = array
    }

    private fun configureGraph(
        graphNumber: Int = 1
    ) {
        var currentGraphView: GraphView = graphview_mainactivity_subgraph
        when (graphNumber) {
            CONFIGURE_FIRST_GRAPH -> {
                currentGraphView = graphview_mainactivity_graph
            }
            CONFIGURE_SECOND_GRAPH -> {
                currentGraphView = graphview_mainactivity_subgraph
            }
        }
        currentGraphView.viewport.isScrollable = MainActivityHolder.graphSettings[graphNumber-1].scrollable
        currentGraphView.viewport.isScalable = MainActivityHolder.graphSettings[graphNumber-1].zoomable

        currentGraphView.viewport.isXAxisBoundsManual = true
        currentGraphView.viewport.setMinX(MainActivityHolder.graphSettings[graphNumber-1].startX.toDouble())
        currentGraphView.viewport.setMaxX(MainActivityHolder.graphSettings[graphNumber-1].endX.toDouble())

        currentGraphView.viewport.isYAxisBoundsManual = true
        currentGraphView.viewport.setMinY(MainActivityHolder.graphSettings[graphNumber-1].startY.toDouble())
        currentGraphView.viewport.setMaxY(MainActivityHolder.graphSettings[graphNumber-1].endY.toDouble())
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_CANCELED) {
            Toast.makeText(this, "Настройка была отменена", Toast.LENGTH_SHORT).show()
            return
        }
        // редактиует настройки того граффа, каоторый изменяли
        configureGraph(requestCode)
    }
}