package image_picker.command_pattern

import image_picker.common.KMPFile
import image_picker.common.GalleryMediaGeneric


object GalleryCommands {
    /**
     * Command for add images to already existing image
     */

    class Remove(
        private val existingImages: List<GalleryMediaGeneric>
    ) : Command {
        var state = existingImages
            private set

        override fun execute() {
            val images = existingImages.filter { !it.isSelected }
            state =images.distinct().sortedBy { it.identity.id }
        }

        override fun undo() {
            state = existingImages
        }

        override fun redo() = execute()
    }

    class Add(
        private val existingImages: List<GalleryMediaGeneric>,
        private val newImages: List<GalleryMediaGeneric>,
    ) : Command {
        var state = existingImages
            private set

        override fun execute() {
            val images = newImages + existingImages
            state = images.distinct().sortedBy { it.identity.id }
        }

        override fun undo() {
            state = existingImages
        }

        override fun redo() = execute()
    }

    class FlipSelection(
        private val identity: KMPFile,
        private val existingImages: List<GalleryMediaGeneric>
    ) : Command {
        var state = existingImages
            private set

        override fun execute() {
            val images = existingImages.map { image ->
                if (image.identity == identity) {
                    val selected = image.isSelected
                    image.copy(isSelected = !selected)
                } else image
            }
            //sorting to avoid mixing new images with old
            state =images.distinct().sortedBy { it.identity.id }
        }

        override fun undo() {
            state = existingImages
        }

        override fun redo() = execute()
    }


}


