package com.example.wishlistapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.wishlistapp.data.Wish
import kotlinx.coroutines.launch

@Composable
fun AddEditDetailView(
    id: Long,
    viewModel: WishViewModel,
    navController: NavController
) {
    val snackMessage = remember {
        mutableStateOf("")
    }
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    if(id != 0L) {
        val wish = viewModel.getWishById(id).collectAsState(initial = Wish(0L, "", ""))
        viewModel.wishTitleState = wish.value.title
        viewModel.wishDescriptionState = wish.value.description
    } else {
        viewModel.wishTitleState = ""
        viewModel.wishDescriptionState = ""
    }

    Scaffold(
        topBar = {
            AppBarView(title = if(id != 0L) "Update Wish" else "Add Wish") {
                navController.navigateUp()
            }
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) {
        Column (
            modifier = Modifier
                .padding(it)
                .padding(horizontal = 16.dp)
                .wrapContentSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(10.dp))
            WishTextField(label = "Title", value = viewModel.wishTitleState, onValueChange = { viewModel.onWishTitleChanged(it) })
            Spacer(modifier = Modifier.height(10.dp))
            WishTextField(label = "Description", value = viewModel.wishDescriptionState, onValueChange = { viewModel.onWishDescriptionChanged(it) })
            Spacer(modifier = Modifier.height(10.dp))
            Button(onClick = {
                if(viewModel.wishTitleState.isNotEmpty() && viewModel.wishDescriptionState.isNotEmpty()) {
                    if(id != 0L) {
                        viewModel.updateWish(
                            Wish(
                                id = id,
                                title = viewModel.wishTitleState.trim(),
                                description = viewModel.wishDescriptionState.trim()
                            )
                        )
                        snackMessage.value = "Wish has been updated"
                    } else {
                        viewModel.addWish(
                            Wish(
                                title = viewModel.wishTitleState.trim(),
                                description = viewModel.wishDescriptionState.trim())
                        )
                        snackMessage.value = "Wish has been created"
                    }
                } else {
                    snackMessage.value = "Enter fields to create a wish"
                }
                scope.launch {
                    // snackbarHostState.showSnackbar(snackMessage.value)
                    navController.navigateUp()
                }

            }) {
                Text(text = if(id != 0L) "Update Wish" else "Add Wish", fontSize = 18.sp)
            }
        }
    }
}

@Composable
fun WishTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = {
            Text(text = label, color = Color.Black)
        },
        modifier = Modifier
            .fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        colors = OutlinedTextFieldDefaults.colors(
            cursorColor = Color.Black,
            focusedBorderColor = Color.Black,
            unfocusedBorderColor = Color.Black,
            focusedLabelColor = Color.Black,
            unfocusedLabelColor = Color.Black,
        )
    )
}