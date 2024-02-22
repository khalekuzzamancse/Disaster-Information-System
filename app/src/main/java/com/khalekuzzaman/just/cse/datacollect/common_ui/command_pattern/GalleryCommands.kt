package com.khalekuzzaman.just.cse.datacollect.common_ui.command_pattern

import android.net.Uri
import com.khalekuzzaman.just.cse.datacollect.image_picker.GalleryMedia


object GalleryCommands {
    /**
     * Command for add images to already existing image
     */

    class Remove(
        private val existingImages: List<GalleryMedia>
    ) : Command {
        var state = existingImages
            private set

        override fun execute() {
            val images = existingImages.filter { !it.isSelected }
            state =images.distinct().sortedBy { it.uri }
        }

        override fun undo() {
            state = existingImages
        }

        override fun redo() = execute()
    }

    class Add(
        private val existingImages: List<GalleryMedia>,
        private val newImages: List<GalleryMedia>,
    ) : Command {
        var state = existingImages
            private set

        override fun execute() {
            val images = newImages + existingImages
            state = images.distinct().sortedBy { it.uri }
        }

        override fun undo() {
            state = existingImages
        }

        override fun redo() = execute()
    }

    class FlipSelection(
        private val uri: Uri,
        private val existingImages: List<GalleryMedia>
    ) : Command {
        var state = existingImages
            private set

        override fun execute() {
            val images = existingImages.map { image ->
                if (image.uri == uri) {
                    val selected = image.isSelected
                    image.copy(isSelected = !selected)
                } else image
            }
            state =images.distinct().sortedBy { it.uri }
        }

        override fun undo() {
            state = existingImages
        }

        override fun redo() = execute()
    }


}


