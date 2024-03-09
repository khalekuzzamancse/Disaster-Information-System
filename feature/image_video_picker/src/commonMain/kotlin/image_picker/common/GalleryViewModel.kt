package image_picker.common
import image_picker.command_pattern.Command
import image_picker.command_pattern.GalleryCommands
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class GalleryViewModel {

    private val _imageGalleryState = MutableStateFlow(emptyList<GalleryMediaGeneric>())
    val galleryState = _imageGalleryState.asStateFlow()
    val anySelected: Flow<Boolean> = _imageGalleryState.map { images ->
        images.any { it.isSelected }
    }
    private var lastCommand: Command? = null



    fun addImages(identities: List<KMPFile>) {
        val command = GalleryCommands.Add(
            existingImages = _imageGalleryState.value,
            newImages =identities.map { GalleryMediaGeneric(identity = it) }
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

    private fun updateState(images: List<GalleryMediaGeneric>) {
        _imageGalleryState.update { images.distinct() }
    }

    fun flipSelection(currentSelected: KMPFile) {
        val command = GalleryCommands.FlipSelection(
            existingImages = _imageGalleryState.value,
            identity = currentSelected
        )
        command.execute()
        updateState(command.state)
    }


}
