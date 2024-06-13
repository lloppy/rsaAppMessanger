package com.example.filenamecoder.chat

import android.app.Activity
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.filenamecoder.R

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ChatScreen(viewModel: ChatViewModel) {
    val context = LocalContext.current
    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.data?.let { uri ->
                    handleFileSelection(context, uri)

                    val fileName = getFileName(context, uri)
                    viewModel.sendMessage(fileName, true, context)
                }
            }
        }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 12.dp)
    ) {

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(8.dp),
            reverseLayout = true
        ) {
            items(viewModel.messages.reversed()) { message ->
                if (message.isUser) {
                    MessageBubble(
                        message.content,
                        Alignment.End,
                        Color.Transparent,
                        Color.Black,
                        Modifier.padding(start = 46.dp)
                    )
                } else {
                    MessageBubble(
                        message.content,
                        Alignment.Start,
                        Color.LightGray,
                        Color.White,
                        Modifier.padding(end = 46.dp)
                    )
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            var inputText by remember { mutableStateOf("") }
            IconButton(
                onClick = {
                    launcher.launch(createChooseFileIntent())
                    if (inputText.isNotEmpty()) {
                        viewModel.sendMessage(inputText, true, context)
                        inputText = ""
                    }
                },
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.attach),
                    contentDescription = "Attach File"
                )
            }
            TextField(
                value = inputText,
                onValueChange = { inputText = it },
                modifier = Modifier.weight(1f),
                label = { Text(text = "Cообщение", color = Color.Gray) },
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Send),
                keyboardActions = KeyboardActions(onSend = {
                    if (inputText.isNotEmpty()) {
                        viewModel.sendMessage(inputText, true, context)
                        inputText = ""
                    }
                })
            )
            IconButton(
                onClick = {
                    if (inputText.isNotEmpty()) {
                        viewModel.sendMessage(inputText, true, context)
                        inputText = ""
                    }
                },
                modifier = Modifier.padding(
                    start = 5.dp,
                    top = 6.dp
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Send,
                    contentDescription = "Отправить сообщение",
                    modifier = Modifier
                        .size(30.dp),
                    tint = Color.Gray
                )
            }
        }
    }
}

@Composable
fun MessageBubble(
    text: String,
    alignment: Alignment.Horizontal,
    color: Color,
    textColor: Color,
    modifier: Modifier
) {
    Surface(
        shape = MaterialTheme.shapes.medium,
        tonalElevation = 4.dp,
        modifier = modifier
            .padding(vertical = 4.dp)
            .fillMaxWidth()
            .wrapContentWidth(alignment),
    ) {
        Text(
            text = text,
            modifier = Modifier
                .background(color)
                .padding(16.dp),
            fontSize = 16.sp,
            color = textColor
        )
    }
}
