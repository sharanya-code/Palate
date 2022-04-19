package com.example.simpleyelp

import android.content.Context
import android.view.LayoutInflater
import android.view.RoundedCorner
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_restraunt.view.*

class RestaurantsAdapter(val context: Context, val restaurants: List<YelpRestaurants>):
    RecyclerView.Adapter<RestaurantsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_restraunt,parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val restaurant = restaurants[position]
        holder.bind(restaurant)
    }

    override fun getItemCount(): Int = restaurants.size

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(restaurant: YelpRestaurants) {
            itemView.tvNumReviws.text = restaurant.reviewCount.toString()
            itemView.tvName.text = restaurant.name
            itemView.tvDistance.text = restaurant.displayDistance()
            itemView.tvPrice.text = restaurant.price
            itemView.tvCategory.text = restaurant.categories[0].title
            itemView.tvAddress.text = restaurant.location.address
            itemView.ratingBar.rating = restaurant.rating.toFloat()
            Glide.with(context).load(restaurant.imageUrl).into(itemView.imageView)
        }

    }
}
