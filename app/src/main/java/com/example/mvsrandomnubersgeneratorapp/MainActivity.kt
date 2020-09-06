package com.example.mvsrandomnubersgeneratorapp

import android.annotation.SuppressLint
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.BarGraphSeries
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    val TAG = "TAG"
    lateinit var dotsArray: Array<DataPoint?>
//    val graph: GraphView = findViewById(R.id.graph)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Генерация массива Гаусовой числовой последовательности
        val array: Array<Int?> = arrayOfNulls(6553)
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

        barChatVisualSettings()
//        setBarChat(dotsArray,graph)

        //Первое значение второго массива - количество элементов выборки
        //Второе - начальное знаечние для доставания выборки из изначального массива
        for (i in 0..3000 step 1000) {
            AsyncShowBarChat().execute(array, Array(2) { 1024; i})
            Thread.sleep(1000)
        }
    }

    @SuppressLint("StaticFieldLeak")
    inner class AsyncShowBarChat : AsyncTask<
            Array<Int?>,
            Unit,
            Array<DataPoint?>>() {

        override fun doInBackground(
            vararg params: Array<Int?>?
        ): Array<DataPoint?>? {

            //получаем массив изначально сгенерированный и из него извлекаем в зависимости от
            //длинны шага и начального элемента - подмассив
            val sizeOfViborki = params[1]!![0]!!
            val startedElement = params[1]!![1]!!
            val arrayToWorkWith = params[0]!!
                .copyOfRange(startedElement, startedElement+sizeOfViborki)
//                .slice(startedElement until startedElement+sizeOfViborki)

            //преобразуем подмассив в мэпу
            val myMap: MutableMap<Int, Int> = HashMap()
            DataWorker().convertToMap(arrayToWorkWith, myMap)

            //разбиваем на состовляющее для перевода в массив точек
            val sortedMap = myMap.toSortedMap()
            val arrayKeys = sortedMap.keys.toIntArray()
            val arrayValues = sortedMap.values.toIntArray()

            //заполняем массив точек
            val dotsArray: Array<DataPoint?> = arrayOfNulls(sortedMap.size)
            for (j in dotsArray.indices) {
                dotsArray[j] = DataPoint(
                    arrayKeys[j].toDouble(),
                    arrayValues[j].toDouble()
                )
            }

            return dotsArray
        }

        override fun onPostExecute(result: Array<DataPoint?>) {
            //Установить гистограммы
            val series1 = LineGraphSeries(dotsArray)
            val series2 = BarGraphSeries(dotsArray)

            graph.addSeries(series2)
            graph.addSeries(series1)
        }
    }

    fun barChatVisualSettings() {
        graph.viewport.isScrollable = true

        graph.viewport.isXAxisBoundsManual = true
        graph.viewport.setMinX(0.0)
        graph.viewport.setMaxX(2560.0)

        graph.viewport.isYAxisBoundsManual = true
        graph.viewport.setMinY(0.0)
        graph.viewport.setMaxY(1024.0)
    }
}