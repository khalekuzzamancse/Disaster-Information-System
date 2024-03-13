package report_form.platform_contracts

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * * Defining DateUtil as platform specif because Java code not run in IOS
 * so the commonMain should not have any have dependency
 */
actual class DateUtilsCustom {
   actual fun getCurrentDate(): String {
        val milliseconds = System.currentTimeMillis()
        return getDate(milliseconds)
    }
  actual  fun getDate(milliseconds: Long): String {
        val date = Date(milliseconds)
        val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        return sdf.format(date)
    }


}