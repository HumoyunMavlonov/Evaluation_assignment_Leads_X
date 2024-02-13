package uz.humoyun.evaluationassignmentleadsx.ui.bottomsheet

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uz.humoyun.evaluationassignmentleadsx.data.response.StatusData
import uz.humoyun.evaluationassignmentleadsx.ui.viewmodel.leaddetails.LeadDetailViewModel


@Composable
fun StatusBottomSheet(viewModel: LeadDetailViewModel, onStatusClick: (StatusData) -> Unit) {

    val statuses by viewModel.statuses.observeAsState(initial = listOf())
    val leadItem by viewModel.leadItemFlow.observeAsState(initial = null)
    val hasLoadedStatuses by viewModel.hasLoadedStatuses.observeAsState(initial = false)
    val progress by viewModel.progressFlow.collectAsState(initial = false)

    var selectedStatus by remember {
        mutableStateOf(leadItem?.data?.status)
    }

    LaunchedEffect(key1 = null) {
        if (!hasLoadedStatuses) {
            viewModel.getStatuses()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 90.dp)
        ) {
            items(statuses) {
                ItemStatus(selected = selectedStatus?.id == it.id, it) { status ->
                    selectedStatus = status
                }
            }
        }

        Text(
            text = "Apply",
            fontSize = 16.sp,
            fontWeight = FontWeight(700),
            color = Color(0xFFFFFFFF),
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(56.dp)
                .background(Color.Black, RoundedCornerShape(12.dp))
                .clickable {
                    if (!progress) selectedStatus?.let { onStatusClick.invoke(it) }
                },
            textAlign = TextAlign.Center
        )

        if (progress) {
            CircularProgressIndicator(
                color = Color(0xD9000000), modifier = Modifier.align(
                    Alignment.Center
                )
            )
        }


    }
}


@Preview
@Composable
fun ItemStatus(
    selected: Boolean = true,
    data: StatusData = StatusData(0, 0, 0, "title", "#FFA726", "#FFA726"),
    onSelect: ((StatusData) -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .clip(RoundedCornerShape(12.dp))
            .border(
                width = 1.dp,
                color = if (selected) Color(0xFF000000) else Color(0xFFEEEEEE),
                shape = RoundedCornerShape(size = 12.dp)
            )
            .background(Color.White)
            .padding(
                vertical = 8.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selected,
            onClick = { onSelect?.invoke(data) },
            colors = RadioButtonDefaults.colors(
                selectedColor = Color(
                    android.graphics.Color.parseColor(data.color)
                ),
                unselectedColor = Color(android.graphics.Color.parseColor(data.color))
            )
        )
        Text(
            text = data.title,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            color = Color(0xFF000000),
            modifier = Modifier.padding(start = 4.dp)
        )
    }
}