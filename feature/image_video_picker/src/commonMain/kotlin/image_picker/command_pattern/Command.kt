package image_picker.command_pattern

interface Command {

    fun execute()
    fun undo()
    fun redo()
    //redo=execute most of the times
}


