package uz.humoyun.evaluationassignmentleadsx.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import uz.humoyun.evaluationassignmentleadsx.R

@Preview
@Composable
fun BackButton(onBackPress: (() -> Unit)? = null) {
    IconButton(
        onClick = {
            onBackPress?.invoke()
        },
        modifier = Modifier
            .padding(start = 16.dp, end = 12.dp)
            .background(
                Color(0xFFF5F5F5),
                shape = RoundedCornerShape(8.dp)
            )
            .size(40.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_arrow_left),
            contentDescription = "back button"
        )
    }
}