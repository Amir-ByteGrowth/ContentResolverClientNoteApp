package com.example.contentresolverclientnoteapp

import android.content.ContentResolver
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.contentresolverclientnoteapp.presentation.screens.MainScreen
import com.example.contentresolverclientnoteapp.presentation.screens.MainViewModel
import com.example.contentresolverclientnoteapp.ui.theme.ContentResolverClientNoteAppTheme
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModels<MainViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            ContentResolverClientNoteAppTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    MainScreen(viewModel = mainViewModel)
                }
            }
        }
        lifecycleScope.launch {
            mainViewModel.notes.update { getAllNotes(context = this@MainActivity) }
        }

        lifecycleScope.launch {
            this@MainActivity.contentResolver.observe(this@MainActivity, CONTENT_URI)
                .collectLatest {notes->
                    println("Update is calling")
                    mainViewModel.notes.update { notes }
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
    ContentResolverClientNoteAppTheme {
        Greeting("Android")
    }
}