package ui.media_picker.command_pattern

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


/**
Implementing the undo manager,since we can use coroutine do do operation so
to avoid multiple threads can do  undo and redo at a time we used synchronization.
to avoid dead lock situation avoid nested synchronization and choose a better lock key;so
that the both synchronization block get locks at a time and use the same lock object to both block
so that the both block can get the the lock at a time.
 */

@PublishedApi
internal class UndoManager {
    private val _undoAvailable = MutableStateFlow(false)
    val undoAvailable = _undoAvailable.asStateFlow()
    private val _redoAvailable = MutableStateFlow(false)
    val redoAvailable = _redoAvailable.asStateFlow()
    private val _command = MutableStateFlow<Command?>(null)
    val command=_command.asStateFlow()

    private var undoStack = emptyList<Command>()
        set(value) {
            field = value
            _undoAvailable.update { undoStack.isNotEmpty() }
        }
    private var redoStack = emptyList<Command>()
        set(value) {
            field = value
            _redoAvailable.update { redoStack.isNotEmpty() }
        }
    private val lock = Any()


    fun execute(command: Command) {
        command.execute()
        undoStack = undoStack + command
        //
        _command.update { command }
    }


    fun undo() {
        synchronized(lock) {
            undoStack.lastOrNull()?.let { command ->
                undoStack = undoStack.dropLast(1)
                command.undo()
                redoStack = redoStack + command
                //
                _command.update { command }
            }
        }

    }

    fun redo() {
        synchronized(lock) {
            redoStack.lastOrNull()?.let { command ->
                redoStack = redoStack.dropLast(1)
                command.redo()
                undoStack = undoStack + command
                //
                _command.update { command }
            }

        }

    }
}
