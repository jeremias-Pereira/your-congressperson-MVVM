package com.jpgsolution.youcongresspersonmvvm.view


import android.os.Bundle
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.jpgsolution.youcongresspersonmvvm.R
import com.jpgsolution.youcongresspersonmvvm.databinding.ActivityDetailBinding
import com.jpgsolution.youcongresspersonmvvm.viewmodel.DetailViewModel
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity(){

    private lateinit var binding: ActivityDetailBinding
    private val viewModel: DetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpToolbar()
        viewModelDataChange()
        actionShowDetails()
    }

    override fun onStart() {
        super.onStart()
        val id = intent.getStringExtra("congresspersonId")
        id?.let {
            viewModel.getCongressperson(it)
        }
    }


    private fun setUpToolbar(){
        setSupportActionBar(binding.appBarLayout.toolbar)
        binding.appBarLayout.toolbar.title = getString(R.string.toolbar_title)
        binding.appBarLayout.toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        binding.appBarLayout.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun viewModelDataChange(){
        viewModel.congressperson.observe({lifecycle}){
            Picasso.get().load(it.dados.ultimoStatus.urlFoto).into(binding.photo)
            binding.congressPerson = it
        }
    }

    private fun actionShowDetails(){
        binding.showDetails.setOnClickListener {
            TransitionManager.beginDelayedTransition(binding.details,AutoTransition())
            binding.details.visibility = if ( binding.details.visibility == View.GONE ) View.VISIBLE else View.GONE

        }
    }

}