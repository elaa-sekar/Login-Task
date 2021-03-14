package com.technotackle.test.ui.home

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    //data
    var userName = ObservableField<String>()
    var profileCompletedPercentage = ObservableField<Int>()

    fun updateUserName(userName: String) {
        this.userName.set(userName)
    }

    fun updateProfileCompletedPercentage(percentage: Int) {
        profileCompletedPercentage.set(percentage)
    }

}