package com.example.filenamecoder

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.filenamecoder.chat.ChatScreen
import com.example.filenamecoder.chat.ChatViewModel
import com.example.filenamecoder.ui.CenterAlignedTopAppBarExample
import com.example.filenamecoder.ui.theme.FileNameCoderTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FileNameCoderTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MaterialTheme {
                        Column {
                            Box(
                                modifier = Modifier
                                    .padding(top = 6.dp)
                                    .height(70.dp)
                                    .shadow(5.dp)
                            ) {
                                CenterAlignedTopAppBarExample()
                            }
                            val chatViewModel = viewModel<ChatViewModel>()
                            ChatScreen(chatViewModel)
                        }
                    }
                }
            }
        }
    }
}
