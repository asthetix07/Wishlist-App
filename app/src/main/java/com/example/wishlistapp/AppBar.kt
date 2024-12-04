package com.example.wishlistapp

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AppBarView(
    title: String,
    onTitleClick: () -> Unit,
    onBackNavClicked: () -> Unit = {}
) {
    // Define the back navigation icon only when the title doesn't contain "Notes"
    val navigationIcon: (@Composable () -> Unit)? =
        if (!title.contains("NOTES")) {
            {
                IconButton(onClick = { onBackNavClicked() }, modifier = Modifier.padding(start = 4.dp, top = 20.dp)) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        tint = Color.White,
                        contentDescription = null,
                    )
                }
            }
        } else { null }

    // Get the context for Toast
    val context = LocalContext.current

    // Properly align the "NOTES" title and set the color to white
    TopAppBar(
        title = {
            Text(
                text = title,
                color = colorResource(id = R.color.white),
                style = TextStyle(fontStyle = FontStyle.Italic, fontWeight = FontWeight.Bold, fontSize = 20.sp),
                modifier = Modifier
                    .padding(start = 4.dp, top = 20.dp)
                    .heightIn(max = 24.dp)
                    .clickable {
                        if (title == "NOTES") {
                            // Show toast when "NOTES" is clicked
                            Toast.makeText(context, "Notes app", Toast.LENGTH_SHORT).show()
                            onTitleClick()
                        }
                    }
            )
        },
        elevation = 3.dp,
        backgroundColor = colorResource(id = R.color.app_bar),
        navigationIcon = navigationIcon,
        modifier = Modifier.height(80.dp)
    )
}

