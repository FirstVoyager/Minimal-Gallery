package limitless.android.minimalgallery.ui.home

import android.content.ContentResolver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import limitless.android.minimalgallery.data.MainRepository
import limitless.android.minimalgallery.data.model.Photo
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val mainRepository: MainRepository) : ViewModel() {

    private val _photos = MutableLiveData<List<Photo>>(emptyList())
    val photos : LiveData<List<Photo>> = _photos

    fun getPhotos(contentResolver: ContentResolver){
        viewModelScope.launch {
            mainRepository.getPhotos(contentResolver).collectLatest {data ->
                _photos.value = data
            }
        }
    }

}