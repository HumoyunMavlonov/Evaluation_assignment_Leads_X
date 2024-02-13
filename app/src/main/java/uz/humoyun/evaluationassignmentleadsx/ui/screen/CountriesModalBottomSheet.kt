package uz.humoyun.evaluationassignmentleadsx.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.EmojiSupportMatch
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import uz.humoyun.evaluationassignmentleadsx.data.response.CountryDto
import uz.humoyun.evaluationassignmentleadsx.data.response.IntentionDto
import uz.humoyun.evaluationassignmentleadsx.R
import uz.humoyun.evaluationassignmentleadsx.ui.viewmodel.createlead.CreateLeadViewModel
import uz.humoyun.evaluationassignmentleadsx.ui.viewmodel.createlead.CreateLeadViewModelImpl

@Composable
fun CountriesListModalSheet(viewModel: CreateLeadViewModel, onItemClick: (CountryDto) -> Unit) {

    val hasLoadedCountries by viewModel.hasLoadedCountries.observeAsState(initial = false)

    val countries by viewModel.countries.observeAsState(initial = listOf())

    LaunchedEffect(key1 = null, block = {
        if (!hasLoadedCountries) viewModel.getCountries()
    }
    )

    var search by remember {
        mutableStateOf("")
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {

        TextField(
            value = search,
            onValueChange = {
                search = it
                viewModel.searchCountry(it)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            placeholder = {
                Text(text = "Search")
            },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = "search icon",
                )
            },
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                disabledIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedContainerColor = Color(0xFFF5F5F5),
                unfocusedContainerColor = Color(0xFFF5F5F5),
                disabledContainerColor = Color(0xFFF5F5F5)
            ),
            singleLine = true
        )

        LazyColumn(
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth()
        ) {

            items(countries) { dto ->
                ItemCountry(dto, onItemClick = onItemClick)
            }

        }

    }

}

@Preview
@Composable
fun CountriesListModalSheetPreview() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {

        TextField(
            value = "",
            onValueChange = {

            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            placeholder = {
                Text(text = "Search")
            },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = "search icon",
                )
            },
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                disabledIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedContainerColor = Color(0xFFF5F5F5),
                unfocusedContainerColor = Color(0xFFF5F5F5),
                disabledContainerColor = Color(0xFFF5F5F5)
            ),
            singleLine = true,
        )

        LazyColumn(
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth()
        ) {

            items(20) {
                ItemCountryPreview(
                )
            }

        }

    }

}

@Composable
fun ItemCountry(
    data: CountryDto,
    onItemClick: (CountryDto) -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(Color.White)
            .clickable(interactionSource = remember {
                MutableInteractionSource()
            }, indication = rememberRipple()) {
                onItemClick(data)
            }
            .padding(end = 24.dp, top = 8.dp, bottom = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                modifier = Modifier,
                text = data.emoji,
                fontSize = 24.sp,
                style = TextStyle(
                    platformStyle = PlatformTextStyle(
                        emojiSupportMatch = EmojiSupportMatch.None
                    )/* ... */
                )
            )

            Text(
                text = data.title,
                fontSize = 16.sp,
                lineHeight = 24.sp,
                fontWeight = FontWeight(400),
                color = Color(0xFF212121),
            )
        }

        Text(
            text = "+${data.phoneCode}",
            fontSize = 14.sp,
            lineHeight = 24.sp,
            fontWeight = FontWeight(400),
            color = Color(0xFF49454F),
            textAlign = TextAlign.Right,
        )
    }

}

@Preview
@Composable
fun ItemCountryPreview() {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(Color.White)
            .padding(end = 24.dp, top = 8.dp, bottom = 8.dp)
            .clickable(interactionSource = remember {
                MutableInteractionSource()
            }, indication = rememberRipple()) {

            },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = "",
                contentDescription = "country image",
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFEEEEEE))
            )

            Text(
                text = "Andorra",
                fontSize = 16.sp,
                lineHeight = 24.sp,
                fontWeight = FontWeight(400),
                color = Color(0xFF212121),
            )
        }

        Text(
            text = "+23",
            fontSize = 14.sp,
            lineHeight = 24.sp,
            fontWeight = FontWeight(400),
            color = Color(0xFF49454F),
            textAlign = TextAlign.Right,
        )
    }
}

@Composable
fun CitiesLayout(
    id: Int, viewModel: CreateLeadViewModel = hiltViewModel<CreateLeadViewModelImpl>(),
    onItemClick: (IntentionDto) -> Unit
) {

    val cities by viewModel.cities.observeAsState(listOf())

    LaunchedEffect(key1 = null, block = {
        viewModel.getCities(id)
    })

    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            items(cities) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .clickable(interactionSource = remember {
                            MutableInteractionSource()
                        }, indication = rememberRipple()) {
                            onItemClick(it)
                        }
                        .padding(
                            horizontal = 16.dp,
                            vertical = 12.dp
                        ),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {


                    Text(
                        text = it.title,
                        fontSize = 17.sp,
                        lineHeight = 24.sp,
                        fontWeight = FontWeight(400),
                        color = Color(0xFF212121),
                    )


                }
            }
        }
    }
}

@Composable
fun LanguagesLayout(
    viewModel: CreateLeadViewModel = hiltViewModel<CreateLeadViewModelImpl>(),
    onItemClick: (IntentionDto) -> Unit
) {

    val languages by viewModel.languages.observeAsState(listOf())

    val hasLoadedLanguages by viewModel.hasLoadedLanguages.observeAsState(initial = false)

    LaunchedEffect(key1 = null, block = {
        if (!hasLoadedLanguages) viewModel.getLanguages()
    })

    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            items(languages) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .clickable(interactionSource = remember {
                            MutableInteractionSource()
                        }, indication = rememberRipple()) {
                            onItemClick(it)
                        }
                        .padding(
                            horizontal = 16.dp,
                            vertical = 12.dp
                        ),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {


                    Text(
                        text = it.title,
                        fontSize = 17.sp,
                        lineHeight = 24.sp,
                        fontWeight = FontWeight(400),
                        color = Color(0xFF212121),
                    )


                }
            }
        }
    }
}

@Composable
fun AdSourcesLayout(
    viewModel: CreateLeadViewModel = hiltViewModel<CreateLeadViewModelImpl>(),
    onItemClick: (IntentionDto) -> Unit
) {

    val languages by viewModel.adSources.observeAsState(listOf())

    val hasLoadedLanguages by viewModel.hasLoadedAdSources.observeAsState(initial = false)

    LaunchedEffect(key1 = null, block = {
        if (!hasLoadedLanguages) viewModel.getAdSources()
    })

    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            items(languages) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .clickable(interactionSource = remember {
                            MutableInteractionSource()
                        }, indication = rememberRipple()) {
                            onItemClick(it)
                        }
                        .padding(
                            horizontal = 16.dp,
                            vertical = 12.dp
                        ),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {

                    Text(
                        text = it.title,
                        fontSize = 17.sp,
                        lineHeight = 24.sp,
                        fontWeight = FontWeight(400),
                        color = Color(0xFF212121),
                    )

                }
            }
        }
    }
}