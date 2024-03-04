package core.work_manager

/**
 * used to encapsulate operation,so that multiple operation can be passed as list,like the command pattern Command
 */
fun interface Work{
    suspend fun doWork():Result<String>
}