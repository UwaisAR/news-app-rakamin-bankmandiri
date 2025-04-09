import android.os.Build
import androidx.annotation.RequiresApi
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.Instant

@RequiresApi(Build.VERSION_CODES.O)
fun formatPublishedDate(publishedAt: String?): String {
    return try {
        val instant = Instant.parse(publishedAt)
        val localDateTime = instant.atZone(ZoneId.of("UTC")).toLocalDateTime()

        val formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy, HH:mm")
        localDateTime.format(formatter)
    } catch (e: Exception) {
        "Unknown Time"
    }
}
