//package com.example.mvsrandomnubersgeneratorapp
//
//import android.content.Context
//import android.os.AsyncTask
//import com.jjoe64.graphview.series.DataPoint
//
//class AsyncShowBarChat : AsyncTask<
//        MutableMap<Int, Int>,
//        Unit,
//        Array<DataPoint?>>() {
//
//    override fun doInBackground(
//        vararg params: MutableMap<Int, Int>?
//    ): Array<DataPoint?> {
//
//        val sortedMap = params[0]!!.toSortedMap()
//        val arrayKeys = sortedMap.keys.toIntArray()
//        val arrayValues = sortedMap.values.toIntArray()
//
//        val dotsArray: Array<DataPoint?> = arrayOfNulls(1024)
//
//        for (j in 0 until 1024) {
//            dotsArray[j] = DataPoint(
//                arrayKeys[j].toDouble(),
//                arrayValues[j].toDouble()
//            )
//        }
//
//
//        return dotsArray
//    }
//
//    override fun onPostExecute(result: Array<DataPoint?>) {
////        MainActivity().setBarChat(result)
//    }
//}