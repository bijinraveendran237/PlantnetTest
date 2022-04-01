package com.example.myapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class CustomAdapter(val context: Context) : RecyclerView.Adapter<CustomAdapter.MyViewHolder>() {

    var movieList : List<Details> = listOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_view_design,parent,false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.tvMovieName.text = movieList.get(position).bestMatch
        holder.tv_subtext.text = movieList.get(position).bestMatch
        Glide.with(context).load(movieList.get(position).query?.images?.get(0)).into(holder.image);
//        Glide.with(context).load(movieList.get(position).image)
//            .apply(RequestOptions().centerCrop())
//            .into(holder.image)
    }

    fun setMovieListItems(movieList: List<Details?>){
        this.movieList = movieList as List<Details>;
        notifyDataSetChanged()
    }

    class MyViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {

        val tvMovieName: TextView = itemView!!.findViewById(R.id.textView)
        val image: ImageView = itemView!!.findViewById(R.id.imageview)
        val tv_subtext: TextView = itemView!!.findViewById(R.id.tv_subtext)

    }
}