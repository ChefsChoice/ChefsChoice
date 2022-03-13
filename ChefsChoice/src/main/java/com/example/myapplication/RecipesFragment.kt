package com.example.myapplication

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context.CAMERA_SERVICE
import android.content.Intent
import android.graphics.Bitmap
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.SearchView

import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myapplication.database.RecipesApplication
import com.example.myapplication.databinding.FragmentRecipesBinding
import com.example.myapplication.databinding.RecipeListBinding
import com.example.myapplication.viewmodel.RecipesViewModelFactory
import com.google.android.material.snackbar.Snackbar

import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil.setContentView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.FragmentRecipesBinding
import com.example.myapplication.databinding.RecipeListBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage

import kotlinx.coroutines.launch

import com.google.mlkit.vision.label.ImageLabeling
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions


class RecipesFragment : Fragment() {
    val adapter = RecipesListAdapter()
    private val viewModel: RecipesViewModel by activityViewModels() {
        RecipesViewModelFactory((activity?.application as RecipesApplication).database.favoriteDao())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentRecipesBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        //Get user input from searchview & query the api to get the results
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                Log.i("Neariah", query)
                viewModel.getChefPhotos(query)
                return true
            }

            override fun onQueryTextChange(s: String?): Boolean {
                return false
            }
        })
        binding.recyclerView.adapter = adapter
        adapter.onSaveClickListener {
            lifecycleScope.launch {
                viewModel.insert(it)
                Snackbar.make(requireView(), "SAVED", Snackbar.LENGTH_SHORT).show()
            }
        }
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }


}

/*  Would code related to the camera go here instead? https://developers.google.com/ml-kit/vision/barcode-scanning/android
    val options = BarcodeScannerOptions.Builder()
        .setBarcodeFormats(
            Barcode.FORMAT_UPC_A,
            Barcode.FORMAT_UPC_E)
        .build()

         */
//Tameika Testing MLK changes

// Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_recipes, container, false)}






