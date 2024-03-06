package platform_contracts

expect class DateUtilsCustom {
   public fun getCurrentDate():String
    fun getDate(milliseconds: Long): String

}