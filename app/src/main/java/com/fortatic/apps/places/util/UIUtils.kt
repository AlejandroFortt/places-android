package com.fortatic.apps.places.util

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.fortatic.apps.places.R
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory

/**
 * This ensure that code is only applied when object exist. Maps produce headaches
 */
fun <T> T?.applyIfNotNull(block: T.() -> Unit) {
    this?.block()
}

@SuppressLint("InflateParams")
fun Context.generateMarker(title: String): BitmapDescriptor {
    val markerView = (getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(
        R.layout.marker_layout,
        null
    )
    val text = markerView.findViewById<TextView>(R.id.tv_location)
    val layout = markerView.findViewById<ConstraintLayout>(R.id.container_mark)

    text.text = title

    val bitmap = Bitmap.createScaledBitmap(viewToBitmap(layout), layout.width, layout.height, false)
    return BitmapDescriptorFactory.fromBitmap(bitmap)
}

fun viewToBitmap(view: View): Bitmap {
    view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
    val bitmap =
        Bitmap.createBitmap(view.measuredWidth, view.measuredHeight, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    view.layout(0, 0, view.measuredWidth, view.measuredHeight)
    view.draw(canvas)
    return bitmap
}