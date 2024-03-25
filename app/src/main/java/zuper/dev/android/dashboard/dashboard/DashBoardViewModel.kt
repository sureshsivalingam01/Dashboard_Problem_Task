package zuper.dev.android.dashboard.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import zuper.dev.android.dashboard.data.DataRepository
import javax.inject.Inject


@HiltViewModel
class DashBoardViewModel @Inject constructor(
    private val dataRepository: DataRepository
) : ViewModel() {


    /**
     * jobs from data Repository observed as flow and stateIn
     */
    val jobs = dataRepository.observeJobs()
        .map { list ->
            list.sortedBy { it.status.ordinal }
        }
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    val invoices = dataRepository.observeInvoices()
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())





}