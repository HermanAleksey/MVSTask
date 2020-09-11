package com.example.mvsrandomnubersgeneratorapp.Activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mvsrandomnubersgeneratorapp.AsyncShowBarChat
import com.example.mvsrandomnubersgeneratorapp.DataWorker
import com.example.mvsrandomnubersgeneratorapp.GraphSettings
import com.example.mvsrandomnubersgeneratorapp.R
import com.example.mvsrandomnubersgeneratorapp.ViewHolder.MainActivityHolder
import com.example.mvsrandomnubersgeneratorapp.ViewHolder.MainActivityHolder.array
import com.example.mvsrandomnubersgeneratorapp.ViewHolder.MainActivityHolder.subArray
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.LineGraphSeries
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    val TAG = "TAG"
    val CONFIGURE_FIRST_GRAPH:Int = 1
    val CONFIGURE_SECOND_GRAPH:Int  = 2


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setTitle("Koshi")

        //Генерация массива Гаусовой числовой последовательности
        DataWorker().generateRandomNumbersSequence(array, "Koshi", 100)

        configureGraph(1)
        configureGraph(2)


        button_mainactivity_generate_graph.setOnClickListener {
            val numberOfElements = edit_text_main_num_of_elements.text.toString().toInt()
            array = arrayOfNulls(numberOfElements)
            DataWorker().generateRandomNumbersSequence(array, "Koshi", 100)

            AsyncShowBarChat().execute(array)
            graphview_mainactivity_graph.removeAllSeries()
            val series1 = LineGraphSeries(MainActivityHolder.dotsArray)
            graphview_mainactivity_graph.addSeries(series1)
        }
        button_mainactivity_generate_sub_graph.setOnClickListener {
            val firstElement = edit_text_main_start_sub_array.text.toString().toInt()
            val lastElement = edit_text_main_end_sub_array.text.toString().toInt()
            subArray = array.copyOfRange(firstElement,lastElement)

            AsyncShowBarChat().execute(subArray)
            graphview_mainactivity_subgraph.removeAllSeries()
            val series1 = LineGraphSeries(MainActivityHolder.dotsArray)
            graphview_mainactivity_subgraph.addSeries(series1)

        }
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.menu_item_main_graph_settings -> {
                val intent = Intent(this, GraphSettingsActivity::class.java)
                intent.putExtra("graphNumber",CONFIGURE_FIRST_GRAPH)
                startActivityForResult(
                    intent, CONFIGURE_FIRST_GRAPH
                )
            }
            R.id.menu_item_main_subgraph_settings -> {
                val intent = Intent(this, GraphSettingsActivity::class.java)
                intent.putExtra("graphNumber",CONFIGURE_SECOND_GRAPH)
                startActivityForResult(
                    intent, CONFIGURE_SECOND_GRAPH
                )
            }

        }
        return super.onOptionsItemSelected(item)
    }
}