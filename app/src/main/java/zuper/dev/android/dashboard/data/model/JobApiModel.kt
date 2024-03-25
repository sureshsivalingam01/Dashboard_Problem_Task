package zuper.dev.android.dashboard.data.model

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import zuper.dev.android.dashboard.R
import zuper.dev.android.dashboard.ui.theme.ColdPurple
import zuper.dev.android.dashboard.ui.theme.GoldenTainoi
import zuper.dev.android.dashboard.ui.theme.Malibu
import zuper.dev.android.dashboard.ui.theme.MonteCarlo
import zuper.dev.android.dashboard.ui.theme.Sunglo
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


/**
 * A simple API model representing a Job
 *
 * Start and end date time is in ISO 8601 format - Date and time are stored in UTC timezone and
 * expected to be shown on the UI in the local timezone
 */
data class JobApiModel(
    val jobNumber: Int,
    val title: String,
    val startTime: String,
    val endTime: String,
    val status: JobStatus
) {




    @Composable
    fun getSubText(): String {

        val isoFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        val timeWithAmPm = DateTimeFormatter.ofPattern("hh:mm a")
        val date = DateTimeFormatter.ofPattern("dd/MM/yyyy, hh:mm a")

        val today = LocalDate.now()
        val start = LocalDateTime.parse(startTime, isoFormatter)
        val end = LocalDateTime.parse(endTime, isoFormatter)

        val isToday = today.isEqual(start.toLocalDate()) && today.isEqual(end.toLocalDate())

        //return "${start.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL))} - ${start.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL))}"

        return if (isToday) {
            stringResource(
                R.string.today_start_and_end,
                start.format(timeWithAmPm),
                end.format(timeWithAmPm)
            )
        } else {
            stringResource(
                R.string.start_arrow_end_date,
                start.format(date),
                end.format(date)
            )
        }
    }

}

enum class JobStatus(val value: String, val color: Color) {
    YetToStart("Yet to start", ColdPurple),
    InProgress("In-Progress", Malibu),
    Canceled("Cancelled", GoldenTainoi),
    Completed("Completed", MonteCarlo),
    InComplete("In-Complete", Sunglo);
}
