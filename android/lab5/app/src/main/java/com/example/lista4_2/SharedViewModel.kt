package com.example.lista4_2

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    val imageResId: MutableLiveData<Int> = MutableLiveData()
}
