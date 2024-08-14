package com.example.foodapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodapp.databinding.PopularItemBinding

class PopularAdapter(
    private val items: List<String>,
    private val prices: List<String>,
    private val images: List<Int>
) : RecyclerView.Adapter<PopularAdapter.PopularViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularViewHolder {
        // this is where you inflate the layout (giving a look our rows)

        // Inflate the layout using the PopularItemBinding class, which is generated from the XML layout file.
        // parent viewGroup mean that the ConstraintLayout or LinearLayout, etc for the xml
        // 1. LayoutInflater.from(parent.context): Get a LayoutInflater instance from the context of the parent ViewGroup.
        // 2. parent: The parent ViewGroup that this new view will be attached to.
        // 3. false: Indicate that the inflated view should not be immediately attached to the parent ViewGroup.
        // Return a new instance of PopularViewHolder, passing the inflated binding as an argument.

        return PopularViewHolder(PopularItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: PopularViewHolder, position: Int) {
        // assign values to the views we created in the recycler_view_row layout file
        // based on the position of the recycler view

        val item = items[position]
        val image = images[position]
        val price = prices[position]
        holder.bind(item, image, price)
    }

    override fun getItemCount(): Int {
        // the recycler view just wants to know the number of items you want displayed
        return items.size
    }

    class PopularViewHolder(private val binding: PopularItemBinding)
        : RecyclerView.ViewHolder(binding.root) {


        // grabbing the views from recycle view layout file
        // kinda like in the onCreate method

        fun bind(item: String, image: Int, price: String) {
            binding.tvFoodName.text = item
            binding.tvMoney.text = price
            binding.imageFood.setImageResource(image)
        }

    }

}