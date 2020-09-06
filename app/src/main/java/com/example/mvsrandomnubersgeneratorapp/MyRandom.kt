package com.example.mvsrandomnubersgeneratorapp

import java.util.*

class MyRandom {

    /* Распределение Коши

    * Один из способов быстро получить случайную величину с распределением Коши
    *  — это взять две нормальные случайные величины и поделить друг на друга.
    *
    * Можно ли быстрее? Можно воспользоваться обычным методом инверсии, но он потребует вычисления тангенса. Это еще медленнее.
    * Помните полярный метод Джорджа Марсальи?
    * Он позволял в преобразовании Бокса-Мюллера быстро получать синус или косинус равномерно распределенной случайной величины.
    * Ровно таким же образом можно получить и тангенс. Генерируем две равномерно распределенные случайные
    * координаты x и y на квадрате [-1, 1]x[-1, 1]. Если мы попали в круг с центром в (0, 0) и единичным радиусом — возвращаем x/y,
    *   иначе — генерируем x и y еще раз. Вероятность не попасть в круг, скажем, с раза третьего уже меньше чем 0.01.
    *
    * https://habr.com/ru/post/263993/
    */

    /* Равномерное распределение
    *
    * Равномерное распределение может использоваться при генерации почти что любой случайной величины,
    *  благо имеется очень простой и универсальный метод инверсии (inverse transform sampling):
    *  генерируем случайную величину U, равномерно распределенную от 0 до 1, и возвращаем обратную
    *  функцию распределения (квантиль) с параметром U.
    * */

    fun nextNormal() = (
            (Random().nextDouble()
                    + Random().nextDouble()
                    + Random().nextDouble()
                    + Random().nextDouble()
                    + Random().nextDouble()
                    + Random().nextDouble())
                    / 6)

    fun nextKoshi(max:Int) = ((nextNormal() / nextNormal())*max).toInt()
}