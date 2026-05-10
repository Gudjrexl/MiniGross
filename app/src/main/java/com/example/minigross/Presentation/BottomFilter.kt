package com.example.minigross.Presentation


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.minigross.ViewModel.Producrviewmodel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun FilterBottomSheetWithSections(onDismiss: () -> Unit, vm: Producrviewmodel = viewModel(), onApply: () -> Unit ) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val selectedCategories = remember { mutableStateListOf<String>() }
    val selectedCategories2 = remember { mutableStateListOf<Int>() }
    val priceOptions = listOf(500,1000, 3000, 5000, 8000,12000,1500,20000)
    val vmp: Producrviewmodel = viewModel()

    LaunchedEffect(Unit) {
        vmp.categoryviewmodel()
    }

    val categoryOptions = vmp.category

    ModalBottomSheet(onDismiss, sheetState = sheetState, dragHandle = { BottomSheetDefaults.DragHandle()}) {
        Box(modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(16.dp)
        ) {
            Column {
                Row (modifier = Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.Center){
                    Text(text = "Filter", style = MaterialTheme.typography.titleLarge )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = "Category",style= MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(5.dp))
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(9.dp),
                    verticalArrangement = Arrangement.spacedBy(-4.dp)
                ) {
                    categoryOptions.forEach { option ->
                        val categoryName = option.name
                        val selected = selectedCategories.contains(categoryName)

                        FilterChip(
                            selected = selected,
                            onClick = {
                                if (selected) {
                                    selectedCategories.remove(categoryName)
                                } else {
                                    selectedCategories.add(categoryName)
                                }
                            },
                            label = { Text(categoryName) }
                        )
                    }
                }


                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                ) {
                    Button(
                        onClick = {
                            selectedCategories.clear()
                            selectedCategories2.clear()
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent,
                            contentColor = Color.Black
                        ),
                        border = BorderStroke(1.dp, Color.Black),
                        shape = RoundedCornerShape(15.dp),
                        modifier = Modifier.height(40.dp).width(150.dp)
                    ) {
                        Text(text = "Clean")
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    Button(
                        onClick = {
                            val lastPrice = selectedCategories2.lastOrNull()
                            vm.applyfilter(
                                selectedCategories,
                                if (lastPrice != null) listOf(lastPrice) else emptyList()
                            )
                            onApply()
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Black,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(15.dp),
                        modifier = Modifier.height(40.dp).width(150.dp)
                    ) {
                        Text(text = "Apply Changes")
                    }
                }
            }
        }

    }

}

@Composable
@Preview(showBackground = true)
fun FilterBottomSheetPreview() {
    FilterBottomSheetWithSections(onDismiss = {}, onApply = {})
}