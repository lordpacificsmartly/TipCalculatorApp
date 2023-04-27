package com.lordpacific.tipcalculatorapp

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Money
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.roundToInt

@Composable
fun HomeScreen() {

    var inputAmount by remember {
        mutableStateOf("")
    }
    var tipPercent by remember {
        mutableStateOf("")
    }

    var persons by remember {
        mutableStateOf(1)
    }

    var amountPerPerson = 0
    var tipAmount: Int
    var totalAmount = 0

    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        Text(text = "Tip Calculator", fontSize = 30.sp, color = Color.White)
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Amount", fontSize = 24.sp, color = Color.White)
        Spacer(modifier = Modifier.height(8.dp))
        TextField(value = inputAmount, onValueChange = { newInput ->
            inputAmount = newInput
        },
            modifier = Modifier
                .clip(CircleShape)
                .border(2.dp, Color.Yellow, CircleShape),
            colors = TextFieldDefaults.textFieldColors(textColor = Color.White),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            maxLines = 1,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Money,
                    contentDescription = null,
                    tint = Color.Green
                )
            }
        )

        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Tip %", fontSize = 24.sp, color = Color.White)
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = tipPercent, onValueChange = { newInput ->
                tipPercent = newInput
            },
            modifier = Modifier
                .clip(CircleShape)
                .border(2.dp, Color.Yellow, CircleShape),
            colors = TextFieldDefaults.textFieldColors(textColor = Color.White),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            maxLines = 1
        )

        Spacer(modifier = Modifier.height(8.dp))
        if (inputAmount > 0.toString() && tipPercent > 0.toString()) {
            tipAmount = calculateTipAmount(inputAmount, tipPercent)!!
            totalAmount = inputAmount.toInt() + tipAmount

            Text(
                text = "Total Amount: $totalAmount",
                fontSize = 24.sp,
                color = Color.White,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }
        Spacer(modifier = Modifier.height(15.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {

            Button(onClick = { persons++ }) {
                Text(text = "Add", fontSize = 24.sp, color = Color.White)
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = { persons-- }) {
                Text(text = "Remove", fontSize = 24.sp, color = Color.White)
            }
            Spacer(modifier = Modifier.width(10.dp))

            Text(
                text = "$persons",
                fontSize = 24.sp,
                color = Color.White,
                modifier = Modifier.padding(8.dp)
            )
        }

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {

            Text(
                text = "Amount per person",
                fontSize = 24.sp,
                color = Color.White,
                modifier = Modifier.padding(8.dp)
            )
            Spacer(modifier = Modifier.width(20.dp))
            try {
                amountPerPerson = totalAmount / persons
            } catch (e: Exception) {
                inputAmount = "0"
                tipPercent = "0"
                Log.d(TAG, "amount per person $e")
            }
            Text(
                text = "$amountPerPerson",
                fontSize = 24.sp,
                color = Color.White,
                modifier = Modifier.padding(8.dp)
            )
        }

        Spacer(modifier = Modifier.height(100.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.Center
        ) {
            Button(onClick = {
                if (inputAmount.isNotEmpty() ) inputAmount = "0"
                if (tipPercent.isNotEmpty()) tipPercent = "0"
            }, modifier = Modifier.clip(CircleShape)) {
                Text(text = "Clear All", fontSize = 28.sp, color = Color.White)
            }
        }
    }
}

fun calculateTipAmount(inputAmount: String, tipPercent: String): Int? {

    val tipAmount: Int

    if (inputAmount.isNotEmpty() && tipPercent.isNotEmpty()) {
        tipAmount = ((inputAmount.toFloat() / 100) * (tipPercent.toFloat())).roundToInt()
    } else {
        return null
    }
    return tipAmount
}
