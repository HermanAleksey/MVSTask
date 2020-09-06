package com.example.mvsrandomnubersgeneratorapp

import com.jjoe64.graphview.series.DataPoint

class DataWorker {


    //Генерирует последовательность псевдо-случайных чисел
    fun generateRandomNumbersSequence(
        array: Array<Int?>,
        distribution: String = "Koshi", randomModification: Int
    ) {
        when (distribution) {
            "Koshi" -> {
                for (i in array.indices) {
                    array[i] = ((MyRandom().nextKoshi(randomModification)))
                }
            }
        }

    }

    //преобразование массива всех случайных чисел в мэпу, в которой соответственно
    //ключ - случайное значение, а значение - колическо повторов ключа в выборке
    fun convertToMap(array: Array<Int?>, myMap: MutableMap<Int, Int>) {
        array.forEach {
            if (!myMap.containsKey(it)) {
                myMap.put(it!!, 1)
            } else {
                myMap.put(it!!, myMap[it]!! + 1)
            }
        }
    }

    //На основании массивов ключей и
    fun fillDataPointsArray(
        dotsArray: Array<DataPoint?>,
        arrayKeys: IntArray,
        arrayValues: IntArray
    ) {
        for (i in arrayKeys.indices) {
            dotsArray[i] = DataPoint(arrayKeys[i].toDouble(), arrayValues[i].toDouble())
        }
    }

}