package com.example.wishlistapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.wishlistapp.data.Wish
import kotlinx.coroutines.launch

@Composable
fun AddEditDetailView(id: Long, viewModel: WishViewModel, navController: NavController) {
    val snackMessage = remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()

    // Update state values directly
    // appropriate for side effects when dealing with state changes
        if (id != 0L) {

            // Collect the wish data based on the ID
            val wish = viewModel.getAWishById(id).collectAsState(initial = Wish(0L, "", ""))

            viewModel.wishTitleState = wish.value.title
            viewModel.wishDescriptionState = wish.value.description
        } else {
            viewModel.wishTitleState = ""
            viewModel.wishDescriptionState = ""
        }



    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            AppBarView(
                title = if (id != 0L) stringResource(id = R.string.update_wish)
                else stringResource(id = R.string.add_wish),
                onTitleClick = {},
            ) { navController.navigateUp() }
        },
    ) {
        Column(
            modifier = Modifier.padding(it).wrapContentSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(10.dp))

            // Title text field
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, start = 8.dp, end = 8.dp)) {

                WishTextField(
                    label = "Title",
                    value = viewModel.wishTitleState,
                    onValueChanged = { viewModel.onWishTitleChanged(it) }
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Description text field
                WishTextField(
                    label = "Description",
                    value = viewModel.wishDescriptionState,
                    onValueChanged = { viewModel.onWishDescriptionChanged(it) }
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Button to update or add wish
            Button(
                onClick = {
                    if (viewModel.wishTitleState.isNotEmpty() && viewModel.wishDescriptionState.isNotEmpty()) {
                        if (id != 0L) {
                            // Update existing wish
                            viewModel.updateWish(
                                Wish(id = id, title = viewModel.wishTitleState.trim(), description = viewModel.wishDescriptionState.trim())
                            )
                            snackMessage.value = "Wish updated"
                        } else {
                            // Add new wish
                            viewModel.addWish(
                                Wish(title = viewModel.wishTitleState.trim(), description = viewModel.wishDescriptionState.trim())
                            )
                            snackMessage.value = "Wish added"
                        }
                    } else {
                        snackMessage.value = "Enter the required fields"
                    }

                    scope.launch {
                        //scaffoldState.snackbarHostState.showSnackbar(snackMessage.value)
                        navController.navigateUp()
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = colorResource(id = R.color.app_bar),
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = if (id != 0L) stringResource(id = R.string.update_wish) else stringResource(id = R.string.add_wish),
                )
            }
        }
    }
}


@Composable
fun WishTextField(label: String, value: String, onValueChanged: (String) -> Unit) {

    OutlinedTextField(
        label = { Text(text = label, color = Color.Black) },
        value = value,
        onValueChange = onValueChanged,  // Corrected parameter name
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = Color.Black,
            focusedBorderColor = Color.Black,
            unfocusedBorderColor = Color.Black,
            cursorColor = Color.Black,
            focusedLabelColor = Color.Black,
            unfocusedLabelColor = Color.Black )
    )
}

@Preview(showBackground = true)
@Composable
fun WishTextFieldPreview() {
    WishTextField(label = "Title", value = "Sample Title", onValueChanged = {})
}