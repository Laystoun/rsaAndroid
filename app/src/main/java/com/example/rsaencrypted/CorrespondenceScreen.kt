package com.example.rsaencrypted

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties
import androidx.navigation.NavController
import com.example.rsaencrypted.encrypted.EncryptedLogsViewModel
import com.example.rsaencrypted.encrypted.RSAObject
import com.example.rsaencrypted.encrypted.decryptMessage
import com.example.rsaencrypted.encrypted.encryptedMessage

@Composable
fun Correspondence(
    navController: NavController,
    viewModel: EncryptedLogsViewModel
) {
    val textFieldValue = remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.InitializationKeys()
    }

    val rsa = viewModel.rsa

    Scaffold(
        topBar = {
            TopBar(navController)
        },
        bottomBar = {
            BottomBar(
                textFieldValue,
                rsa = viewModel.rsa,
                viewModel = viewModel
            )
        },
        contentWindowInsets = WindowInsets(0, 0, 0, 0)
    ) { innerPadding ->
        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFFE0F2FE), // сверху
                        Color(0xFFB3D9FF)  // снизу
                    )
                )
            ),
            reverseLayout = true
        ) {
            items(viewModel.messages.size) { index ->
                val msg = viewModel.messages[viewModel.messages.size - 1 - index]

                AnimatedVisibility(
                    visible = true,
                    enter = androidx.compose.animation.fadeIn() + androidx.compose.animation.expandVertically()
                ) {
                    MyMessageItem(msg)
                }
            }
        }
    }
}

@Composable
fun TopBar(navController: NavController) {

    val expanded = remember { mutableStateOf(false) }

    Row(modifier = Modifier
        .fillMaxWidth()
        .height(80.dp)
        .background(Color(0xFF517B94))
        .windowInsetsPadding(WindowInsets.statusBars),
        verticalAlignment = Alignment.Bottom
    ) {
        IconButton(
            onClick = {},
            modifier = Modifier.padding(start = 10.dp, end = 10.dp, bottom = 5.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.arrow_backward),
                contentDescription = "",
                tint = Color.White
            )
        }

        Image(
            painter = painterResource(R.drawable.img_10983445_624a99e258c99_1),
            contentDescription = "",
            modifier = Modifier
                .padding(start = 5.dp, bottom = 5.dp)
                .size(50.dp) // Установите одинаковую ширину и высоту
                .clip(CircleShape) // Обрезать по кругу
                .background(Color.LightGray)
        )

        Column(
            modifier = Modifier
                .padding(start = 12.dp, bottom = 8.dp)
                .weight(1f)
        ) {
            Text(
                text = "Pavel Durov",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 2.dp)
            )
            Text(
                text = "был(а) в 00:24",
                color = Color.White.copy(alpha = 0.8f),
                fontSize = 15.sp
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 5.dp)
        ) {
            IconButton(
                onClick = {  },
                modifier = Modifier
                    .size(40.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.phone_calling_rounded_svgrepo_com),
                    contentDescription = "Позвонить",
                    tint = Color.White,
                    modifier = Modifier
                        .size(28.dp)
                )
            }

            Box() {
                IconButton(
                    onClick = { expanded.value = true },
                    modifier = Modifier
                        .size(40.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.three_dots_vertical),
                        contentDescription = "Меню",
                        tint = Color.White,
                        modifier = Modifier
                            .size(24.dp)
                    )
                }

                DropdownMenu(
                    expanded = expanded.value,
                    onDismissRequest = { expanded.value = false },
                    modifier = Modifier.background(Color.White)
                ) {
                    DropdownMenuItem(
                        text = {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .weight(0.3f)
                                ) {
                                    Icon(
                                        painter = painterResource(R.drawable.logs),
                                        contentDescription = "",
                                        tint = Color(0xFF919191),
                                        modifier = Modifier.size(32.dp)
                                    )
                                }

                                Box(
                                    modifier = Modifier
                                        .weight(0.7f)
                                ) {
                                    Text(
                                        text = "Логи шифрования",
                                        textAlign = TextAlign.Center,
                                        fontSize = 18.sp,
                                        modifier = Modifier.align(Alignment.Center)
                                    )
                                }

                            }
                        },
                        modifier = Modifier
                            .height(40.dp)
                            .wrapContentWidth(),
                        onClick = {
                            expanded.value = false
                            navController.navigate(ScreensInfo.logScreen)
                        }

                    )
                }
            }
        }
    }
}

@Composable
fun BottomBar(
    textValue: MutableState<String> = mutableStateOf(""),
    rsa: RSAObject? = RSAObject(),
    viewModel: EncryptedLogsViewModel
) {
    val isViewGallery = textValue.value.isEmpty()
    val imePadding = WindowInsets.ime.asPaddingValues().calculateBottomPadding()

    Row(
        modifier = Modifier.padding(bottom = imePadding)
    ) {
        Row(
            modifier = Modifier
                .weight(0.6f)
                .background(Color.White)
                .windowInsetsPadding(WindowInsets.navigationBars),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = textValue.value,
                onValueChange = { newString -> textValue.value = newString },
                modifier = Modifier
                    .weight(1f),
                placeholder = { Text("Сообщение", fontSize = 20.sp, color = Color(0xFFA2A6A7)) },
                leadingIcon = {
                    IconButton(
                        onClick = {},
                        modifier = Modifier
                            .size(48.dp)
                            .padding(start = 5.dp, bottom = 5.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.sticker),
                            contentDescription = "",
                            tint = Color(0xFF919191),
                            modifier = Modifier.size(32.dp)
                        )
                    }
                },
                textStyle = TextStyle(
                    fontSize = 20.sp,
                    color = Color.Black
                ),
                singleLine = true,
                maxLines = 1,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    cursorColor = Color(0xFF517B94)
                )
            )
        }

        AnimatedVisibility(
            isViewGallery
        ) {
            Row(
                modifier = Modifier
                    .weight(0.4f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                IconButton(
                    onClick = {},
                    modifier = Modifier
                        .size(48.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.gift),
                        contentDescription = "",
                        modifier = Modifier.size(34.dp),
                        tint = Color(0xFF919191)
                    )
                }

                IconButton(
                    onClick = {},
                    modifier = Modifier
                        .size(48.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.skrepka),
                        contentDescription = "",
                        modifier = Modifier.size(28.dp),
                        tint = Color(0xFF919191)
                    )
                }
            }
        }

        Row() {
            AnimatedVisibility(isViewGallery) {
                IconButton(
                    onClick = {},
                    modifier = Modifier.size(48.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.voice),
                        contentDescription = "",
                        modifier = Modifier.size(42.dp),
                        tint = Color(0xFF919191)
                    )
                }
            }

            AnimatedVisibility(!isViewGallery) {
                IconButton(
                    onClick = {
                        val thisText = textValue.value
                        textValue.value = ""

                        viewModel.addMessage(thisText)

                        val encryptArray = encryptedMessage(
                            thisText,
                            rsa!!.e,
                            rsa.n,
                            viewModel = viewModel
                        )

                        val decryptArray = decryptMessage(
                            encryptArray,
                            rsa.d,
                            rsa.n,
                            viewModel
                        )
                    },
                    modifier = Modifier.size(64.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.send_message),
                        contentDescription = "",
                        modifier = Modifier.size(64.dp),
                        tint = Color(0xFF62ABD4)
                    )
                }
            }
        }
    }
}
