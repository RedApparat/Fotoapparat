package io.fotoapparat.sample

import android.view.View
import android.widget.CompoundButton
import android.widget.SeekBar
import androidx.appcompat.widget.SwitchCompat

internal infix fun SwitchCompat.onCheckedChanged(function: (CompoundButton, Boolean) -> Unit) {
    setOnCheckedChangeListener(function)
}

internal infix fun View.onClick(function: () -> Unit) {
    setOnClickListener { function() }
}

internal infix fun SeekBar.onProgressChanged(zoomUpdated: () -> Unit) {
    setOnSeekBarChangeListener(object : OnProgressChanged() {
        override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
            zoomUpdated()
        }
    })
}
