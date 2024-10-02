package com.example.e2logypracticaltest

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.e2logypracticaltest.databinding.FragmentDetailBinding
import com.squareup.picasso.Picasso

class DetailFragment : Fragment() {


    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!


    @SuppressLint("SetTextI18n")
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        val store = arguments?.getSerializable("store") as? Store
        store?.let {
            binding.detailTitle.text = it.title
            binding.detailCategory.text = "Category : ${it.cateName}"
            binding.detailStartDate.text = "Start Date : ${it.startDate}"
            binding.detailEndDate.text = "End Date : ${it.endDate}"
            binding.detailDescription.text = "Description : ${it.description}"
            Log.d(TAG, "Image : ${it.image}")

            Glide.with(this)
                    .load(it.image)
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_foreground)
                    .into(binding.detailImage)

            (activity as? MainActivity)?.setToolbarTitle(store.title)
            val toolbar = requireActivity().findViewById<Toolbar>(R.id.toolbar)
            (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)
            (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
