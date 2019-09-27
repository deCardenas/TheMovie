package com.aprendiendo.themovie.views.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aprendiendo.themovie.BuildConfig
import com.aprendiendo.themovie.R
import com.aprendiendo.themovie.data.Movie
import com.aprendiendo.themovie.listeners.AdapterListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.adapter_item.view.*

class MovieAdapter(listener: AdapterListener) : BaseAdapter<Movie>(listener) {
    override fun getItemViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.adapter_item, parent, false)
        return MovieViewHolder(view)
    }

    override fun addLoadingFooter() {
        isLoadingAdded = true
        addItem(Movie())
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(getItemViewType(position)){
            ITEM->(holder as MovieViewHolder).bind(itemList[position], listener)
        }
    }

    class MovieViewHolder(view : View) : RecyclerView.ViewHolder(view){
        fun bind(movie: Movie, listener : AdapterListener){
            itemView.cardTitle.text = movie.title!!
            itemView.cardOverview.text = movie.overview!!
            if (movie.posterBit == null){
                if (movie.posterPath == null)
                    itemView.cardPath.setImageResource(R.drawable.ic_launcher_foreground)
                else
                    Picasso.with(itemView.context)
                            .load(BuildConfig.BASE_IMAGE.plus(movie.posterPath!!))
                            .placeholder(R.drawable.ic_launcher_background)
                            .fit().into(itemView.cardPath)
            } else
                itemView.cardPath.setImageBitmap(movie.posterBit)
            itemView.setOnClickListener {
                listener.onClick(movie.id!!)
            }
        }
    }
}