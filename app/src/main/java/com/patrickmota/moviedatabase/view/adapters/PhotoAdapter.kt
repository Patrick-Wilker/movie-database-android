package com.patrickmota.moviedatabase.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.patrickmota.moviedatabase.R
import com.patrickmota.moviedatabase.model.ImageResponse
import com.patrickmota.moviedatabase.model.Backdrop
import kotlinx.android.synthetic.main.item_photo.view.*

class PhotoAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    lateinit var items: ImageResponse

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PhotoViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_photo, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is PhotoViewHolder -> {
                holder.bind(items.backdrops[position])
            }
        }
    }

    override fun getItemCount(): Int = items.backdrops.size

    class PhotoViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind (backdrop: Backdrop){

            val bannerUrl = "https://image.tmdb.org/t/p/w500/" + backdrop.filePath

            val requestOptions = RequestOptions()
                .placeholder(R.color.gray)
                .error(R.color.gray)

            Glide.with(itemView.context)
                .applyDefaultRequestOptions(requestOptions)
                .load(bannerUrl)
                .fitCenter()
                .into(itemView.poster)
        }

    }
}