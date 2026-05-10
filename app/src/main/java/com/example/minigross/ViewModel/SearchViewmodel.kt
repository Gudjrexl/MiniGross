package com.example.minigross.ViewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.minigross.Model.DataClass.ProductDetDatacls
import com.example.minigross.Retrofit.Productretro
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {

    var searchProducts by mutableStateOf<List<ProductDetDatacls>>(
        emptyList()
    )
        private set


    var isSearching by mutableStateOf(false)
        private set


    private var searchJob: Job? = null


    fun searchProduct(
        text: String
    ){

        searchJob?.cancel()

        searchJob = viewModelScope.launch {

            try {

                if(text.isEmpty()){

                    searchProducts = emptyList()

                    return@launch
                }

                isSearching = true

                // SEARCH DELAY
                delay(300)

                searchProducts =

                    Productretro.PrdtApi
                        .searchProducts(
                            text
                        )

            } catch (e: Exception){

                Log.e(
                    "SearchProduct",
                    e.toString()
                )

            } finally {

                isSearching = false
            }
        }
    }
}