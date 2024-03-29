package com.byoyedele.movemate.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.byoyedele.movemate.model.Item
import com.byoyedele.movemate.R
import com.byoyedele.movemate.ui.theme.NavyBlue
import com.byoyedele.movemate.ui.theme.Orange
import com.byoyedele.movemate.ui.theme.Purple
import com.byoyedele.movemate.ui.utils.BallPoint
import com.byoyedele.movemate.ui.utils.ContentAnimatedVisibility
import com.byoyedele.movemate.viewmodels.SearchViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
@Composable
fun SearchScreen(viewModel: SearchViewModel = viewModel(), navController: NavController) {
    var isContentVisible by remember { mutableStateOf(false) }
    val searchResults by viewModel.searchResults.collectAsStateWithLifecycle()

    LaunchedEffect("") {
        isContentVisible = true
    }
    Scaffold(
        topBar = {
            AppBar(
                searchQuery = viewModel.searchQuery,
                onSearchQueryChange = { viewModel.onSearchQueryChange(it) },
                onClick = { navController.popBackStack() }
            )
        },
    ) { paddingValues ->
        ContentAnimatedVisibility(visibility = isContentVisible) {
            SearchContent(searchResults, paddingValues)
        }
    }
}

@Composable
private fun AppBar(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Purple)
            .padding(end = 12.dp, top = 16.dp, bottom = 16.dp),
        verticalAlignment = Alignment.CenterVertically,

        ) {
        IconButton(onClick = onClick, modifier = Modifier.size(36.dp)) {
            Icon(Icons.Default.KeyboardArrowLeft, tint = Color.White, contentDescription = null)
        }
        TextField(
            value = searchQuery, onValueChange = onSearchQueryChange,
            placeholder = {
                Text(
                    text = "Enter the receipt number...",
                    overflow = TextOverflow.Ellipsis
                )
            },
            modifier = Modifier
                .padding(end = 8.dp)
                .fillMaxWidth()
                .height(56.dp)
                .clip(RoundedCornerShape(48.dp))
                .focusRequester(remember { FocusRequester() })
                .clickable { },
            leadingIcon = { Icon(Icons.Outlined.Search, contentDescription = "Search") },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                disabledContainerColor = Color.White,
                focusedPlaceholderColor = Color.Gray.copy(0.8f),
                unfocusedPlaceholderColor = Color.Gray.copy(0.8f),
            ),
            trailingIcon = {
                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .clip(CircleShape)
                        .background(Orange)
                        .size(40.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_scanner),
                        contentDescription = stringResource(id = R.string.scanner),
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        )
    }
}

@Composable
fun SearchContent(
    searchResults: List<Item>,
    paddingValues: PaddingValues,
) {
    AnimatedContent(targetState = searchResults, label = "", transitionSpec = {
        ContentTransform(
            targetContentEnter = slideInVertically(
                initialOffsetY = { fullHeight -> fullHeight },
                animationSpec = tween(durationMillis = 1000, easing = LinearOutSlowInEasing)
            ) + fadeIn(
                initialAlpha = 0f,
                animationSpec = tween(durationMillis = 1000, easing = LinearOutSlowInEasing)
            ),
            initialContentExit = slideOutVertically(
                targetOffsetY = { h -> h },
                animationSpec = tween(durationMillis = 500, easing = LinearOutSlowInEasing)
            ) + fadeOut(
                targetAlpha = 0f,
                animationSpec = tween(durationMillis = 500, easing = LinearOutSlowInEasing)
            ),
        )
    }) { newList ->
        Surface(
            modifier = Modifier
                .padding(top = paddingValues.calculateTopPadding())
                .padding(vertical = 12.dp, horizontal = 16.dp)
                .shadow(
                    elevation = 2.dp,
                    shape = RoundedCornerShape(16.dp)
                )
                .clip(RoundedCornerShape(12.dp))
        ) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .padding(16.dp)
            ) {
                items(
                    count = newList.size,
                    key = { index -> newList[index].name },
                    itemContent = { index ->
                        val item = newList[index]
                        ListItem(item = item)
                        if (index < newList.lastIndex) {
                            Spacer(modifier = Modifier.size(12.dp))
                            Divider(color = Color.Gray.copy(0.4f), thickness = 1.dp)
                        }
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ListItem(
    item: Item,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.Start,
        modifier = modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .background(Purple)
                .size(48.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.box),
                modifier = Modifier.size(40.dp),
                contentDescription = null
            )
        }
        Spacer(modifier = Modifier.size(12.dp))
        // Column with a text and a row with a text, a ball point and another text
        // forward arrow and another text
        Column {
            Text(text = item.name, fontSize = 18.sp, color = NavyBlue, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.size(4.dp))
            FlowRow(verticalArrangement = Arrangement.Bottom) {
                Text(text = item.id, fontSize = 12.sp, color = Color.Gray)
                // Ball point here
                Spacer(modifier = Modifier.size(6.dp))
                BallPoint(Color.Gray)
                Spacer(modifier = Modifier.size(6.dp))
                Text(text = item.origin, fontSize = 12.sp, color = Color.Gray)
                Spacer(modifier = Modifier.size(6.dp))
                Icon(
                    Icons.Default.ArrowForward,
                    modifier = Modifier.size(12.dp),
                    tint = Color.Gray,
                    contentDescription = ""
                )
                Spacer(modifier = Modifier.size(6.dp))
                Text(text = item.destination, fontSize = 12.sp, color = Color.Gray)
            }
        }
    }
}