package com.example.wishlistapp

import android.widget.Toast
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissState
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.Icon
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.rememberDismissState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.getColor
import androidx.navigation.NavController
import com.example.wishlistapp.data.Wish

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeView(navController: NavController, viewModel: WishViewModel) {

    val context = LocalContext.current

    Scaffold(
        topBar = {
            AppBarView(title = stringResource(id=R.string.app_name2), onTitleClick = {})
        },
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier
                    .padding(all = 20.dp),
                backgroundColor = colorResource(id = R.color.app_bar),
                contentColor = Color.White,
                onClick = {
                    navController.navigate(Screen.AddScreen.route + "/0L")
                    Toast.makeText(context, "Add detail here", Toast.LENGTH_SHORT).show()
                }
            ) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = null, tint = Color.White)
            }
        }

    ) {
        //Display data
        val wishlist = viewModel.getAllWishes.collectAsState(initial = listOf())
        //Not preload all wishes
        LazyColumn(modifier = Modifier.fillMaxSize().padding(it)) {
            items(wishlist.value, key = { wish -> wish.id }) {
                wish ->

                    //Swipe to delete feature
                    val dismissState= rememberDismissState(
                        confirmStateChange = { if(it==DismissValue.DismissedToEnd || it==DismissValue.DismissedToStart){ viewModel.deleteWish(wish) } ; true }
                    )

                    SwipeToDismiss(state = dismissState,
                        background = {
                            val color by animateColorAsState(
                                if(dismissState.dismissDirection==DismissDirection.EndToStart) colorResource(id=R.color.app_bar) else Color.Transparent,
                                label = ""
                            )
                            val alignment= Alignment.CenterEnd
                            Box(Modifier.fillMaxSize().background(color).padding(horizontal = 20.dp), contentAlignment = alignment) { Icon(Icons.Default.Delete, contentDescription = "Delete Item", tint = Color.White) }

                        },
                        directions = setOf(DismissDirection.EndToStart),
                        dismissThresholds = {FractionalThreshold(0.5f)},
                        dismissContent = {

                            //Display list
                            WishItem(wish) {
                                val id=wish.id
                                navController.navigate(Screen.AddScreen.route + "/$id")  // Navigate with the correct wish id
                            }

                        }

                    )
            }
        }
    }
}


@Composable
fun WishItem(wish: Wish, onclick: ()-> Unit){
    Card(modifier = Modifier.fillMaxWidth()
        .padding(top=8.dp,start=8.dp,end=8.dp)
        .clickable { onclick()},
        elevation = 12.dp, backgroundColor = Color.White )
        {
            Column(modifier = Modifier.padding(16.dp))
            {
                Text(text = wish.title, fontWeight = FontWeight.ExtraBold)
                Text(text = wish.description)
            }
        }
}
