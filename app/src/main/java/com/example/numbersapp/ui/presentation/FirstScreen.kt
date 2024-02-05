package com.example.numbersapp.ui.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.numbersapp.ui.viewmodel.MainViewModel
import dagger.hilt.android.lifecycle.HiltViewModel

@Composable
fun FirstScreen(
    viewModel: MainViewModel = hiltViewModel(),
    navController: NavHostController,
) {
    val state by viewModel.mainState.collectAsState()
    var text by remember { mutableStateOf(TextFieldValue()) }
    val numbers = state.numbersList
    val scrollState = rememberLazyListState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TextField(
            value = text,
            onValueChange = { newValue ->
                if (newValue.text.isEmpty() || newValue.text.toIntOrNull() != null) {
                    text = newValue
                }
            },
            singleLine = true,
            placeholder = {
                Text(text = "Input number")
            },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = { viewModel.getInputNumber(text.text.toInt()) },
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "Get Fact")
            }

            Button(
                onClick = {
                    viewModel.getRandomNumber()
                },
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "Get a Fact about Random Number")
            }
        }

        LazyColumn(
            state = scrollState,
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
        ) {
            items(numbers) { number ->
                NumberRowCard(number.numbers)
            }
        }
    }
}
