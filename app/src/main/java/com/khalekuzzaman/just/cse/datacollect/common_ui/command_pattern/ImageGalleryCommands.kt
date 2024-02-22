package com.khalekuzzaman.just.cse.datacollect.common_ui.command_pattern

import android.net.Uri
import com.khalekuzzaman.just.cse.datacollect.GalleryImage
import com.khalekuzzaman.just.cse.datacollect.ImageGalleryState


object ImageGalleryCommands {
    /**
     * Command for add images to already existing image
     */

    class Remove(
        private val currentState: ImageGalleryState
    ) : Command {
        var state=currentState
            private  set
        override fun execute() {
            val images = currentState.images.filter {! it.isSelected }
            state= ImageGalleryState(images.distinct().sortedBy { it.uri })
        }

        override fun undo() {
            state= currentState
        }

        override fun redo() = execute()
    }
    class Add(
        private val images: List<GalleryImage>,
        private val currentState: ImageGalleryState =ImageGalleryState()
    ) : Command {
        var state=currentState
            private  set
        override fun execute() {
            val images = currentState.images + images
            state= ImageGalleryState(images.distinct().sortedBy { it.uri })
        }

        override fun undo() {
            state= currentState
        }

        override fun redo() = execute()
    }
    class FlipSelection(
        private val uri: Uri,
        private val currentState: ImageGalleryState =ImageGalleryState()
    ) : Command {
        var state=currentState
            private  set
        override fun execute() {
            val images = currentState.images.map { image ->
                if (image.uri == uri) {
                    val selected = image.isSelected
                    image.copy(isSelected = !selected)
                } else image
            }
            state= ImageGalleryState(images.distinct().sortedBy { it.uri })
        }
        override fun undo() {
            state= currentState
        }

        override fun redo() = execute()
    }



}


