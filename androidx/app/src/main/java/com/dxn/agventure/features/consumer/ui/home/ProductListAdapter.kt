package com.dxn.agventure.features.consumer.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.dxn.agventure.R
import com.dxn.data.models.CatalogueProduct

class ProductListAdapter( private val onBuyClicked: (String) -> Unit, private val onAddToCartClicked: (String) -> Unit) :
    RecyclerView.Adapter<ProductListAdapter.ProductViewHolder>() {

    var products: List<CatalogueProduct> = listOf()

    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productImage: ImageView = itemView.findViewById(R.id.product_image)
        val productName: TextView = itemView.findViewById(R.id.product_name)
        val productPrice: TextView = itemView.findViewById(R.id.product_price)
        val productQuantity: TextView = itemView.findViewById(R.id.product_quantity)
        val buyButton: Button = itemView.findViewById(R.id.buy)
        val addToCartButton: Button = itemView.findViewById(R.id.add_to_cart)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.consumer_product_card, parent, false),
        )
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.productName.text = products[position].name
        holder.productPrice.text = products[position].price.toString()
        holder.productQuantity.text = products[position].quantity.toString()
        holder.addToCartButton.setOnClickListener {
            onAddToCartClicked(products[position].productId)
        }
        holder.buyButton.setOnClickListener {
            onBuyClicked(products[position].productId)
        }
        holder.productImage.load(products[position].photoUrl)
    }

    override fun getItemCount(): Int {
        return products.size
    }

}