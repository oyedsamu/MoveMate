import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.byoyedele.movemate.viewmodels.HomeViewModel
import com.byoyedele.movemate.R
import com.byoyedele.movemate.ui.theme.Orange
import com.byoyedele.movemate.ui.theme.Purple
import com.byoyedele.movemate.ui.utils.noRippleClickable

@Composable
fun CustomTopAppBar(
    viewModel: HomeViewModel,
    navController: NavHostController
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Purple)
            .padding(horizontal = 16.dp, vertical = 16.dp)

    ) {
        // Row
        Row {
            Image(
                modifier = Modifier
                    .clip(CircleShape)
                    .size(48.dp),
                painter = painterResource(id = R.drawable.profile_picture),
                contentDescription = stringResource(id = R.string.profile_picture),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.size(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                // Faded Text with starting
                Row {
                    Icon(
                        Icons.Default.LocationOn,
                        contentDescription = stringResource(id = R.string.location),
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = stringResource(id = R.string.your_location),
                        fontSize = 14.sp,
                        color = Color.White.copy(alpha = 0.70f)
                    )
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = viewModel.demoLocation, fontSize = 18.sp, color = Color.White)
                    Spacer(modifier = Modifier.size(8.dp))
                    Icon(
                        Icons.Default.KeyboardArrowDown,
                        contentDescription = stringResource(id = R.string.location),
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
            // Icon with circular background
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(Color.White)
                    .size(48.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Outlined.Notifications,
                    contentDescription = stringResource(id = R.string.notifications),
                    modifier = Modifier.size(30.dp)
                )
            }
        }
        Spacer(modifier = Modifier.size(16.dp))
        TextField(
            value = "", onValueChange = {},
            placeholder = {
                Text(
                    text = "Enter the receipt number...",
                    overflow = TextOverflow.Ellipsis
                )
            },
            enabled = false,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .clip(RoundedCornerShape(48.dp))
                .noRippleClickable { navController.navigate("search") },
            leadingIcon = { Icon(Icons.Outlined.Search, tint = Purple, contentDescription = "Search") },
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