
import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Instant
import java.time.Duration
import java.time.ZoneId

@RequiresApi(Build.VERSION_CODES.O)
fun getTimeAgo(publishedAt: String): String {
    return try {
        val publishedTime = Instant.parse(publishedAt)
        val now = Instant.now()
        val duration = Duration.between(publishedTime, now)

        when {
            duration.toMinutes() < 1 -> "Just now"
            duration.toMinutes() < 60 -> "${duration.toMinutes()} minutes ago"
            duration.toHours() < 24 -> "${duration.toHours()} hours ago"
            duration.toDays() < 7 -> "${duration.toDays()} days ago"
            else -> publishedTime.atZone(ZoneId.systemDefault()).toLocalDate().toString()
        }
    } catch (e: Exception) {
        "Unknown time"
    }
}
