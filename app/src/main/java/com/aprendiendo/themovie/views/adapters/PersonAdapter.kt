package com.aprendiendo.themovie.views.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aprendiendo.themovie.BuildConfig
import com.aprendiendo.themovie.R
import com.aprendiendo.themovie.data.Person
import com.aprendiendo.themovie.listeners.AdapterListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.adapter_person.view.*

class PersonAdapter(listener: AdapterListener) : BaseAdapter<Person>(listener) {

    override fun getItemViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.adapter_person, parent, false)
        return PersonViewHolder(view)
    }

    override fun addLoadingFooter() {
        isLoadingAdded = true
        addItem(Person())
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            ITEM ->(holder as PersonViewHolder).bind(itemList[position], listener)
        }
    }

    class PersonViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(person: Person, listener: AdapterListener) {
            itemView.cardName.text = person.name!!
            if (person.profileBit == null) {
                if (person.profilePath == null)
                    itemView.cardPath.setImageResource(R.drawable.ic_launcher_foreground)
                else
                    Picasso.with(itemView.context)
                            .load(BuildConfig.BASE_IMAGE.plus(person.profilePath!!))
                            .placeholder(R.drawable.ic_launcher_background)
                            .fit().into(itemView.cardPath)
            } else
                itemView.cardPath.setImageBitmap(person.profileBit)
            itemView.setOnClickListener {
                listener.onClick(person.id!!)
            }
        }
    }
}