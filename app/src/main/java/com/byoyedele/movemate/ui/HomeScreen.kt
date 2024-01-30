package com.byoyedele.movemate.ui

import CustomTopAppBar
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Divider
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.byoyedele.movemate.R
import com.byoyedele.movemate.data.VehicleDetails
import com.byoyedele.movemate.data.listOfVehicles
import com.byoyedele.movemate.ui.theme.DimOrange
import com.byoyedele.movemate.ui.theme.DirtyWhite
import com.byoyedele.movemate.ui.theme.Green
import com.byoyedele.movemate.ui.theme.Orange
import com.byoyedele.movemate.ui.theme.SemiBoldTitleText
import com.byoyedele.movemate.ui.utils.AppBarAnimatedVisibility
import com.byoyedele.movemate.ui.utils.ContentAnimatedVisibility
import com.byoyedele.movemate.viewmodels.HomeViewModel

@Composable
fun HomeScreen(viewModel: HomeViewModel, navController: NavHostController) {
    var isContentVisible by remember { mutableStateOf(false) }
    var isAppBarVisible by remember { mutableStateOf(false) }
    fun loadContent() {
        isContentVisible = !isContentVisible
    }

    fun loadAppBar() {
        isAppBarVisible = !isAppBarVisible
    }

    Scaffold(
        topBar = {
            AppBarAnimatedVisibility(visibility = isAppBarVisible) {
                CustomTopAppBar(
                    viewModel,
                    navController
                )
            }
        },
    ) { paddingValues ->
        ContentAnimatedVisibility(visibility = isContentVisible) {
            Content(paddingValues)
        }
        LaunchedEffect("") {
            loadAppBar()
            loadContent()
        }
    }
}


@Composable
private fun Content(paddingValues: PaddingValues) {
    var isListVisible by remember { mutableStateOf(false) }
    Surface(
        modifier = Modifier
            .padding(
                top = paddingValues.calculateTopPadding(),
                bottom = paddingValues.calculateBottomPadding()
            )
            .background(color = DirtyWhite)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = stringResource(id = R.string.tracking),
                style = SemiBoldTitleText
            )
            Spacer(modifier = Modifier.size(24.dp))

            // Card
            ItemCard()
            Spacer(modifier = Modifier.size(24.dp))
            Text(
                text = stringResource(id = R.string.available_vehicles),
                style = SemiBoldTitleText.copy(fontSize = 18.sp)
            )
            Spacer(modifier = Modifier.size(12.dp))
            AnimatedVisibility(
                visible = isListVisible,
                enter = slideInHorizontally(
                    initialOffsetX = { fullWidth -> fullWidth },
                    animationSpec = tween(durationMillis = 1000, easing = LinearOutSlowInEasing)
                ) + fadeIn(
                    initialAlpha = 0f,
                    animationSpec = tween(durationMillis = 1000, easing = LinearOutSlowInEasing)
                ),
                exit = slideOutHorizontally(
                    targetOffsetX = { fullWidth -> fullWidth },
                    animationSpec = tween(durationMillis = 1000, easing = LinearOutSlowInEasing)
                ) + fadeOut(
                    targetAlpha = 0f,
                    animationSpec = tween(durationMillis = 1000, easing = LinearOutSlowInEasing)
                ),
            ) {
                VehiclesList()
            }
            LaunchedEffect("") {
                isListVisible = true
            }
        }
    }
}

@Composable
fun VehiclesList() {
    LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        items(listOfVehicles) { vehicle ->
            VehicleItem(vehicle)
        }
    }
}


@Composable
fun VehicleItem(vehicleDetails: VehicleDetails) {
    Column(
        modifier = Modifier
            .shadow(
                elevation = 1.dp,
                spotColor = Color.Gray,
                ambientColor = Color.Gray,
            )
            .size(width = 150.dp, height = 250.dp)
            .clip(RoundedCornerShape(24.dp))

            .padding(16.dp)
    ) {
        Text(text = vehicleDetails.name, fontSize = 18.sp)
        Text(text = vehicleDetails.type, fontSize = 12.sp, color= Color.Gray)
        Image(
            painter = painterResource(id = vehicleDetails.image),
            alignment = Alignment.BottomEnd,
            contentDescription = ""
        )

    }
}

@Composable
fun ItemCard() {
    Surface(
        modifier = Modifier
            .shadow(
                elevation = 10.dp,
                spotColor = Color.Gray,
                ambientColor = Color.Gray,
                shape = RoundedCornerShape(12.dp)
            )
            .background(color = Color.White)
            .clip(RoundedCornerShape(24.dp))
            .fillMaxWidth()
    ) {
        Column {
            Column(modifier = Modifier.padding(16.dp)) {
                Row {
                    Column(
                        Modifier.weight(1f)
                    ) {
                        Text(
                            text = stringResource(id = R.string.shipment_number),
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                        Spacer(modifier = Modifier.size(6.dp))
                        Text(
                            text = "NEJ20089934122231",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Image(
                        painter = painterResource(id = R.drawable.stacked_box),
                        contentDescription = "",
                        modifier = Modifier.size(50.dp)
                    )
                }
                Spacer(modifier = Modifier.size(12.dp))
                Divider(color = DividerDefaults.color.copy(alpha = 0.3f))
                Spacer(modifier = Modifier.size(16.dp))
                Row {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(color = DimOrange.copy(alpha = 0.3f)),
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.box),
                            modifier = Modifier
                                .size(24.dp)
                                .align(Alignment.Center),
                            contentDescription = null
                        )
                    }
                    Spacer(modifier = Modifier.size(12.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = stringResource(id = R.string.sender),
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                        Text(text = "Atlanta, 5243")
                    }

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = stringResource(id = R.string.time),
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .clip(shape = CircleShape)
                                    .background(color = Color(0xFF4aca2d)),
                            )
                            Spacer(modifier = Modifier.size(4.dp))
                            Text(text = "2 day -3 days")
                        }
                    }
                }
                Spacer(modifier = Modifier.size(40.dp))

                Row {
                    // Should actually be an icon with circular background. Fix up
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(color = Green.copy(alpha = 0.3f)),
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.box),
                            modifier = Modifier
                                .size(24.dp)
                                .align(Alignment.Center),
                            contentDescription = null
                        )
                    }
                    Spacer(modifier = Modifier.size(12.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = stringResource(id = R.string.receiver),
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                        Text(text = "Chicago, 6342")
                    }

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = stringResource(id = R.string.status),
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                        Text(text = "Waiting to collect")
                    }
                }
            }
            Divider(color = DividerDefaults.color.copy(alpha = 0.3f))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp), horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    Icons.Rounded.Add,
                    contentDescription = stringResource(id = R.string.add_stop),
                    tint = Orange
                )
                Text(
                    text = stringResource(id = R.string.add_stop),
                    color = Orange,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}
