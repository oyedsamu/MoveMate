package com.byoyedele.movemate.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.byoyedele.movemate.viewmodels.CalculateViewModel
import com.byoyedele.movemate.R
import com.byoyedele.movemate.ui.theme.DarkBlue
import com.byoyedele.movemate.ui.theme.NavyBlue
import com.byoyedele.movemate.ui.theme.SemiBoldTitleText
import com.byoyedele.movemate.ui.utils.ActionButton
import com.byoyedele.movemate.ui.utils.TitledAppBar
import com.byoyedele.movemate.ui.utils.bounceClick

@Composable
fun CalculateScreen(
    viewModel: CalculateViewModel,
    navController: NavController
) {
    Scaffold(
        topBar = {
            TitledAppBar(
                title = stringResource(id = R.string.calculate),
                onBackClick = { navController.popBackStack() })
        }
    ) { paddingValues ->
        CalculateBody(paddingValues = paddingValues, viewModel = viewModel, navController)
    }
}

@Composable
fun CalculateBody(
    paddingValues: PaddingValues,
    viewModel: CalculateViewModel,
    navController: NavController
) {
    Column(
        Modifier.fillMaxHeight(),
    ) {
        Column(
            Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .weight(1f)
        ) {
            Text(text = stringResource(id = R.string.destination), style = SemiBoldTitleText)
            Spacer(modifier = Modifier.size(12.dp))
            // Add Card with 3 entries
            Surface(
                modifier = Modifier
                    .shadow(elevation = 7.dp)
                    .clip(RoundedCornerShape(16.dp))
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .padding(16.dp)
                        .fillMaxWidth()
                        .background(Color.White)
                ) {
                    CustomTextField(
                        textValue = viewModel.senderLocation,
                        onValChange = { newVal -> viewModel.updateSenderLocation(newVal) },
                        placeHolder = stringResource(id = R.string.sender_location),
                        icon = {
                            Icon(
                                Icons.Outlined.Send,
                                contentDescription = "",
                                modifier = Modifier.padding(horizontal = 8.dp),
                                tint = Color.Gray
                            )
                        },
                    )
                    CustomTextField(
                        textValue = viewModel.receiverLocation,
                        onValChange = { newVal -> viewModel.updateReceiverLocation(newVal) },
                        placeHolder = stringResource(id = R.string.receiver_location),
                        icon = {
                            Icon(
                                Icons.Outlined.ArrowDropDown,
                                contentDescription = "",
                                modifier = Modifier.padding(horizontal = 8.dp),
                                tint = Color.Gray
                            )
                        },
                    )

                    CustomTextField(
                        textValue = viewModel.weight,
                        onValChange = { newVal -> viewModel.updateWeight(newVal) },
                        placeHolder = stringResource(id = R.string.approx_weight),
                        icon = {
                            Icon(
                                Icons.Outlined.Settings,
                                contentDescription = "",
                                modifier = Modifier.padding(horizontal = 8.dp),
                                tint = Color.Gray
                            )
                        },
                    )
                }
            }
            Spacer(modifier = Modifier.size(16.dp))
            Text(text = stringResource(id = R.string.packaging), style = SemiBoldTitleText)
            Text(text = stringResource(id = R.string.calculate_subtext), color = Color.Gray)
            Spacer(modifier = Modifier.size(8.dp))
            CustomTextField(
                textValue = viewModel.box,
                onValChange = { viewModel.updateBox(it) },
                placeHolder = stringResource(id = R.string.box),
                icon = {
                    Image(
                        painter = painterResource(id = R.drawable.box),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(8.dp)
                            .size(30.dp)
                    )
                },
                backgroundColor = Color.White,
                modifier = Modifier.shadow(elevation = 4.dp)
            )
            // Drop down panel
            Spacer(modifier = Modifier.size(16.dp))
            Text(text = stringResource(id = R.string.categories), style = SemiBoldTitleText)
            Text(text = stringResource(id = R.string.calculate_subtext), color = Color.Gray)
            val categories =
                listOf("Document", "Glass", "Liquid", "Food", "Electronic", "Product", "Others")
            FilterChipGroup(items = categories)
        }
        ActionButton(stringResource(id = R.string.calculate)) {
            navController.navigate("finalDestination") {
                popUpTo(navController.graph.startDestinationId)
                launchSingleTop = true
            }
        }
        Spacer(modifier = Modifier.size(16.dp))
    }
}

@Composable
private fun CustomTextField(
    textValue: String,
    onValChange: (String) -> Unit,
    placeHolder: String,
    icon: @Composable () -> Unit?,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color(0xFFf8f8f8),
) {
    Row(
        modifier = modifier
            .height(IntrinsicSize.Min)
            .clip(RoundedCornerShape(8.dp))
            .background(backgroundColor),
        verticalAlignment = Alignment.CenterVertically
    ) {
        icon()
        Divider(
            modifier = Modifier
                .fillMaxHeight()
                .padding(vertical = 12.dp)
                .width(1.dp)
        )
        OutlinedTextField(
            value = textValue,
            onValueChange = onValChange,
            colors = OutlinedTextFieldDefaults.colors(
                disabledBorderColor = Color.Transparent,
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                errorBorderColor = Color.Transparent,
                unfocusedContainerColor = backgroundColor,
                focusedContainerColor = backgroundColor,
                errorContainerColor = backgroundColor,
                focusedTextColor = DarkBlue,
                unfocusedTextColor = DarkBlue,
                errorTextColor = DarkBlue,
                unfocusedPlaceholderColor = Color.Gray.copy(0.8f),
                focusedPlaceholderColor = Color.Gray.copy(0.8f),
            ),
            placeholder = { Text(placeHolder) },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun FilterChipGroup(
    items: List<String>,
    defaultSelectedItemIndex: Int = -1,
    selectedItemIcon: ImageVector = Icons.Filled.Done,
    onSelectedChanged: (Int) -> Unit = {}
) {
    var selectedItemIndex by remember { mutableIntStateOf(0) }

    FlowRow {
        items.forEachIndexed { index, item ->
            val isSelected = items[selectedItemIndex] == item
            FilterChip(
                modifier = Modifier
                    .padding(end = 6.dp)
                    .bounceClick(),
                selected = if (defaultSelectedItemIndex == -1) false else isSelected,
                onClick = {
                    selectedItemIndex = index
                    onSelectedChanged(index)
                },
                label = { Text(item, color = if (isSelected) Color.White else NavyBlue) },
                leadingIcon = if (isSelected) {
                    {
                        Icon(
                            imageVector = selectedItemIcon,
                            contentDescription = "Localized Description",
                            modifier = Modifier.size(FilterChipDefaults.IconSize),
                            tint = Color.White
                        )
                    }
                } else null,
                colors = FilterChipDefaults.filterChipColors(
                    containerColor = if (isSelected) NavyBlue else MaterialTheme.colorScheme.background
                )
            )
        }
    }
}