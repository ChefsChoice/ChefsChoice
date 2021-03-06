package com.example.myapplication

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import coil.load
import com.example.myapplication.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private val viewModel: PopularViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentHomeBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.popularRecyclerView.adapter = PopularListAdapter()

        binding.button2.setOnClickListener {
             viewModel.trivia.observe(viewLifecycleOwner){
                 binding.foodTrivia.text = it.text
                 Log.i("Neariah", "Button clicked.")
             }
        }

        return binding.root
    }
}



// Inflate the layout for this fragment
// return inflater.inflate(R.layout.fragment_home, container, false)
/*
 viewModel.random.observe(viewLifecycleOwner) {
                binding.recipeOfTheDay.load(
                    it?.image?.toUri()?.buildUpon()?.scheme("https")?.build()
                )
                viewModel.getRandomRecipe()

 */
