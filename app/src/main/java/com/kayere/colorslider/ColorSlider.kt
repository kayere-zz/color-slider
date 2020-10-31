package com.kayere.colorslider

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.widget.SeekBar
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat

class ColorSlider(context: Context, attrs: AttributeSet): androidx.appcompat.widget.AppCompatSeekBar(context, attrs) {
    private val colors = arrayOf(Color.RED, Color.BLACK, Color.YELLOW, Color.BLUE, Color.GRAY, Color.GREEN)
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var listeners = ArrayList<(Int) -> Unit>()

    init {
        progressBackgroundTintList = ContextCompat.getColorStateList(context, android.R.color.transparent)
        progressTintList = ContextCompat.getColorStateList(context, android.R.color.transparent)
        splitTrack = false
        max = 5
        setOnSeekBarChangeListener(object: OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                listeners.forEach {
                    it(colors[progress])
                }
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
                Log.i("Picker", "Tracking started")
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
                Log.i("Picker", "Tracking stopped")
            }

        })
    }

    fun addListener(function: (Int) -> Unit) {
        listeners.add(function)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        drawTickMarks(canvas)
    }

    private fun drawTickMarks(canvas: Canvas?) {
        canvas?.let {
            val w = 24F
            val h = 24F
            val spacing = (width - paddingLeft - paddingRight) / max .toFloat()
            it.translate(paddingLeft.toFloat(), height / 2 .toFloat())
            for (color in colors) {
                paint.style = Paint.Style.FILL_AND_STROKE
                paint.color = color
                it.apply {
                    drawRect(-w, -h, w, h, paint)
                    translate(spacing, 0F)
                }
            }
        }
    }
}