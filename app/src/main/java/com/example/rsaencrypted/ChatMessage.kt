package com.example.rsaencrypted

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import java.time.LocalTime
import java.time.format.DateTimeFormatter

data class ChatMessage(
    val id: String = System.currentTimeMillis().toString(),
    val text: String,
    val timestamp: Long = System.currentTimeMillis()// миллисекунды
)

@Preview (showBackground = true)
@Composable
fun MyMessageItem(message: ChatMessage = ChatMessage(id = "", text = ""), modifier: Modifier = Modifier) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.End // всегда справа
    ) {
        ConstraintLayout(
            modifier = modifier
                .background(
                    color = Color(0xFFECFAFD),
                    shape = RoundedCornerShape(
                        topStart = 64.dp,
                        topEnd = 12.dp,
                        bottomStart = 64.dp,
                        bottomEnd = 4.dp
                    )
                )
                .padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            val (messageStr, time) = createRefs()

            Text(
                text = message.text,
                color = Color.Black,
                fontSize = 16.sp,
                modifier = Modifier.constrainAs(messageStr) {
                    start.linkTo(parent.start)
                    bottom.linkTo(parent.bottom)
                    top.linkTo(parent.top)
                }
            )

            Text(
                text = getCurrentTimeFormatted(),
                color = Color(0xFF9CAFB6),
                fontSize = 12.sp,
                modifier = Modifier.constrainAs(time) {
                    start.linkTo(messageStr.end, margin = 8.dp)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                },
            )
        }
    }
}

fun getCurrentTimeFormatted(): String {
    val now = LocalTime.now()
    return now.format(DateTimeFormatter.ofPattern("HH:mm"))
}