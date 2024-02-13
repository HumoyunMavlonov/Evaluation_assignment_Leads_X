package uz.humoyun.evaluationassignmentleadsx.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import uz.humoyun.evaluationassignmentleadsx.R
import uz.humoyun.evaluationassignmentleadsx.data.response.LeadsGeneral
import uz.humoyun.evaluationassignmentleadsx.type.FetchLeadInput
import uz.humoyun.evaluationassignmentleadsx.ui.viewmodel.allleads.AllLeadViewModel
import uz.humoyun.evaluationassignmentleadsx.util.isScrolledToEnd

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllLeadsScreen(
    mainViewModel: AllLeadViewModel,
    onLeadClick: (Int) -> Unit,
    onCreateClick: () -> Unit
) {

    val leads by mainViewModel.leadsFlow.observeAsState(initial = listOf())
    val progress by mainViewModel.progressFlow.collectAsState(initial = false)
    val error by mainViewModel.errorFlow.collectAsState(initial = null)

    if (error != null) {
        mainViewModel.gotError()
    }

    val gotLoaded by mainViewModel.gotLoadedLead.observeAsState(initial = false)
    val item by mainViewModel.leadItemFlow.observeAsState(initial = null)

    LaunchedEffect(gotLoaded) {
        if (gotLoaded) {
            mainViewModel.gotLoaded()
            item?.let { onLeadClick(it.data.id) }
        }
    }


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color.White,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.White
                ),
                title = {
                    Text(
                        text = "Leads",
                        color = Color.Black,
                        fontWeight = FontWeight(700),
                        fontSize = 24.sp
                    )
                },
                actions = {
                    IconButton(
                        onClick = {
                            onCreateClick()
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
                            painter = painterResource(id = R.drawable.ic_add_user),
                            contentDescription = "create lead icon"
                        )
                    }
                    IconButton(
                        onClick = {
                            // TODO: search
                        },
                        modifier = Modifier
                            .padding(end = 16.dp)
                            .background(
                                Color(0xFFF5F5F5),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .size(40.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_search),
                            contentDescription = "search icon"
                        )
                    }
                }
            )
        }) { pad ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(pad)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 16.dp,
                            vertical = 8.dp
                        ),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    IconButton(
                        onClick = { /*TODO filter */ },
                        modifier = Modifier
                            .border(
                                width = 1.dp,
                                color = Color(0xFFEEEEEE),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .size(32.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_filter),
                            contentDescription = "filter button"
                        )
                    }
//
//                    FilterChip(selected = true, onClick = {
//
//                    }, label = {
//                        Text(text = "All leads")
//                    },
//                        colors = FilterChipDefaults.filterChipColors(
//                            selectedContainerColor = Color.Black,
//                            selectedLabelColor = Color.White,
//                            containerColor = Color.White,
//                            labelColor = Color.Black
//                        ),
//                        border = FilterChipDefaults.filterChipBorder(borderColor = Color(0xFFEEEEEE))
//                    )
//
//                    FilterChip(selected = false, onClick = {
//                        // TODO: select chip
//                    }, label = {
//                        Text(text = "Due today")
//                    },
//                        colors = FilterChipDefaults.filterChipColors(
//                            selectedContainerColor = Color.Black,
//                            selectedLabelColor = Color.White,
//                            containerColor = Color.White,
//                            labelColor = Color.Black
//                        ),
//                        border = FilterChipDefaults.filterChipBorder(borderColor = Color(0xFFEEEEEE))
//                    )

                }

                val lazyListState = rememberLazyListState()

                val endOfListReached by remember {
                    derivedStateOf {
                        lazyListState.isScrolledToEnd()
                    }
                }

                LaunchedEffect(endOfListReached) {
                    mainViewModel.getLeads()
                }

                LazyColumn(
                    state = lazyListState,
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(bottom = 120.dp)
                ) {
                    items(leads) { lead ->
                        ItemLead(lead) {
                            mainViewModel.getLead(FetchLeadInput(leadId = it))
                        }
                    }
                }

            }

            if (progress) {
                CircularProgressIndicator(
                    color = Color(0xD9000000), modifier = Modifier.align(
                        Alignment.Center
                    )
                )
            }
        }

    }
}


