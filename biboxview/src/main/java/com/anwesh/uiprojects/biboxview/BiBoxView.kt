package com.anwesh.uiprojects.biboxview

/**
 * Created by anweshmishra on 10/04/19.
 */

import android.view.View
import android.view.MotionEvent
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Canvas
import android.graphics.Color
import android.content.Context
import android.app.Activity

val nodes : Int = 5
val boxes : Int = 2
val scGap : Float = 0.05f
val scDiv : Double = 0.51
val strokeFactor : Int = 90
val sizeFactor : Float = 2.9f
val foreColor : Int = Color.parseColor("#673AB7")
val backColor : Int = Color.parseColor("#BDBDBD")

fun Int.inverse() : Float = 1f / this
fun Float.maxScale(i : Int, n : Int) : Float = Math.max(0f, this - i * n.inverse())
fun Float.divideScale(i : Int, n : Int) : Float = Math.min(n.inverse(), maxScale(i, n)) * n
fun Float.scaleFactor() : Float = Math.floor(this / scDiv).toFloat()
fun Float.mirrorValue(a : Int, b : Int) : Float {
    val k : Float = scaleFactor()
    return (1 - k) * a.inverse() + k * b.inverse()
}
fun Int.sf() : Float = 1f - 2 * this
fun Int.rem2() : Int = this % 2
fun Float.scf(i : Int) : Float = (1f - this) * (i.rem2()) + this * (1f - i.rem2())
fun Float.updateValue(dir : Float, a : Int, b : Int)  : Float = mirrorValue(a, b) * dir * scGap

fun Canvas.drawBiBox(i : Int, gap : Float, size : Float, sc : Float, paint : Paint) {
    val sc1 : Float = sc.divideScale(i, boxes)
    save()
    translate(gap * i.sf() * sc1, 0f)
    drawRect(RectF(-size, -size, size, size), paint)
    restore()
}

fun Canvas.drawBBNode(i : Int, scale : Float, paint : Paint) {
    val w : Float = width.toFloat()
    val h : Float = height.toFloat()
    val gap : Float = h / (nodes + 1)
    val size : Float = gap / sizeFactor
    paint.color = foreColor
    paint.strokeWidth = Math.min(w, h) / strokeFactor
    paint.strokeCap = Paint.Cap.ROUND
    val sc1 : Float = scale.divideScale(0, 2)
    val sc2 : Float = scale.divideScale(1, 2)
    save()
    translate(w / 2, h - gap * (i) + size + gap * sc1)
    for (j in 0..(boxes - 1)) {
        drawBiBox(j, gap, size, sc2.scf(i), paint)
    }
    restore()
}

class BiBoxView(ctx : Context) : View(ctx) {

    private val paint : Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    override fun onDraw(canvas : Canvas) {

    }

    override fun onTouchEvent(event : MotionEvent) : Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {

            }
        }
        return true
    }
}