package com.mayankkussh.autoplay

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mayankkussh.autoplay.databinding.ActivityMainBinding
import com.mayankkussh.autoplay.recyclerview.MyListAdapter

class MainActivity : AppCompatActivity() {
  private lateinit var binding: ActivityMainBinding
  private lateinit var listAdapter: MyListAdapter
  private lateinit var viewModel: MainViewModel

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    setContentView(binding.root)
    setUpRecyclerView()
    viewModel.listLiveData.observe(this, Observer {
      listAdapter.setItems(it)
      binding.myListRv.postDelayed({
        findVisiblePositions(binding.myListRv)
      }, 1000)
    })
    viewModel.onViewCreated()
  }

  private fun setUpRecyclerView() {
    listAdapter = MyListAdapter()
    binding.myListRv.adapter = listAdapter
    binding.myListRv.layoutManager = LinearLayoutManager(this)


    binding.myListRv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
      override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        when (newState) {
          RecyclerView.SCROLL_STATE_IDLE -> {
            findVisiblePositions(recyclerView)
          }
          else -> {
            viewModel.onRecyclerViewStartScrolling()
            viewModel.setVisiblePositionsInRecyclerView(emptyList())
          }
        }
      }
    })
  }

  private fun findVisiblePositions(recyclerView: RecyclerView) {
    (recyclerView.layoutManager is LinearLayoutManager).let { isLinearLayoutManager ->
      if (isLinearLayoutManager) {
        (recyclerView.layoutManager as LinearLayoutManager).run {
          val firstPosition = findFirstCompletelyVisibleItemPosition()
          val lastVisiblePosition = findLastCompletelyVisibleItemPosition()
          val visiblePositions = mutableListOf<Int>()
          for (i in (firstPosition..lastVisiblePosition)) {
            visiblePositions.add(i)
          }
          Log.d("AUTOPLAY", "visiblePositions $firstPosition to $lastVisiblePosition")
          viewModel.setVisiblePositionsInRecyclerView(visiblePositions)
        }
      }
    }
  }
}