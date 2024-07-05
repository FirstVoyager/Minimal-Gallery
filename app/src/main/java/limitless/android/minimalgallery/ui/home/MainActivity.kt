package limitless.android.minimalgallery.ui.home

import androidx.lifecycle.lifecycleScope
import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import limitless.android.minimalgallery.R
import limitless.android.minimalgallery.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: HomeAdapter
    private val viewModel: MainViewModel by viewModels()

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                // Permission is granted, proceed with accessing photos
                getGalleryPhotos()
            } else {
                // Permission denied, handle the failure scenario
                // For example, show a message to the user
            }
        }

    private fun checkAndRequestPermission() {
        when {
            ContextCompat.checkSelfPermission(
                this, Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED -> {
                // Permission is already granted, proceed with accessing photos
                getGalleryPhotos()
            }

            shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE) -> {
                // Provide an additional rationale to the user if the permission was not granted
                // and the user would benefit from additional context for the use of the permission.
                // Display a dialog or a snackbar explaining why the app needs this permission
            }

            else -> {
                // Directly ask for the permission
                requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        adapter = HomeAdapter()
        binding.rv.adapter = adapter
        binding.rv.layoutManager = GridLayoutManager(this, 4, RecyclerView.VERTICAL, false)

        checkAndRequestPermission()

        getGalleryPhotos()

        viewModel.photos.observe(this) { list ->
            adapter.submitList(list)
        }
    }

    fun getGalleryPhotos() {
        viewModel.getPhotos(contentResolver)

    }

}