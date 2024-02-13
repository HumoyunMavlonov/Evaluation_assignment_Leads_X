package uz.humoyun.evaluationassignmentleadsx.ui.screen

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.apollographql.apollo3.api.Optional
import kotlinx.coroutines.launch
import uz.humoyun.evaluationassignmentleadsx.R
import uz.humoyun.evaluationassignmentleadsx.type.FetchLeadInput
import uz.humoyun.evaluationassignmentleadsx.type.UpdateLeadInput
import uz.humoyun.evaluationassignmentleadsx.ui.bottomsheet.StatusBottomSheet
import uz.humoyun.evaluationassignmentleadsx.ui.theme.BackButton
import uz.humoyun.evaluationassignmentleadsx.ui.viewmodel.leaddetails.LeadDetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LeadDetailScreen(
    id: Int,
    viewModel: LeadDetailViewModel,
    onBackPress: () -> Unit,
    onEditClick: (Int) -> Unit
) {

    val density = LocalDensity.current
    val itemFlow by viewModel.leadItemFlow.observeAsState()
    val progress by viewModel.progressFlow.collectAsState(initial = false)

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.White
                ),
                navigationIcon = {
                    BackButton(onBackPress = {
                        onBackPress()
                    })
                },
                title = {
                    Text(
                        text = "Lead details",
                        fontSize = 16.sp,
                        fontWeight = FontWeight(700),
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                    )
                },
                actions = {
                    IconButton(
                        onClick = {
                            viewModel.getLead(FetchLeadInput(id))
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
                            painter = painterResource(id = R.drawable.ic_refresh),
                            contentDescription = "refresh"
                        )
                    }
                }
            )
        },
        containerColor = Color.White
    ) { pad ->


        val coroutineScope = rememberCoroutineScope()

        val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)
        var showSheet by remember {
            mutableStateOf(false)
        }

        if (showSheet) ModalBottomSheet(
            onDismissRequest = {
                coroutineScope.launch {
                    sheetState.hide()
                    showSheet = false
                }
            },
            sheetState = sheetState,
            shape = RoundedCornerShape(topEnd = 20.dp, topStart = 20.dp),
            contentColor = Color.White,
            containerColor = Color.White,
            modifier = Modifier.fillMaxSize(),
            dragHandle = { BottomSheetDefaults.DragHandle() },
        ) {

            StatusBottomSheet(viewModel = viewModel, onStatusClick = {
                viewModel.updateLead(
                    UpdateLeadInput(
                        leadId = id,
                        statusId = Optional.present(it.id)
                    )
                )
            })

        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(pad)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                contentPadding = PaddingValues(
                    start = 16.dp,
                    end = 16.dp,
                    top = 8.dp,
                    bottom = 20.dp
                )
            ) {
                item {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        AsyncImage(
                            model = itemFlow?.data?.avatar?.path,
                            contentDescription = "avatar",
                            modifier = Modifier
                                .size(56.dp)
                                .border(
                                    width = 1.dp,
                                    color = Color(0xFFE2E2E2),
                                    shape = CircleShape
                                )
                                .clip(CircleShape)
                                .background(Color(240, 240, 240))
                        )

                        Text(
                            text = "${itemFlow?.data?.firstName} ${itemFlow?.data?.lastName}",
                            fontSize = 16.sp,
                            fontWeight = FontWeight(500),
                            color = Color(0xFF212121),
                            modifier = Modifier.padding(top = 8.dp)
                        )

                        Text(
                            text = "ID: ${itemFlow?.data?.id}",
                            fontSize = 12.sp,
                            fontWeight = FontWeight(400),
                            color = Color(0xFF757575),
                            modifier = Modifier.padding(top = 2.dp)
                        )
                        Column(
                            modifier = Modifier
                                .padding(
                                    vertical = 16.dp
                                )
                                .fillMaxWidth()
                                .border(
                                    width = 1.dp,
                                    color = Color(0xFFEEEEEE),
                                    shape = RoundedCornerShape(size = 8.dp)
                                )
                                .padding(top = 12.dp, start = 12.dp, end = 12.dp, bottom = 26.dp)
                        ) {

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                Text(
                                    text = "Lead status",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight(500),
                                    color = Color(0xFF000000),
                                )

                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    AsyncImage(
                                        model = null,
                                        contentDescription = "null",
                                        modifier = Modifier
                                            .size(6.dp)
                                            .clip(CircleShape)
                                            .background(
                                                try {
                                                    Color(
                                                        android.graphics.Color.parseColor(
                                                            itemFlow?.data?.status?.color ?: ""
                                                        )
                                                    )
                                                } catch (
                                                    _: Exception
                                                ) {
                                                    Color(0xFF5F5F5F)
                                                }
                                            )
                                    )

                                    Text(
                                        text = itemFlow?.data?.status?.title ?: "",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight(600),
                                        color = Color(0xFF757575),
                                    )
                                }
                            }

                            DashedProgressIndicator(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 8.dp)
                                    .clickable(interactionSource = remember {
                                        MutableInteractionSource()
                                    }, indication = null) {
                                        showSheet = true
                                        coroutineScope.launch {
                                            sheetState.show()
                                        }
                                    },
                                progress = itemFlow?.data?.status?.step ?: 0,
                                totalNumberOfBars = itemFlow?.data?.status?.stepsCount ?: 10,
                                thickness = 12.dp.value * density.density,
                                progressColor = try {
                                    Color(
                                        android.graphics.Color.parseColor(
                                            itemFlow?.data?.status?.color ?: ""
                                        )
                                    )
                                } catch (_: java.lang.Exception) {
                                    Color(255, 183, 77)
                                },
                                trackColor = try {
                                    Color(
                                        android.graphics.Color.parseColor(
                                            itemFlow?.data?.status?.backgroundColor ?: ""
                                        )
                                    )
                                } catch (_: java.lang.Exception) {
                                    Color(
                                        245,
                                        245,
                                        245
                                    )
                                }
                            )

                        }
                        Column(
                            modifier = Modifier
                                .padding(
                                    bottom = 16.dp
                                )
                                .fillMaxWidth()
                                .border(
                                    width = 1.dp,
                                    color = Color(0xFFEEEEEE),
                                    shape = RoundedCornerShape(size = 8.dp)
                                )
                                .padding(12.dp)
                        ) {

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                Text(
                                    text = "Lead quality",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight(500),
                                    color = Color(0xFF000000),
                                )
                                Text(
                                    text = "${itemFlow?.data?.quality ?: 0}%",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight(600),
                                    color = Color(0xFF000000),
                                )
                            }

                            LinearRoundedProgressIndicator(
                                progress = (itemFlow?.data?.quality ?: 0) * 1f / 100,
                                modifier = Modifier
                                    .padding(top = 8.dp)
                                    .fillMaxWidth()
                                    .height(12.dp)
                                    .clip(RoundedCornerShape(100.dp)),
                                progressColor = Color.Black,
                                trackColor = Color(0xFFEEEEEE),
                                thickness = 12.dp.value * (density.density),
                                cornerRadius = 100.dp
                            )

                        }

                    }
                }
                item {
                    Column(Modifier.fillMaxWidth()) {
                        Row(
                            Modifier
                                .padding(
                                    vertical = 8.dp
                                )
                                .fillMaxWidth()
                                .border(
                                    width = 1.dp,
                                    color = Color(0xFFEEEEEE),
                                    shape = RoundedCornerShape(size = 8.dp)
                                )
                                .height(40.dp)
                                .background(
                                    color = Color(0xFFFAFAFA),
                                    shape = RoundedCornerShape(size = 8.dp)
                                )
                                .padding(start = 2.dp, top = 2.dp, end = 2.dp, bottom = 2.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            FilterChip(
                                selected = true,
                                onClick = {
                                    // TODO: select chip
                                },
                                label = {
                                    Text(
                                        text = "Info",
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .weight(1f),
                                        textAlign = TextAlign.Center
                                    )
                                },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = Color.Black,
                                    selectedLabelColor = Color.White,
                                    containerColor = Color(0xFFFAFAFA),
                                    labelColor = Color(0xFF616161)
                                ),
                                border = FilterChipDefaults.filterChipBorder(
                                    borderColor = Color(0xFFFAFAFA)
                                ),
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .weight(1f)
                            )

                            Divider(
                                modifier = Modifier
                                    .height(16.dp)
                                    .width(1.dp)
                                    .weight(0.01f),
                                thickness = 1.dp,
                                color = Color(0xFFEEEEEE)
                            )

                            FilterChip(
                                selected = false,
                                onClick = {
                                    // TODO: select chip
                                },
                                label = {
                                    Text(
                                        text = "Activity",
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .weight(1f),
                                        textAlign = TextAlign.Center
                                    )
                                },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = Color.Black,
                                    selectedLabelColor = Color.White,
                                    containerColor = Color(0xFFFAFAFA),
                                    labelColor = Color(0xFF616161)
                                ),
                                border = FilterChipDefaults.filterChipBorder(
                                    borderColor = Color(
                                        0xFFFAFAFA
                                    )
                                ),
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .weight(1f)
                            )

                            Divider(
                                modifier = Modifier
                                    .height(16.dp)
                                    .width(1.dp)
                                    .weight(0.01f),
                                thickness = 1.dp,
                                color = Color(0xFFEEEEEE)
                            )

                            FilterChip(
                                selected = false,
                                onClick = {
                                    // TODO: select chip
                                },
                                label = {
                                    Text(
                                        text = "Analytics",
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .weight(1f),
                                        textAlign = TextAlign.Center
                                    )
                                },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = Color.Black,
                                    selectedLabelColor = Color.White,
                                    containerColor = Color(0xFFFAFAFA),
                                    labelColor = Color(0xFF616161)
                                ),
                                border = FilterChipDefaults.filterChipBorder(
                                    borderColor = Color(
                                        0xFFFAFAFA
                                    )
                                ),
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .weight(1f),
                                leadingIcon = {},

                                )

                        }

                        Row(
                            modifier = Modifier
                                .padding(vertical = 8.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {

                            Text(
                                text = "General info",
                                fontSize = 20.sp,
                                lineHeight = 32.sp,
                                fontWeight = FontWeight(700),
                                color = Color(0xFF000000),
                            )


                            IconButton(
                                onClick = {
                                    onEditClick(id)
                                },
                                modifier = Modifier
                                    .background(
                                        Color(0xFFF5F5F5),
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    .size(40.dp)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.pen_square),
                                    contentDescription = "edit icon"
                                )
                            }
                        }


                        Text(
                            text = "Lead intention",
                            fontSize = 12.sp,
                            fontWeight = FontWeight(500),
                            color = Color(0xFF000000),
                            letterSpacing = 0.5.sp
                        )

                        OutlinedTextField(
                            value = "${itemFlow?.data?.intention?.title}",
                            onValueChange = {

                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.Black,
                                unfocusedTextColor = Color.Black,
                                disabledTextColor = Color(0xFF616161),
                                disabledBorderColor = Color(0xFFEEEEEE),
                                cursorColor = Color.Black,
                                focusedBorderColor = Color.Black,
                                unfocusedBorderColor = Color(0xFF616161),
                                unfocusedPlaceholderColor = Color(0xFF616161),
                                focusedPlaceholderColor = Color(0xFF616161),
                                disabledPlaceholderColor = Color(0xFF616161),
                                focusedContainerColor = Color(0xFFF5F5F5),
                                unfocusedContainerColor = Color(0xFFF5F5F5),
                                disabledContainerColor = Color(0xFFF5F5F5)
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 4.dp),
                            placeholder = {
                                Text(text = "Select")
                            },
                            shape = RoundedCornerShape(12.dp),
                            enabled = false,
                            trailingIcon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_arrow_down),
                                    contentDescription = ""
                                )
                            },
                            readOnly = true,
                            singleLine = true,
                            textStyle = TextStyle.Default.copy(fontSize = 16.sp)
                        )


                        Text(
                            text = "AD Source",
                            fontSize = 12.sp,
                            fontWeight = FontWeight(500),
                            color = Color(0xFF000000),
                            letterSpacing = 0.5.sp,
                            modifier = Modifier.padding(top = 12.dp)
                        )

                        OutlinedTextField(
                            value = "${itemFlow?.data?.adSource?.title}",
                            onValueChange = {

                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.Black,
                                unfocusedTextColor = Color.Black,
                                disabledTextColor = Color(0xFF616161),
                                disabledBorderColor = Color(0xFFEEEEEE),
                                cursorColor = Color.Black,
                                focusedBorderColor = Color.Black,
                                unfocusedBorderColor = Color(0xFF616161),
                                unfocusedPlaceholderColor = Color(0xFF616161),
                                focusedPlaceholderColor = Color(0xFF616161),
                                disabledPlaceholderColor = Color(0xFF616161),
                                focusedContainerColor = Color(0xFFF5F5F5),
                                unfocusedContainerColor = Color(0xFFF5F5F5),
                                disabledContainerColor = Color(0xFFF5F5F5)
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 4.dp),
                            placeholder = {
                                Text(text = "Select")
                            },
                            shape = RoundedCornerShape(12.dp),
                            enabled = false,
                            trailingIcon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_arrow_down),
                                    contentDescription = ""
                                )
                            },
                            readOnly = true,
                            singleLine = true,
                            textStyle = TextStyle.Default.copy(fontSize = 16.sp)
                        )

                        Text(
                            text = "Country",
                            fontSize = 12.sp,
                            fontWeight = FontWeight(500),
                            color = Color(0xFF000000),
                            letterSpacing = 0.5.sp,
                            modifier = Modifier.padding(top = 12.dp)
                        )

                        OutlinedTextField(
                            value = "${itemFlow?.data?.country?.title}",
                            onValueChange = {

                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.Black,
                                unfocusedTextColor = Color.Black,
                                disabledTextColor = Color(0xFF616161),
                                disabledBorderColor = Color(0xFFEEEEEE),
                                cursorColor = Color.Black,
                                focusedBorderColor = Color.Black,
                                unfocusedBorderColor = Color(0xFF616161),
                                unfocusedPlaceholderColor = Color(0xFF616161),
                                focusedPlaceholderColor = Color(0xFF616161),
                                disabledPlaceholderColor = Color(0xFF616161),
                                focusedContainerColor = Color(0xFFF5F5F5),
                                unfocusedContainerColor = Color(0xFFF5F5F5),
                                disabledContainerColor = Color(0xFFF5F5F5)
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 4.dp),
                            placeholder = {
                                Text(text = "Select")
                            },
                            shape = RoundedCornerShape(12.dp),
                            enabled = false,
                            trailingIcon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_arrow_down),
                                    contentDescription = ""
                                )
                            },
                            readOnly = true,
                            singleLine = true,
                            textStyle = TextStyle.Default.copy(fontSize = 16.sp)
                        )

                        Text(
                            text = "City",
                            fontSize = 12.sp,
                            fontWeight = FontWeight(500),
                            color = Color(0xFF000000),
                            letterSpacing = 0.5.sp,
                            modifier = Modifier.padding(top = 12.dp)
                        )

                        OutlinedTextField(
                            value = "${itemFlow?.data?.city?.title}",
                            onValueChange = {

                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.Black,
                                unfocusedTextColor = Color.Black,
                                disabledTextColor = Color(0xFF616161),
                                disabledBorderColor = Color(0xFFEEEEEE),
                                cursorColor = Color.Black,
                                focusedBorderColor = Color.Black,
                                unfocusedBorderColor = Color(0xFF616161),
                                unfocusedPlaceholderColor = Color(0xFF616161),
                                focusedPlaceholderColor = Color(0xFF616161),
                                disabledPlaceholderColor = Color(0xFF616161),
                                focusedContainerColor = Color(0xFFF5F5F5),
                                unfocusedContainerColor = Color(0xFFF5F5F5),
                                disabledContainerColor = Color(0xFFF5F5F5)
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 4.dp),
                            placeholder = {
                                Text(text = "Select")
                            },
                            shape = RoundedCornerShape(12.dp),
                            enabled = false,
                            trailingIcon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_arrow_down),
                                    contentDescription = ""
                                )
                            },
                            readOnly = true,
                            singleLine = true,
                            textStyle = TextStyle.Default.copy(fontSize = 16.sp)
                        )

                        Text(
                            text = "Language",
                            fontSize = 12.sp,
                            fontWeight = FontWeight(500),
                            color = Color(0xFF000000),
                            letterSpacing = 0.5.sp,
                            modifier = Modifier.padding(top = 12.dp)
                        )

                        OutlinedTextField(
                            value = "${itemFlow?.data?.languages?.joinToString { it.title }}",
                            onValueChange = {

                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.Black,
                                unfocusedTextColor = Color.Black,
                                disabledTextColor = Color(0xFF616161),
                                disabledBorderColor = Color(0xFFEEEEEE),
                                cursorColor = Color.Black,
                                focusedBorderColor = Color.Black,
                                unfocusedBorderColor = Color(0xFF616161),
                                unfocusedPlaceholderColor = Color(0xFF616161),
                                focusedPlaceholderColor = Color(0xFF616161),
                                disabledPlaceholderColor = Color(0xFF616161),
                                focusedContainerColor = Color(0xFFF5F5F5),
                                unfocusedContainerColor = Color(0xFFF5F5F5),
                                disabledContainerColor = Color(0xFFF5F5F5)
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 4.dp),
                            placeholder = {
                                Text(text = "Select")
                            },
                            shape = RoundedCornerShape(12.dp),
                            enabled = false,
                            trailingIcon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_arrow_down),
                                    contentDescription = ""
                                )
                            },
                            readOnly = true,
                            singleLine = true,
                            textStyle = TextStyle.Default.copy(fontSize = 16.sp)
                        )

                        Text(
                            text = "Phone number",
                            fontSize = 12.sp,
                            fontWeight = FontWeight(500),
                            color = Color(0xFF000000),
                            letterSpacing = 0.5.sp,
                            modifier = Modifier.padding(top = 12.dp)
                        )

                        OutlinedTextField(
                            value = itemFlow?.data?.contacts?.data?.find { it.phoneContact.title.length > 1 }?.phoneContact?.title
                                ?: "",
                            onValueChange = {

                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.Black,
                                unfocusedTextColor = Color.Black,
                                disabledTextColor = Color(0xFF616161),
                                disabledBorderColor = Color(0xFFEEEEEE),
                                cursorColor = Color.Black,
                                focusedBorderColor = Color.Black,
                                unfocusedBorderColor = Color(0xFF616161),
                                unfocusedPlaceholderColor = Color(0xFF616161),
                                focusedPlaceholderColor = Color(0xFF616161),
                                disabledPlaceholderColor = Color(0xFF616161),
                                focusedContainerColor = Color(0xFFF5F5F5),
                                unfocusedContainerColor = Color(0xFFF5F5F5),
                                disabledContainerColor = Color(0xFFF5F5F5)
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 4.dp),
                            placeholder = {
                                Text(text = "Ex: +1234 5678 9012")
                            },
                            shape = RoundedCornerShape(12.dp),
                            enabled = false,
                            readOnly = true,
                            singleLine = true,
                            textStyle = TextStyle.Default.copy(fontSize = 16.sp)
                        )

                        Text(
                            text = "Email",
                            fontSize = 12.sp,
                            fontWeight = FontWeight(500),
                            color = Color(0xFF000000),
                            letterSpacing = 0.5.sp,
                            modifier = Modifier.padding(top = 12.dp)
                        )

                        OutlinedTextField(
                            value = itemFlow?.data?.contacts?.data?.find { it.emailContact.title.length > 1 }?.emailContact?.title
                                ?: "",
                            onValueChange = {

                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.Black,
                                unfocusedTextColor = Color.Black,
                                disabledTextColor = Color(0xFF616161),
                                disabledBorderColor = Color(0xFFEEEEEE),
                                cursorColor = Color.Black,
                                focusedBorderColor = Color.Black,
                                unfocusedBorderColor = Color(0xFF616161),
                                unfocusedPlaceholderColor = Color(0xFF616161),
                                focusedPlaceholderColor = Color(0xFF616161),
                                disabledPlaceholderColor = Color(0xFF616161),
                                focusedContainerColor = Color(0xFFF5F5F5),
                                unfocusedContainerColor = Color(0xFFF5F5F5),
                                disabledContainerColor = Color(0xFFF5F5F5)
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 4.dp),
                            placeholder = {
                                Text(text = "Ex: example@mail.com")
                            },
                            shape = RoundedCornerShape(12.dp),
                            enabled = false,
                            readOnly = true,
                            singleLine = true,
                            textStyle = TextStyle.Default.copy(fontSize = 16.sp)
                        )
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
fun LeadDetailScreenPreview() {

    val density = LocalDensity.current

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.White
                ),
                navigationIcon = {
                    BackButton(onBackPress = {
                        // TODO: back button
                    })
                },
                title = {
                    Text(
                        text = "Lead details",
                        fontSize = 16.sp,
                        fontWeight = FontWeight(700),
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                    )
                },
                actions = {
                    IconButton(
                        onClick = {
                            // TODO: refresh
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
                            painter = painterResource(id = R.drawable.ic_refresh),
                            contentDescription = "refresh"
                        )
                    }
                }
            )
        },
        containerColor = Color.White
    ) { pad ->
        LazyColumn(
            modifier = Modifier
                .padding(pad)
                .fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
        ) {
            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    AsyncImage(
                        model = null,
                        contentDescription = "avatar",
                        modifier = Modifier
                            .size(56.dp)
                            .border(
                                width = 1.dp,
                                color = Color(0xFFE2E2E2),
                                shape = CircleShape
                            )
                            .clip(CircleShape)
                            .background(Color(240, 240, 240))
                    )

                    Text(
                        text = "Jane Cooper",
                        fontSize = 16.sp,
                        fontWeight = FontWeight(500),
                        color = Color(0xFF212121),
                        modifier = Modifier.padding(top = 8.dp)
                    )

                    Text(
                        text = "ID: 234234",
                        fontSize = 12.sp,
                        fontWeight = FontWeight(400),
                        color = Color(0xFF757575),
                        modifier = Modifier.padding(top = 2.dp)
                    )
                    Column(
                        modifier = Modifier
                            .padding(
                                vertical = 16.dp
                            )
                            .fillMaxWidth()
                            .border(
                                width = 1.dp,
                                color = Color(0xFFEEEEEE),
                                shape = RoundedCornerShape(size = 8.dp)
                            )
                            .padding(top = 12.dp, start = 12.dp, end = 12.dp, bottom = 26.dp)
                    ) {

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Text(
                                text = "Lead status",
                                fontSize = 14.sp,
                                fontWeight = FontWeight(500),
                                color = Color(0xFF000000),
                            )

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                AsyncImage(
                                    model = null,
                                    contentDescription = "null",
                                    modifier = Modifier
                                        .size(6.dp)
                                        .clip(CircleShape)
                                        .background(Color(0xFF444444))
                                )

                                Text(
                                    text = "Option sent",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight(600),
                                    color = Color(0xFF757575),
                                )
                            }
                        }

                        DashedProgressIndicator(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp),
                            progress = 3,
                            totalNumberOfBars = 10,
                            thickness = 12.dp.value * density.density
                        )

                    }
                    Column(
                        modifier = Modifier
                            .padding(
                                bottom = 16.dp
                            )
                            .fillMaxWidth()
                            .border(
                                width = 1.dp,
                                color = Color(0xFFEEEEEE),
                                shape = RoundedCornerShape(size = 8.dp)
                            )
                            .padding(12.dp)
                    ) {

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Text(
                                text = "Lead quality",
                                fontSize = 14.sp,
                                fontWeight = FontWeight(500),
                                color = Color(0xFF000000),
                            )
                            Text(
                                text = "35%",
                                fontSize = 14.sp,
                                fontWeight = FontWeight(600),
                                color = Color(0xFF000000),
                            )
                        }

                        LinearRoundedProgressIndicator(
                            progress = 0.35f,
                            modifier = Modifier
                                .padding(top = 8.dp)
                                .fillMaxWidth()
                                .height(12.dp)
                                .clip(RoundedCornerShape(100.dp)),
                            progressColor = Color.Black,
                            trackColor = Color(0xFFEEEEEE),
                            thickness = 12.dp.value * (density.density),
                            cornerRadius = 100.dp
                        )

                    }

                }
            }
            item {
                Column(Modifier.fillMaxWidth()) {
                    Row(
                        Modifier
                            .padding(
                                vertical = 8.dp
                            )
                            .fillMaxWidth()
                            .border(
                                width = 1.dp,
                                color = Color(0xFFEEEEEE),
                                shape = RoundedCornerShape(size = 8.dp)
                            )
                            .height(40.dp)
                            .background(
                                color = Color(0xFFFAFAFA),
                                shape = RoundedCornerShape(size = 8.dp)
                            )
                            .padding(start = 2.dp, top = 2.dp, end = 2.dp, bottom = 2.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        FilterChip(
                            selected = true,
                            onClick = {
                                // TODO: select chip
                            },
                            label = {
                                Text(
                                    text = "Info",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .weight(1f),
                                    textAlign = TextAlign.Center
                                )
                            },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = Color.Black,
                                selectedLabelColor = Color.White,
                                containerColor = Color(0xFFFAFAFA),
                                labelColor = Color(0xFF616161)
                            ),
                            border = FilterChipDefaults.filterChipBorder(
                                borderColor = Color(0xFFFAFAFA)
                            ),
                            modifier = Modifier
                                .fillMaxHeight()
                                .weight(1f)
                        )

                        Divider(
                            modifier = Modifier
                                .height(16.dp)
                                .width(1.dp)
                                .weight(0.01f),
                            thickness = 1.dp,
                            color = Color(0xFFEEEEEE)
                        )

                        FilterChip(
                            selected = false,
                            onClick = {
                                // TODO: select chip
                            },
                            label = {
                                Text(
                                    text = "Activity",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .weight(1f),
                                    textAlign = TextAlign.Center
                                )
                            },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = Color.Black,
                                selectedLabelColor = Color.White,
                                containerColor = Color(0xFFFAFAFA),
                                labelColor = Color(0xFF616161)
                            ),
                            border = FilterChipDefaults.filterChipBorder(
                                borderColor = Color(
                                    0xFFFAFAFA
                                )
                            ),
                            modifier = Modifier
                                .fillMaxHeight()
                                .weight(1f)
                        )

                        Divider(
                            modifier = Modifier
                                .height(16.dp)
                                .width(1.dp)
                                .weight(0.01f),
                            thickness = 1.dp,
                            color = Color(0xFFEEEEEE)
                        )

                        FilterChip(
                            selected = false,
                            onClick = {
                                // TODO: select chip
                            },
                            label = {
                                Text(
                                    text = "Analytics",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .weight(1f),
                                    textAlign = TextAlign.Center
                                )
                            },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = Color.Black,
                                selectedLabelColor = Color.White,
                                containerColor = Color(0xFFFAFAFA),
                                labelColor = Color(0xFF616161)
                            ),
                            border = FilterChipDefaults.filterChipBorder(
                                borderColor = Color(
                                    0xFFFAFAFA
                                )
                            ),
                            modifier = Modifier
                                .fillMaxHeight()
                                .weight(1f),
                            leadingIcon = {},

                            )

                    }

                    Row(
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        Text(
                            text = "General info",
                            fontSize = 20.sp,
                            lineHeight = 32.sp,
                            fontWeight = FontWeight(700),
                            color = Color(0xFF000000),
                        )


                        IconButton(
                            onClick = {
                                // TODO: edit
                            },
                            modifier = Modifier
                                .background(
                                    Color(0xFFF5F5F5),
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .size(40.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.pen_square),
                                contentDescription = "edit icon"
                            )
                        }
                    }


                    Text(
                        text = "Lead intention",
                        fontSize = 12.sp,
                        fontWeight = FontWeight(500),
                        color = Color(0xFF000000),
                        letterSpacing = 0.5.sp
                    )

                    OutlinedTextField(
                        value = "",
                        onValueChange = {

                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black,
                            disabledTextColor = Color(0xFF616161),
                            disabledBorderColor = Color(0xFFEEEEEE),
                            cursorColor = Color.Black,
                            focusedBorderColor = Color.Black,
                            unfocusedBorderColor = Color(0xFF616161),
                            unfocusedPlaceholderColor = Color(0xFF616161),
                            focusedPlaceholderColor = Color(0xFF616161),
                            disabledPlaceholderColor = Color(0xFF616161),
                            focusedContainerColor = Color(0xFFF5F5F5),
                            unfocusedContainerColor = Color(0xFFF5F5F5),
                            disabledContainerColor = Color(0xFFF5F5F5)
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 4.dp),
                        placeholder = {
                            Text(text = "Select")
                        },
                        shape = RoundedCornerShape(12.dp),
                        enabled = false,
                        trailingIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_arrow_down),
                                contentDescription = ""
                            )
                        },
                        readOnly = true,
                        singleLine = true
                    )


                    Text(
                        text = "AD Source",
                        fontSize = 12.sp,
                        fontWeight = FontWeight(500),
                        color = Color(0xFF000000),
                        letterSpacing = 0.5.sp,
                        modifier = Modifier.padding(top = 12.dp)
                    )

                    OutlinedTextField(
                        value = "",
                        onValueChange = {

                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black,
                            disabledTextColor = Color(0xFF616161),
                            disabledBorderColor = Color(0xFFEEEEEE),
                            cursorColor = Color.Black,
                            focusedBorderColor = Color.Black,
                            unfocusedBorderColor = Color(0xFF616161),
                            unfocusedPlaceholderColor = Color(0xFF616161),
                            focusedPlaceholderColor = Color(0xFF616161),
                            disabledPlaceholderColor = Color(0xFF616161),
                            focusedContainerColor = Color(0xFFF5F5F5),
                            unfocusedContainerColor = Color(0xFFF5F5F5),
                            disabledContainerColor = Color(0xFFF5F5F5)
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 4.dp),
                        placeholder = {
                            Text(text = "Select")
                        },
                        shape = RoundedCornerShape(12.dp),
                        enabled = false,
                        trailingIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_arrow_down),
                                contentDescription = ""
                            )
                        },
                        readOnly = true,
                        singleLine = true
                    )

                    Text(
                        text = "Country",
                        fontSize = 12.sp,
                        fontWeight = FontWeight(500),
                        color = Color(0xFF000000),
                        letterSpacing = 0.5.sp,
                        modifier = Modifier.padding(top = 12.dp)
                    )

                    OutlinedTextField(
                        value = "",
                        onValueChange = {

                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black,
                            disabledTextColor = Color(0xFF616161),
                            disabledBorderColor = Color(0xFFEEEEEE),
                            cursorColor = Color.Black,
                            focusedBorderColor = Color.Black,
                            unfocusedBorderColor = Color(0xFF616161),
                            unfocusedPlaceholderColor = Color(0xFF616161),
                            focusedPlaceholderColor = Color(0xFF616161),
                            disabledPlaceholderColor = Color(0xFF616161),
                            focusedContainerColor = Color(0xFFF5F5F5),
                            unfocusedContainerColor = Color(0xFFF5F5F5),
                            disabledContainerColor = Color(0xFFF5F5F5)
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 4.dp),
                        placeholder = {
                            Text(text = "Select")
                        },
                        shape = RoundedCornerShape(12.dp),
                        enabled = false,
                        trailingIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_arrow_down),
                                contentDescription = ""
                            )
                        },
                        readOnly = true,
                        singleLine = true
                    )

                    Text(
                        text = "City",
                        fontSize = 12.sp,
                        fontWeight = FontWeight(500),
                        color = Color(0xFF000000),
                        letterSpacing = 0.5.sp,
                        modifier = Modifier.padding(top = 12.dp)
                    )

                    OutlinedTextField(
                        value = "",
                        onValueChange = {

                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black,
                            disabledTextColor = Color(0xFF616161),
                            disabledBorderColor = Color(0xFFEEEEEE),
                            cursorColor = Color.Black,
                            focusedBorderColor = Color.Black,
                            unfocusedBorderColor = Color(0xFF616161),
                            unfocusedPlaceholderColor = Color(0xFF616161),
                            focusedPlaceholderColor = Color(0xFF616161),
                            disabledPlaceholderColor = Color(0xFF616161),
                            focusedContainerColor = Color(0xFFF5F5F5),
                            unfocusedContainerColor = Color(0xFFF5F5F5),
                            disabledContainerColor = Color(0xFFF5F5F5)
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 4.dp),
                        placeholder = {
                            Text(text = "Select")
                        },
                        shape = RoundedCornerShape(12.dp),
                        enabled = false,
                        trailingIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_arrow_down),
                                contentDescription = ""
                            )
                        },
                        readOnly = true,
                        singleLine = true
                    )

                    Text(
                        text = "Language",
                        fontSize = 12.sp,
                        fontWeight = FontWeight(500),
                        color = Color(0xFF000000),
                        letterSpacing = 0.5.sp,
                        modifier = Modifier.padding(top = 12.dp)
                    )

                    OutlinedTextField(
                        value = "",
                        onValueChange = {

                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black,
                            disabledTextColor = Color(0xFF616161),
                            disabledBorderColor = Color(0xFFEEEEEE),
                            cursorColor = Color.Black,
                            focusedBorderColor = Color.Black,
                            unfocusedBorderColor = Color(0xFF616161),
                            unfocusedPlaceholderColor = Color(0xFF616161),
                            focusedPlaceholderColor = Color(0xFF616161),
                            disabledPlaceholderColor = Color(0xFF616161),
                            focusedContainerColor = Color(0xFFF5F5F5),
                            unfocusedContainerColor = Color(0xFFF5F5F5),
                            disabledContainerColor = Color(0xFFF5F5F5)
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 4.dp),
                        placeholder = {
                            Text(text = "Select")
                        },
                        shape = RoundedCornerShape(12.dp),
                        enabled = false,
                        trailingIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_arrow_down),
                                contentDescription = ""
                            )
                        },
                        readOnly = true,
                        singleLine = true
                    )

                    Text(
                        text = "Phone number",
                        fontSize = 12.sp,
                        fontWeight = FontWeight(500),
                        color = Color(0xFF000000),
                        letterSpacing = 0.5.sp,
                        modifier = Modifier.padding(top = 12.dp)
                    )

                    OutlinedTextField(
                        value = "",
                        onValueChange = {

                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black,
                            disabledTextColor = Color(0xFF616161),
                            disabledBorderColor = Color(0xFFEEEEEE),
                            cursorColor = Color.Black,
                            focusedBorderColor = Color.Black,
                            unfocusedBorderColor = Color(0xFF616161),
                            unfocusedPlaceholderColor = Color(0xFF616161),
                            focusedPlaceholderColor = Color(0xFF616161),
                            disabledPlaceholderColor = Color(0xFF616161),
                            focusedContainerColor = Color(0xFFF5F5F5),
                            unfocusedContainerColor = Color(0xFFF5F5F5),
                            disabledContainerColor = Color(0xFFF5F5F5)
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 4.dp),
                        placeholder = {
                            Text(text = "Select")
                        },
                        shape = RoundedCornerShape(12.dp),
                        enabled = false,
                        readOnly = true,
                        singleLine = true
                    )

                    Text(
                        text = "Email",
                        fontSize = 12.sp,
                        fontWeight = FontWeight(500),
                        color = Color(0xFF000000),
                        letterSpacing = 0.5.sp,
                        modifier = Modifier.padding(top = 12.dp)
                    )

                    OutlinedTextField(
                        value = "",
                        onValueChange = {

                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black,
                            disabledTextColor = Color(0xFF616161),
                            disabledBorderColor = Color(0xFFEEEEEE),
                            cursorColor = Color.Black,
                            focusedBorderColor = Color.Black,
                            unfocusedBorderColor = Color(0xFF616161),
                            unfocusedPlaceholderColor = Color(0xFF616161),
                            focusedPlaceholderColor = Color(0xFF616161),
                            disabledPlaceholderColor = Color(0xFF616161),
                            focusedContainerColor = Color(0xFFF5F5F5),
                            unfocusedContainerColor = Color(0xFFF5F5F5),
                            disabledContainerColor = Color(0xFFF5F5F5)
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 4.dp),
                        placeholder = {
                            Text(text = "Select")
                        },
                        shape = RoundedCornerShape(12.dp),
                        enabled = false,
                        readOnly = true,
                        singleLine = true
                    )
                }
            }
        }
    }
}

