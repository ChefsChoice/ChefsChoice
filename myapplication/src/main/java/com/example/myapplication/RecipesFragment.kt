package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.myapplication.databinding.FragmentRecipesBinding
import com.example.myapplication.databinding.RecipeListBinding

class RecipesFragment : Fragment() {

    private val viewModel: RecipesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentRecipesBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.recyclerView.adapter = RecipesListAdapter()
        return binding.root
    }
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_recipes, container, false)
    }

//}