package com.jpgsolution.youcongresspersonmvvm.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.jpgsolution.youcongresspersonmvvm.R
import com.jpgsolution.youcongresspersonmvvm.databinding.ActivityMainBinding
import com.jpgsolution.youcongresspersonmvvm.model.data.Congressperson
import com.jpgsolution.youcongresspersonmvvm.util.CheckNetWorkStatus
import com.jpgsolution.youcongresspersonmvvm.view.adapter.Adapter
import com.jpgsolution.youcongresspersonmvvm.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity(), Adapter.SelectedCongressperson{

    private lateinit var binding: ActivityMainBinding
    private val dataList: MutableList<Congressperson> = mutableListOf()
    private val viewModel: HomeViewModel by viewModels()
    private val checkNetWorkStatus = CheckNetWorkStatus
    private lateinit var adapter: Adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        checkNetWorkStatus.getNetwork(applicationContext)
        setUpToolbar()
        setDataInRecyclerView()
        actionRefresh()
        viewModelObserverDataChange()
    }

    override fun onStart() {
        super.onStart()
        checkNetWorkStatus.netWorkStatus.observe({lifecycle}){
            if(it) viewModel.getCongressPersons()
            else Snackbar.make(binding.root, getString(R.string.not_connection),Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(R.string.exit)) {}
                .show()
        }
    }

    override fun onStop() {
        super.onStop()
        checkNetWorkStatus.unRegisterCallBack()
    }

    private fun setUpToolbar(){
        binding.appBarLayout.toolbar.title = getString(R.string.home_toolbar_title)
        setSupportActionBar(binding.appBarLayout.toolbar)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun viewModelObserverDataChange(){
        viewModel.congressperson.observe({lifecycle}){
            dataList.clear()
            if (!it.isNullOrEmpty()) {
                dataList.addAll(it)
                adapter.notifyDataSetChanged()
            }else Snackbar.make(binding.root, getString(R.string.sever_error),Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(R.string.exit)) {}
                .show()
        }
    }

    private fun setDataInRecyclerView(){
        adapter = Adapter(dataList,this)
        binding.recycler.setHasFixedSize(true)
        binding.recycler.adapter = adapter
    }

    private fun actionRefresh(){
        binding.refresh.setOnRefreshListener {
            viewModel.getCongressPersons()
            Handler(Looper.getMainLooper()).postDelayed({
                binding.refresh.isRefreshing = false
            },5000)
        }
    }

    override fun selected(id: String) {
        val intent = Intent(this, DetailActivity::class.java).apply {
            putExtra("congresspersonId", id)
        }
        startActivity(intent)
    }
}