//Composable function to create dashed progress bar
@Preview
@Composable
fun DashedProgressIndicator(
    modifier: Modifier = Modifier,
    progress: Int = 0,
    totalNumberOfBars: Int = 10,
    progressColor: Color = Color(255, 183, 77),
    trackColor: Color = Color(
        245,
        245,
        245
    ),
    thickness: Float = 20f
) {
    Canvas(modifier = modifier) {

        val barSpace = 2.dp.toPx()
        val barSize = (size.width - (barSpace * (totalNumberOfBars - 1))) / totalNumberOfBars

        for (i in 1..totalNumberOfBars) {
            drawRoundRect(
                color =
                if (i <= progress) progressColor else trackColor,
                cornerRadius = CornerRadius(4.dp.toPx(), 4.dp.toPx()),
                size = Size(width = barSize, height = thickness),
                topLeft = Offset((i - 1) * (barSpace + barSize), 0f)
            )

        }
    }
}


//Composable function to draw Linear ProgressIndicator with rounded corners
@Preview
@Composable
fun LinearRoundedProgressIndicator(
    modifier: Modifier = Modifier,
    progress: Float = 0.1f,
    progressColor: Color = Color.Black,
    trackColor: Color = Color(0xFFEEEEEE),
    thickness: Float = 10f,
    cornerRadius: Dp = 10.dp
) {
    Canvas(modifier = modifier) {

        drawRoundRect(
            color = trackColor,
            cornerRadius = CornerRadius(cornerRadius.toPx(), cornerRadius.toPx()),
            size = Size(width = size.width, height = thickness),
            topLeft = Offset.Zero
        )

        drawRoundRect(
            color = progressColor,
            cornerRadius = CornerRadius(cornerRadius.toPx(), cornerRadius.toPx()),
            size = Size(width = size.width * progress, height = thickness),
            topLeft = Offset.Zero
        )


    }
}