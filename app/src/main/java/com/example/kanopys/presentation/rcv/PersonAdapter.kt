package com.example.kanopys.presentation.rcv

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.kanopys.R
import com.example.kanopys.data.network.PersonsDTO
import com.example.kanopys.databinding.PersonItemBinding
import com.example.kanopys.domain.entity.Movie

class PersonAdapter :
    ListAdapter<PersonsDTO, PersonAdapter.PersonViewHolder>(PersonDiffUtilCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.person_item,
            parent,
            false
        )
        return PersonViewHolder(view)
    }

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        val item = getItem(position)
        holder.setData(item)
    }

    class PersonViewHolder(item: View) : ViewHolder(item) {
        private val binding = PersonItemBinding.bind(item)
        fun setData(personsDTO: PersonsDTO) = with(binding) {
            Glide.with(itemView)
                .load(personsDTO.photo)
                .override(500, 500)
                .into(imPerson)
            tvPersonName.text = personsDTO.name
            tvPersonDescr.text = personsDTO.description
            Log.d("TESTRCV", personsDTO.name)
        }
    }
}