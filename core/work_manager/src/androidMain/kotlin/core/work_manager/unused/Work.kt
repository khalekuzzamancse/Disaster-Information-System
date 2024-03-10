package core.work_manager.unused

/**
 * used to encapsulate operation,t,like the command pattern Command
 */
fun interface Work{
    suspend fun doWork():Result<String>
}