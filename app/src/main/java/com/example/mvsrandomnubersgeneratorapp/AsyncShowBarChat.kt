package com.example.mvsrandomnubersgeneratorapp

import android.annotation.SuppressLint
import android.os.AsyncTask
import com.example.mvsrandomnubersgeneratorapp.Activity.MainActivity
import com.example.mvsrandomnubersgeneratorapp.ViewHolder.MainActivityHolder
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import kotlinx.android.synthetic.main.activity_main.*

@SuppressLint("StaticFieldLeak")
class AsyncShowBarChat : AsyncTask<
        Array<Int?>,
        Unit,
        Array<DataPoint?>>() {

    override fun doInBackground(
        vararg params: Array<Int?>?
    ): Array<DataPoint?>? {
        //получаем массив изначально сгенерированный и из него извлекаем в зависимости от
        //длинны шага и начального элемента - подмассив
        val arrayToWorkWith = params[0]!!

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

        Thread.sleep(1000)
        return dotsArray
    }

    override fun onPostExecute(result: Array<DataPoint?>) {
        //Установить гистограммы
        MainActivityHolder.dotsArray = result
    }
}