package com.github.derleymad.fitnesstracker

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class MainItem(
    val id: Int,
    val link: String,
    @StringRes val textStringId: Int,
    val color: Int
)
