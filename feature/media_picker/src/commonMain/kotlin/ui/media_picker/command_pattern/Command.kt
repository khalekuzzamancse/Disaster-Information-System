package ui.media_picker.command_pattern
@PublishedApi
internal interface Command {

    fun execute()
    fun undo()
    fun redo()
    //redo=execute most of the times
}


