package zuper.dev.android.dashboard.jobs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import zuper.dev.android.dashboard.R
import zuper.dev.android.dashboard.data.model.JobApiModel
import zuper.dev.android.dashboard.data.model.JobStatus
import zuper.dev.android.dashboard.ui.theme.bold16
import zuper.dev.android.dashboard.ui.theme.regular14
import zuper.dev.android.dashboard.ui.theme.regular16


@Preview
@Composable
private fun ListJobItemPreview() {
    Surface {
        ListJobItem(
            modifier = Modifier.fillMaxWidth(),
            item = JobApiModel(1,"","","",JobStatus.Completed)
        )
    }
}


/**
 * Jobs List item
 *
 * @param modifier
 * @param item
 */
@Composable
fun ListJobItem(
    modifier: Modifier = Modifier,
    item:JobApiModel
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                text = stringResource(id = R.string.job_number,item.jobNumber),
                style = MaterialTheme.typography.regular16
            )
            Text(
                text = item.title,
                style = MaterialTheme.typography.bold16
            )
            Text(
                text = item.getSubText(),
                style = MaterialTheme.typography.regular14
            )
        }
    }
}