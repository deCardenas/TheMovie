package com.aprendiendo.themovie.views.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aprendiendo.themovie.BuildConfig
import com.aprendiendo.themovie.R
import com.aprendiendo.themovie.data.TvShow
import com.aprendiendo.themovie.listeners.AdapterListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.adapter_item.view.*

class TvAdapter(listener: AdapterListener) : BaseAdapter<TvShow>(listener) {

    override fun getItemViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.adapter_item, parent, false)
        return TvViewHolder(view)
    }

    override fun addLoadingFooter() {
        isLoadingAdded = true
        addItem(TvShow())
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(getItemViewType(position)){
            ITEM->(holder as TvViewHolder).bind(itemList[position], listener)
        }
    }

    class TvViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(tvShow: TvShow, listener: AdapterListener) {
            itemView.cardTitle.text = tvShow.name!!
            itemView.cardOverview.text = tvShow.overview!!
            if (tvShow.posterBit == null) {
                if (tvShow.posterPath == null)
                    itemView.cardPath.setImageResource(R.drawable.ic_launcher_foreground)
                else
                    Picasso.with(itemView.context)
                            .load(BuildConfig.BASE_IMAGE.plus(tvShow.posterPath!!))
                            .placeholder(R.drawable.ic_launcher_background)
                            .fit().into(itemView.cardPath)
            } else
                itemView.cardPath.setImageBitmap(tvShow.posterBit)
            itemView.setOnClickListener {
                listener.onClick(tvShow.id!!)
            }
        }
    }
}