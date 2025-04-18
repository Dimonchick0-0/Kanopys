package com.example.kanopys.presentation.rcv

import androidx.recyclerview.widget.DiffUtil
import com.example.kanopys.data.network.PersonsDTO

class PersonDiffUtilCallback: DiffUtil.ItemCallback<PersonsDTO>() {
    override fun areItemsTheSame(oldItem: PersonsDTO, newItem: PersonsDTO): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: PersonsDTO, newItem: PersonsDTO): Boolean {
        return oldItem == newItem
    }
}