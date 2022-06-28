package com.bitgest.kwanza.UI.adapter

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment

fun Fragment.initToolbar (toobar: Toolbar) {
    (activity as AppCompatActivity).setSupportActionBar(toobar)
    (activity as AppCompatActivity).title = ""
    (activity as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(true)
    toobar.setNavigationOnClickListener { activity?.onBackPressed() }
    toobar.setTitleTextColor(Color.parseColor("#2F0736"))
}