package report_form.platform_contracts

expect class DateUtilsCustom {
   fun getCurrentDate():String
    fun getDate(milliseconds: Long): String

}