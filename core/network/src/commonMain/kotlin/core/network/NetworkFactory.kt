package core.network

object NetworkFactory {
    fun createPostRequester():PostRequests{
        return PostRequestsImpl()
    }
}