package com.example.firstapp.ui.playlists

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firstapp.R
import com.example.firstapp.data.models.PlaylistItems
import com.example.firstapp.data.network.Status
import com.example.firstapp.showToast
import com.example.firstapp.ui.playlists.adapter.MainAdapter
import kotlinx.android.synthetic.main.activity_main.*

class PlaylistsActivity : AppCompatActivity() {


    //1. Создать DetailPlaylistActivity
    //2. Сделать Запрос на получение списка данных
    //3. Сделать ui по дизайну
    private lateinit var adapter: MainAdapter
    private lateinit var viewModel: PlaylistViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(PlaylistViewModel::class.java)
        setupAdapter()

        fetchPlaylists()
    }

    private fun setupAdapter() {
        adapter = MainAdapter(this::onItemClick)
        recycler_view.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recycler_view.adapter = adapter
    }

    private fun fetchPlaylists() {
        viewModel.fetchPlaylists().observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> it.data?.items?.let { result -> adapter.addItems(result) }
                Status.ERROR -> showToast(it.message.toString())
            }
        })
    }

    private fun onItemClick(item: PlaylistItems) {
        showToast(item.etag.toString())
    }

}