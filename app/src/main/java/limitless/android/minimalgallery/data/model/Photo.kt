package limitless.android.minimalgallery.data.model

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Photo(
    val id: Long,
    val name: String,
    val dateTaken: Long,
    val uri: Uri
): Parcelable
