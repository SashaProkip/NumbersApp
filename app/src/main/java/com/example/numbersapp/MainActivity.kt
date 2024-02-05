package com.example.numbersapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.numbersapp.data.database.LocalDataSource
import com.example.numbersapp.data.database.NumbersDao
import com.example.numbersapp.data.network.NumbersApi
import com.example.numbersapp.data.network.RemoteDataSource
import com.example.numbersapp.data.repo.Repository
import com.example.numbersapp.ui.presentation.FirstScreen
import com.example.numbersapp.ui.presentation.SecondScreen
import com.example.numbersapp.ui.theme.NumbersAppTheme
import com.example.numbersapp.ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NumbersAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    FirstScreen()
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NumbersAppTheme {
        Greeting("Android")
    }
}