package dev.ferp.navigationsamples.gallery

import dev.ferp.navigationsamples.data.Picture

fun Picture.toGalleryItemModel() = GalleryItemModel(
    pictureId = id,
    pictureUrl = url
)

fun List<Picture>.toGalleryItemModelList() = map { it.toGalleryItemModel() }