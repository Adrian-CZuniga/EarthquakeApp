package com.aprendo.earthquake_2.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.aprendo.earthquake_2.Earthquake
import com.aprendo.earthquake_2.api.ApiResponseStatus
import com.aprendo.earthquake_2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val adapter = EqAdapter(this)
        binding.eqList.adapter = adapter
        binding.eqList.layoutManager = LinearLayoutManager(this)
        val viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        viewModel.eqList.observe(this) { eqList ->
            adapter.submitList(eqList)
            handleEmptyView(eqList, binding)

        }

        viewModel.status.observe(this) { apiResponseStatus ->
            if (apiResponseStatus == ApiResponseStatus.LOADING) {
                binding.loadingWheel.visibility = View.VISIBLE
            } else if (apiResponseStatus == ApiResponseStatus.DONE || apiResponseStatus == ApiResponseStatus.ERROR) {
                binding.loadingWheel.visibility = View.GONE
            }

        }

        adapter.onItemClickListener = {
            Toast.makeText(this, "Terromoto de ${it.place}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun handleEmptyView(eqList: MutableList<Earthquake>, binding: ActivityMainBinding,) {
        if (eqList.isEmpty()) {
            binding.txtEmptyEQ.visibility = View.VISIBLE
        } else {
            binding.txtEmptyEQ.visibility = View.GONE
        }
    }
}