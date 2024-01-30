package com.byoyedele.movemate.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import com.byoyedele.movemate.data.listOfShipmentStatus
import com.byoyedele.movemate.model.ShipmentStatus
import com.byoyedele.movemate.model.ShipmentStatusLabel
import com.byoyedele.movemate.ui.theme.DarkBlue
import com.byoyedele.movemate.ui.theme.DimOrange
import com.byoyedele.movemate.ui.theme.LightGrey
import com.byoyedele.movemate.ui.theme.NavyBlue
import com.byoyedele.movemate.ui.theme.Purple
import com.byoyedele.movemate.ui.utils.ContentAnimatedVisibility
import com.byoyedele.movemate.ui.utils.TitledAppBar
import com.byoyedele.movemate.ui.utils.bounceClick
import com.byoyedele.movemate.viewmodels.ShipmentViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ShipmentScreen(viewModel: ShipmentViewModel = viewModel(), navController: NavHostController) {
    Scaffold(
        topBar = {
            TitledAppBar(
                title = stringResource(id = R.string.shipment_history),
                onBackClick = { navController.popBackStack() })
        }
    ) { paddingValues ->
        ShipmentList(paddingValues, viewModel)
    }
}

@Composable
fun CustomTabRow(selectedId: MutableIntState) {
    ScrollableTabRow(
        selectedTabIndex = selectedId.intValue,
        containerColor = Purple,
        contentColor = Color.White,
        edgePadding = 12.dp,
        indicator = { tabPositions ->
            if (selectedId.intValue < tabPositions.size) {
                TabRowDefaults.Indicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[selectedId.intValue]),
                    color = DimOrange
                )
            }
        }
    ) {
        ShipmentStatusLabel.entries.forEach { status ->
            val isSelected = selectedId.intValue == status.id
            val count =
                if (status == ShipmentStatusLabel.ALL) listOfShipmentStatus.size else listOfShipmentStatus.count { it.status == status }
            Tab(
                selected = isSelected,
                onClick = { selectedId.intValue = status.id },
                modifier = Modifier.bounceClick()
            ) {
                Row(modifier = Modifier.padding(8.dp)) {
                    Text(
                        text = status.title,
                        color = if (isSelected) Color.White else Color.White.copy(0.6f),
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(
                                color = if (isSelected) DimOrange else Color.White.copy(
                                    0.3f
                                )
                            )
                    ) {
                        Text(
                            text = "$count",
                            modifier = Modifier.padding(horizontal = 10.dp),
                            color = if (isSelected) Color.White else Color.White.copy(
                                0.4f
                            )
                        )
                    }

                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ShipmentList(
    paddingValues: PaddingValues,
    viewModel: ShipmentViewModel,
) {

    var isContentVisible by remember { mutableStateOf(false) }
    var isCustomRowVisible by remember { mutableStateOf(false) }


    LaunchedEffect("") {
        isContentVisible = true
        isCustomRowVisible = true
    }


    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f,
        pageCount = { ShipmentStatusLabel.entries.size }
    )

    val selectedId = remember {
        mutableIntStateOf(0)
    }

    Column(
        Modifier.padding(
            bottom = paddingValues.calculateBottomPadding(),
            top = paddingValues.calculateTopPadding()
        )
    ) {
        AnimatedVisibility(
            visible = isCustomRowVisible,
            enter = slideInHorizontally(
                initialOffsetX = { w -> w },
                animationSpec = tween(durationMillis = 2000)) + fadeIn(
                tween(2000),
            ),
            exit = slideOutHorizontally(tween(durationMillis = 2000)) + fadeOut(tween(2000))
        ) {
            CustomTabRow(selectedId)
        }
        ContentAnimatedVisibility(visibility = isContentVisible) {
            HorizontalPager(
                state = pagerState,
                userScrollEnabled = false,
                verticalAlignment = Alignment.Top,
                modifier = Modifier.fillMaxSize().align(Alignment.Start)
            ) {
                LazyColumn(
                    verticalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier
                        .padding(16.dp),

                    ) {
                    item {
                        Text(
                            text = stringResource(id = R.string.shipments),
                            fontSize = 24.sp,
                            color = NavyBlue,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(Modifier.size(8.dp))
                    }
                    items(viewModel.getUpdatedStatusLabelList(selectedId.intValue)) { item ->
                        ShipmentStatusItem(item)
                    }
                }
            }
        }
    }
}

@Composable
fun ShipmentStatusItem(item: ShipmentStatus) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .shadow(elevation = 2.dp, shape = RoundedCornerShape(12.dp))
            .clip(RoundedCornerShape(12.dp))
            .background(color = Color.White)
            .padding(12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier
                .clip(shape = RoundedCornerShape(20.dp))
                .background(color = Color.Gray.copy(0.1f))
                .padding(horizontal = 16.dp, vertical = 4.dp)
        ) {
            Icon(
                imageVector = item.status.icon,
                tint = item.status.color,
                contentDescription = item.status.tag,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.size(4.dp))
            Text(
                text = item.status.tag, fontSize = 12.sp, color = item.status.color,
            )
        }
        Row {
            Column(modifier = Modifier.weight(1f)) {
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    text = item.message,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                    color = DarkBlue
                )
                Spacer(modifier = Modifier.size(6.dp))
                Text(text = item.details, fontSize = 14.sp, color = LightGrey, lineHeight = 18.sp)
                Spacer(modifier = Modifier.size(10.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = item.amount,
                        color = Purple,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Box(
                        modifier = Modifier
                            .padding(6.dp)
                            .size(8.dp)
                            .clip(shape = CircleShape)
                            .background(color = Color.Gray.copy(0.5f)),
                    )
                    Text(text = item.date, fontSize = 12.sp, color = DarkBlue)
                }
            }
            Image(
                painter = painterResource(id = R.drawable.box),
                modifier = Modifier.size(80.dp),
                contentDescription = "",
            )
        }
    }
}