@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllLeadsScreenPreview() {

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color.White,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.White
                ),
                title = {
                    Text(
                        text = "Leads",
                        color = Color.Black,
                        fontWeight = FontWeight(700),
                        fontSize = 24.sp
                    )
                },
                actions = {
                    IconButton(
                        onClick = {
                            // TODO: create_lead
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
                            painter = painterResource(id = R.drawable.ic_add_user),
                            contentDescription = "create lead icon",
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    IconButton(
                        onClick = {
                            // TODO: search
                        },
                        modifier = Modifier
                            .padding(end = 16.dp)
                            .background(
                                Color(0xFFF5F5F5),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .size(40.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_search),
                            contentDescription = "search icon"
                        )
                    }
                }
            )
        }) { pad ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(pad)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 16.dp,
                        vertical = 8.dp
                    ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                IconButton(
                    onClick = { /*TODO filter */ },
                    modifier = Modifier
                        .border(
                            width = 1.dp,
                            color = Color(0xFFEEEEEE),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .size(32.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_filter),
                        contentDescription = "filter button"
                    )
                }
//
//                FilterChip(selected = true, onClick = {
//                    // TODO: select chip
//                }, label = {
//                    Text(text = "All leads")
//                },
//                    colors = FilterChipDefaults.filterChipColors(
//                        selectedContainerColor = Color.Black,
//                        selectedLabelColor = Color.White,
//                        containerColor = Color.White,
//                        labelColor = Color.Black
//                    ),
//                    border = FilterChipDefaults.filterChipBorder(borderColor = Color(0xFFEEEEEE))
//                )
//
//                FilterChip(selected = false, onClick = {
//                    // TODO: select chip
//                }, label = {
//                    Text(text = "Due today")
//                },
//                    colors = FilterChipDefaults.filterChipColors(
//                        selectedContainerColor = Color.Black,
//                        selectedLabelColor = Color.White,
//                        containerColor = Color.White,
//                        labelColor = Color.Black
//                    ),
//                    border = FilterChipDefaults.filterChipBorder(borderColor = Color(0xFFEEEEEE))
//                )

            }

            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(20) {
                    ItemLeadPreview()
                }
            }

        }
    }
}


@Composable
fun ItemLead(data: LeadsGeneral, onLeadClick: (Int) -> Unit) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .background(Color.White)
            .clickable(interactionSource = remember {
                MutableInteractionSource()
            }, indication = rememberRipple()) {
                onLeadClick.invoke(data.id)
            }
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            AsyncImage(
                model = data.avatar.path,
                contentDescription = "avatar",
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .border(
                        width = 1.dp,
                        color = Color(0xFFE2E2E2),
                        shape = CircleShape
                    )
            )

            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.Start
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${data.firstName} ${data.lastName}",
                        fontSize = 16.sp,
                        lineHeight = 24.sp,
                        fontWeight = FontWeight(500),
                        color = Color(0xFF212121),
                    )

                    Text(
                        text = data.countryDto?.emoji ?: "",
                        modifier = Modifier
                            .clip(CircleShape),
                    )

                }

                Text(
                    text = "Follow up: ${data.createdAt}",
                    fontSize = 12.sp,
                    lineHeight = 16.sp,
                    fontWeight = FontWeight(400),
                    color = Color(0xFF757575),
                )
            }

        }

        StatusBadge(
            textColor = Color(android.graphics.Color.parseColor(data.status.color)),
            text = "Option Sent",
            backgroundColor = Color(android.graphics.Color.parseColor(data.status.backgroundColor))
        )

    }

}

@Composable
@Preview
fun ItemLeadPreview() {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .background(Color.White)
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            AsyncImage(
                model = null,
                contentDescription = "avatar",
                modifier = Modifier
                    .size(40.dp)
                    .border(
                        width = 1.dp,
                        color = Color(0xFFE2E2E2),
                        shape = CircleShape
                    )
            )

            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.Start
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Jane Cooper",
                        fontSize = 16.sp,
                        lineHeight = 24.sp,
                        fontWeight = FontWeight(500),
                        color = Color(0xFF212121),
                    )

                    AsyncImage(
                        model = null,
                        contentDescription = "country image",
                        modifier = Modifier
                            .size(16.dp)
                            .clip(CircleShape)
                            .background(Color(240, 240, 240)),
                    )

                }

                Text(
                    text = "Follow up: March 13, 2023",
                    fontSize = 12.sp,
                    lineHeight = 16.sp,
                    fontWeight = FontWeight(400),
                    color = Color(0xFF757575),
                )
            }

        }

        StatusBadge(
            textColor = Color(237, 110, 51),
            text = "Option Sent"
        )

    }

}

@Composable
@Preview
fun StatusBadge(
    textColor: Color = Color(58, 167, 109),
    text: String = "New",
    backgroundColor: Color = Color(58, 167, 109, 25)
) {
    Text(
        text = "New",
        color = textColor,
        modifier = Modifier
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(100.dp)
            )
            .padding(horizontal = 8.dp),
        fontSize = 12.sp,
        fontWeight = FontWeight(700)
    )
}