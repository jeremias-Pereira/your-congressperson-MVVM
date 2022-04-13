package com.jpgsolution.youcongresspersonmvvm.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import androidx.recyclerview.widget.RecyclerView
import com.jpgsolution.youcongresspersonmvvm.R
import com.jpgsolution.youcongresspersonmvvm.databinding.ItemListCongresspersonBinding
import com.jpgsolution.youcongresspersonmvvm.model.data.Congressperson
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Adapter (
    private val listCongressperson: List<Congressperson>,
    private val selectedCongressperson: SelectedCongressperson,
): RecyclerView.Adapter<Adapter.MyViewHolder>() {

    interface SelectedCongressperson{
        fun selected(id: String)
    }

    class MyViewHolder (view: View): RecyclerView.ViewHolder(view){
        private val binding = ItemListCongresspersonBinding.bind(view)
        private val nome = binding.name
        private val siglaPartido = binding.partido
        private val siglaUf = binding.ufState
        private val imageView = binding.imgPhoto

        fun bind(congressperson: Congressperson){
            nome.text = congressperson.nome
            siglaPartido.text = congressperson.siglaPartido
            siglaUf.text = congressperson.siglaUf
            roundImage(congressperson.urlFoto)

        }

        private fun roundImage(urlPhoto: String) {
            CoroutineScope(Main).launch {
                val bitmap = withContext(IO) {
                    val temp = RoundedBitmapDrawableFactory.create(binding.root.resources, Picasso.get().load(urlPhoto).get())
                    temp.isCircular = true
                    temp.bitmap
                }
                imageView.setImageBitmap(bitmap)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_list_congressperson,parent,false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(listCongressperson[position])
        holder.itemView.setOnClickListener {
            selectedCongressperson.selected(listCongressperson[position].id)
        }
    }

    override fun getItemCount(): Int {
        return listCongressperson.size
    }
}
