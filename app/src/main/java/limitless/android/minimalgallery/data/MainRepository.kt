package limitless.android.minimalgallery.data

import android.content.ContentResolver
import kotlinx.coroutines.flow.Flow
import limitless.android.minimalgallery.data.local.LocalDataSource
import limitless.android.minimalgallery.data.model.Photo
import javax.inject.Inject

class MainRepository @Inject constructor(private val localDataSource: LocalDataSource) {

    suspend fun getPhotos(contentResolver: ContentResolver): Flow<MutableList<Photo>> {
        return localDataSource.getPhotos(contentResolver)
    }

}