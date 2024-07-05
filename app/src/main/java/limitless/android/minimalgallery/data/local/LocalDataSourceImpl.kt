package limitless.android.minimalgallery.data.local

import android.content.ContentResolver
import android.content.ContentUris
import android.os.Build
import android.provider.MediaStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import limitless.android.minimalgallery.data.model.Photo

class LocalDataSourceImpl : LocalDataSource {

    override suspend fun getPhotos(contentResolver: ContentResolver): Flow<MutableList<Photo>> {
        val collection = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL)
        }else{
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        }

        val projection = arrayOf(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.DATE_TAKEN
        )

        val sortOrder = "${MediaStore.Images.Media.DATE_TAKEN} DESC"

        val list = mutableListOf<Photo>()

        contentResolver.query(
            collection,
            projection,
            null,
            null,
            sortOrder
        )?.use {cursor ->
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
            val nameColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)
            val dateTakenColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_TAKEN)

            while (cursor.moveToNext()){
                val id = cursor.getLong(idColumn)
                val name = cursor.getString(nameColumn)
                val dateTaken = cursor.getLong(dateTakenColumn)
                val contentUri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id)

                list.add(Photo(
                    id, name, dateTaken, contentUri
                ))
            }
        }
        return flowOf(list);
    }

    override suspend fun getPhoto(id: Int) {

    }

}