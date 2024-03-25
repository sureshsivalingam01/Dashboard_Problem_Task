package zuper.dev.android.dashboard.data.model

import androidx.compose.ui.graphics.Color
import zuper.dev.android.dashboard.ui.theme.GoldenTainoi
import zuper.dev.android.dashboard.ui.theme.Malibu
import zuper.dev.android.dashboard.ui.theme.MonteCarlo
import zuper.dev.android.dashboard.ui.theme.Sunglo

data class InvoiceApiModel(
    val invoiceNumber: Int,
    val customerName: String,
    val total: Int,
    val status: InvoiceStatus
)

enum class InvoiceStatus(
    val value: String,
    val color: Color,
    ) {
    Draft("Draft", GoldenTainoi),
    Pending("Pending", Malibu),
    Paid("Paid", MonteCarlo),
    BadDebt("Bad Debt", Sunglo);
}
