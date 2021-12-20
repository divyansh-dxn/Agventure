package com.dxn.agventure.features.seller.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.dxn.agventure.R
import com.dxn.data.models.CatalogueProduct

class ProductListAdapter(val onRemoveClicked: (productId: String) -> Unit) :
    RecyclerView.Adapter<ProductListAdapter.ProductViewHolder>() {

    var products: List<CatalogueProduct> = listOf()

    class ProductViewHolder(itemView: View) : ViewHolder(itemView) {
        val productImage: ImageView = itemView.findViewById(R.id.product_image)
        val productName: TextView = itemView.findViewById(R.id.product_name)
        val productPrice: TextView = itemView.findViewById(R.id.product_price)
        val productQuantity: TextView = itemView.findViewById(R.id.product_quantity)
        val removeButton: Button = itemView.findViewById(R.id.remove)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.product_card, parent, false),
        )
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.productName.text = products[position].name
        holder.productPrice.text = products[position].price.toString()
        holder.productQuantity.text = products[position].quantity.toString()
        holder.removeButton.setOnClickListener { onRemoveClicked(products[position].productId) }
        holder.productImage.load(products[position].photoUrl)
    }

    override fun getItemCount(): Int {
        return products.size
    }

}