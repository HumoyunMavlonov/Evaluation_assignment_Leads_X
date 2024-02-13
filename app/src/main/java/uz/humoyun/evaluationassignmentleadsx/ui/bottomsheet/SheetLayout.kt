package uz.humoyun.evaluationassignmentleadsx.ui.bottomsheet

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import uz.humoyun.evaluationassignmentleadsx.data.response.CountryDto
import uz.humoyun.evaluationassignmentleadsx.data.response.IntentionDto
import uz.humoyun.evaluationassignmentleadsx.ui.screen.AdSourcesLayout
import uz.humoyun.evaluationassignmentleadsx.ui.screen.CitiesLayout
import uz.humoyun.evaluationassignmentleadsx.ui.screen.CountriesListModalSheet
import uz.humoyun.evaluationassignmentleadsx.ui.screen.LanguagesLayout
import uz.humoyun.evaluationassignmentleadsx.ui.viewmodel.createlead.CreateLeadViewModel
import uz.humoyun.evaluationassignmentleadsx.ui.viewmodel.createlead.CreateLeadViewModelImpl

@Composable
fun SheetLayout(
    sheetStyle: Int,
    country: CountryDto?,
    viewModel: CreateLeadViewModel = hiltViewModel<CreateLeadViewModelImpl>(),
    onAdSourceClick: (IntentionDto) -> Unit,
    onCountryClick: (CountryDto) -> Unit,
    onCityClick: (IntentionDto) -> Unit,
    onLanguageClick: (IntentionDto) -> Unit,
) {

    when (sheetStyle) {
        SheetStyle.AD_SOURCE -> {
            AdSourcesLayout(viewModel = viewModel, onAdSourceClick)
        }

        SheetStyle.COUNTRY -> {
            CountriesListModalSheet(viewModel = viewModel, onCountryClick)
        }

        SheetStyle.CITY -> {
            country?.let { CitiesLayout(it.id, viewModel = viewModel, onCityClick) }
        }

        SheetStyle.LANGUAGE -> {
            LanguagesLayout(viewModel = viewModel, onLanguageClick)
        }
    }

}