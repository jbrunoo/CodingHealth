package com.jbrunoo.codinghealth.component

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp

@Composable
fun PurchaseRow(onTicketChanged: (Int) -> Unit, onGenerateLottoNumbersList: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        SelectTicketBox() {
            onTicketChanged(it)
        }
        IconButton(onClick = {
            onGenerateLottoNumbersList()
        }) {
            Icon(imageVector = Icons.Filled.ShoppingCart, contentDescription = null)
        }
    }
}


@Composable
fun SelectTicketBox(onTicketChanged: (Int) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    var ticket by remember { mutableStateOf(1) }

    Box(
        modifier = Modifier
            .width(112.dp)
            .height(40.dp)
            .border(1.dp, Color.LightGray, RectangleShape)
            .clickable { expanded = true },
        contentAlignment = Alignment.Center
    ) {
        Text(text = "${ticket}장")
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            listOf(1, 2, 3, 4, 5).forEach { input ->
                DropdownMenuItem(text = { Text(input.toString()) },
                    onClick = {
                        onTicketChanged(input)
                        ticket = input
                        expanded = false
                    }
                )
            }
        }
    }
}