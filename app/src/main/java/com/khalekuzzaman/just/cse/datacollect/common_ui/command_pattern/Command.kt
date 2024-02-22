package com.khalekuzzaman.just.cse.datacollect.common_ui.command_pattern

interface Command {

    fun execute()
    fun undo()
    fun redo()
    //redo=execute most of the times
}


