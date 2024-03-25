package zuper.dev.android.dashboard.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import zuper.dev.android.dashboard.R
import zuper.dev.android.dashboard.ui.theme.bold20
import zuper.dev.android.dashboard.ui.theme.regular14
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle


@Preview
@Composable
private fun ProfileGreetingsPreview() {
    ProfileGreetings(
        modifier = Modifier
            .fillMaxWidth()
    )
}


@Composable
fun ProfileGreetings(
    modifier: Modifier = Modifier,
    name:String = "Android"
) {

    val date = remember {
        LocalDate.now()
            .format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL))// format EEEE, MMMM dd yyyy
    }

    OutlinedCard(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = stringResource(
                        id = R.string.hello_name, name
                    ),
                    maxLines = 1,
                    style = MaterialTheme.typography.bold20
                )
                Text(
                    text = date,
                    style = MaterialTheme.typography.regular14
                )
            }
            Image(
                painter = painterResource(R.drawable.ic_profile),
                contentDescription = stringResource(R.string.profile_picture),
                modifier = Modifier.size(50.dp).clip(RoundedCornerShape(6.dp)),
            )
        }
    }

}