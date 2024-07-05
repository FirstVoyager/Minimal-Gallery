package limitless.android.minimalgallery.data.local

import android.content.ContentResolver
import kotlinx.coroutines.flow.Flow
import limitless.android.minimalgallery.data.model.Photo

interface LocalDataSource {

    suspend fun getPhotos(contentResolver: ContentResolver) : Flow<MutableList<Photo>>

    suspend fun getPhoto(id: Int)

}