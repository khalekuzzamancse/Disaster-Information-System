It will be used to upload the file and media.
It will expose the public apis for uploading,so that the implementation can be changed later
the `MultiplatformMedia` will define the media type for multiplatform.
The `MediaUploader` Will define the apis to upload the media with some states,if needed.
It is just abstraction so that with client has loose couping and also has implementation can be changed easily.
