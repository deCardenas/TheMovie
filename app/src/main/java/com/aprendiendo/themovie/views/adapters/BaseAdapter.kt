package com.aprendiendo.themovie.views.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aprendiendo.themovie.R
import com.aprendiendo.themovie.listeners.AdapterListener

abstract class BaseAdapter<T>(val listener: AdapterListener) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    companion object {
        const val ITEM = 1
        const val LOADING = 2
    }
    var itemList : ArrayList<T> = ArrayList()
    var isLoadingAdded  = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var viewHolder : RecyclerView.ViewHolder? = null
        val inflater = LayoutInflater.from(parent.context)
        when(viewType){
            ITEM-> viewHolder = getItemViewHolder(parent)
            LOADING->{
                val view = inflater.inflate(R.layout.adapter_progress, parent, false)
                viewHolder = ProgressViewHolder(view)
            }
        }
        return viewHolder!!
    }

    override fun getItemCount(): Int = itemList.size

    override fun getItemViewType(position: Int): Int =
            if (position == itemCount-1 && isLoadingAdded) LOADING else ITEM

    abstract fun getItemViewHolder(parent: ViewGroup): RecyclerView.ViewHolder

    fun addItem(item : T){
        itemList.add(item)
        notifyItemInserted(itemCount-1)
    }

    fun addAll(items: ArrayList<T>){
        for (item in items) addItem(item)
    }

    private fun remove( position : Int){
        itemList.removeAt(position)
        notifyItemRemoved(position)
    }

    fun clear(){
        isLoadingAdded = false
        while (itemCount>0) remove(0)
    }

    abstract fun addLoadingFooter()

    fun removeLoadingFooter(){
        isLoadingAdded= false
        remove(itemCount-1)
    }

    class ProgressViewHolder(view: View) : RecyclerView.ViewHolder(view)
}