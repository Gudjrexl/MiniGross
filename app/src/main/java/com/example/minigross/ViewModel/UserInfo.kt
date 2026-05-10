package com.example.minigross.ViewModel

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.minigross.Model.DataStoreManager
import kotlinx.coroutines.launch

class UserInfoVm: ViewModel() {

    fun sendOtp(phone: String, Onres: (String) -> Unit) {
        viewModelScope.launch {
            try {
                Log.d(TAG, "Sending OTP to: $phone")
                val res = true
                if (res) {
                    Log.d(TAG, "OTP send success")
                    Onres("success")
                } else {
                    val errorMsg = "unable to send otp"
                    Log.e(TAG, "Failed to send OTP: $errorMsg")
                    Onres("Failed to send OTP: $errorMsg")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Exception while sending OTP", e)
                Onres("Failed to send OTP: ${e.message}")
            }
        }
    }


    fun verifyOtp(context: Context, phone: String, otp: String, Onres: (String) -> Unit) {
        viewModelScope.launch {
            try {
                Log.d(TAG, "Verifying OTP for: $phone | code: $otp")

                var res = false
                DataStoreManager.saveUser(context, phone)

                if (otp == "1234"){
                    res = true
                }

                if (res) {
                    Log.d(TAG, "OTP verification success")
                    Onres("success")
                } else {
                    val errorMsg = "Unknown error"
                    Log.e(TAG, "Failed to verify OTP: $errorMsg")
                    Onres("Failed to verify OTP: $errorMsg")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Exception while verifying OTP", e)
                Onres("Failed to verify OTP: ${e.message}")
            }
        }
    }


}