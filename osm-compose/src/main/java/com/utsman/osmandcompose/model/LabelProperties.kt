package com.utsman.osmandcompose.model


//class LabelProperties for Marker with label
//With default parameters that can be customized
data class LabelProperties(
    val labelColor : Int = android.graphics.Color.BLACK,
    val labelTextSize : Float = 40f,
    val labelAntiAlias : Boolean = true,
    val labelAlign : android.graphics.Paint.Align = android.graphics.Paint.Align.CENTER,
    val labelTextOffset : Float = 30f
)
