package com.khalekuzzaman.just.cse.datacollect.image_picker
import android.net.Uri
import com.khalekuzzaman.just.cse.datacollect.common_ui.command_pattern.Command
import com.khalekuzzaman.just.cse.datacollect.common_ui.command_pattern.GalleryCommands
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class GalleryViewModel {
    private val _imageGalleryState = MutableStateFlow(emptyList<GalleryMedia>())
    val imageGalleryState = _imageGalleryState.asStateFlow()
    val anySelected: Flow<Boolean> = _imageGalleryState.map { images ->
        images.any { it.isSelected }
    }
    private var lastCommand: Command? = null


    fun addImages(uris: List<Uri>) {
        val command = GalleryCommands.Add(
            existingImages = _imageGalleryState.value,
            newImages =uris.map { GalleryMedia(uri = it) }
        )
        command.execute()
        lastCommand = command
        updateState(command.state)
    }

    fun remove() {
        val command = GalleryCommands.Remove(_imageGalleryState.value)
        command.execute()
        lastCommand = command
        updateState(command.state)
    }

    fun undo() {
        lastCommand?.let { command ->
            command.undo()
            when (command) {
                is GalleryCommands.Add -> {
                    updateState(command.state)
                }

                is GalleryCommands.Remove -> {
                    updateState(command.state)
                }
            }

        }

    }

    fun redo() {
        lastCommand?.let { command ->
            command.redo()
            when (command) {
                is GalleryCommands.Add -> {
                    updateState(command.state)
                }

                is GalleryCommands.Remove -> {
                    updateState(command.state)
                }
            }

        }

    }

    private fun updateState(images: List<GalleryMedia>) {
        _imageGalleryState.update { images.distinct() }
    }

    fun flipSelection(currentSelected: Uri) {
        val command = GalleryCommands.FlipSelection(
            existingImages = _imageGalleryState.value,
            uri = currentSelected
        )
        command.execute()
        updateState(command.state)
    }


}
