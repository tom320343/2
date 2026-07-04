package com.example.myresponsive.activity.ui.reflow

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ReflowViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Reflow Fragment"
    }
    val text: LiveData<String> = _text
}