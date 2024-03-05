package com.khalekuzzaman.just.cse.datacollect.ui_layer.common_ui.command_pattern

interface Command {

    fun execute()
    fun undo()
    fun redo()
    //redo=execute most of the times
}


