package com.example.moodflow.uicontent

import android.graphics.Color
import android.os.Parcelable
import androidx.annotation.ColorInt
import kotlinx.parcelize.Parcelize

@Parcelize
enum class GradientColor(
    @ColorInt val startColor: Int,
    @ColorInt val endColor: Int
) : Parcelable {
    GREEN(
        startColor = Color.parseColor("#00FF55"),
        endColor = Color.parseColor("#33FFBB")
    ),
    YELLOW(
        startColor = Color.parseColor("#FFAA00"),
        endColor = Color.parseColor("#FFFF33")
    ),
    BLUE(
        startColor = Color.parseColor("#00AAFF"),
        endColor = Color.parseColor("#33DDFF")
    ),
    RED(
        startColor = Color.parseColor("#FF0000"),
        endColor = Color.parseColor("#FF5533")
    );
}