package com.example.e2logypracticaltest

import android.graphics.Bitmap
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class ListFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: StoreAdapter
    private var storeList: List<Store> = emptyList()
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val toolbar = requireActivity().findViewById<Toolbar>(R.id.toolbar)
        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as? MainActivity)?.setToolbarTitle("Offer List")

        val view = inflater.inflate(R.layout.fragment_list, container, false)
        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)
        fetchStoreData()
        return view
    }

    private fun fetchStoreData() {
        val retrofit = Retrofit.Builder()
                .baseUrl("https://dl.dropboxusercontent.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val apiService = retrofit.create(ApiService::class.java)

        apiService.getStoreInfo().enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                response.body()?.let { apiResponse ->
                    storeList = apiResponse.result
                    adapter = StoreAdapter(requireContext(), storeList,
                            object : StoreAdapter.OnItemClickListener {
                                override fun onItemClick(store: Store) {
                                    saveImage(store)
                                    val bundle = Bundle().apply {
                                        putSerializable("store", store)
                                    }
                                    val detailFragment = DetailFragment()
                                    detailFragment.arguments = bundle

                                    requireActivity().supportFragmentManager.beginTransaction()
                                            .replace(R.id.fragment_container, detailFragment)
                                            .addToBackStack(null)
                                            .commit()
                                }
                            })
                    recyclerView.adapter = adapter
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                Toast.makeText(context, "Failed to fetch data", Toast.LENGTH_SHORT).show()
            }
        })
    }


    private fun saveImage(store: Store) {
        Glide.with(requireContext())
                .asBitmap()
                .load(store.image)
                .into(object : SimpleTarget<Bitmap>() {
                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                        val fileName = "storeImage.jpg"
                        val directory = File(
                                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                                "E2logyTest"
                        )
                        if (!directory.exists()) {
                            directory.mkdirs()
                        }
                        val file = File(directory, fileName)

                        try {
                            val fileOutputStream = FileOutputStream(file)
                            resource.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream)
                            fileOutputStream.close()

                            Log.d("ImageSave", "Image saved at: ${file.absolutePath}")
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }
                    }
                })
    }
}
