package com.aprendo.earthquake_2.main

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aprendo.earthquake_2.Earthquake
import com.aprendo.earthquake_2.R
import com.aprendo.earthquake_2.databinding.EqListItemBinding

private val TAG = EqAdapter::class.java.simpleName
class EqAdapter(private val context: Context) : ListAdapter<Earthquake, EqAdapter.EqViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<Earthquake>() {
        override fun areItemsTheSame(oldItem: Earthquake, newItem: Earthquake): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Earthquake, newItem: Earthquake): Boolean {
            return oldItem == newItem
        }
    }
    lateinit var onItemClickListener: (Earthquake) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EqAdapter.EqViewHolder{
        val binding = EqListItemBinding.inflate(LayoutInflater.from(parent.context))
        return EqViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EqAdapter.EqViewHolder, position: Int) {
        val earthquake = getItem(position)
        holder.bind(earthquake)
    }

    inner class EqViewHolder(private val binding: EqListItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(earthquake: Earthquake){
            binding.eqMagnitude.text = context.getString(R.string.magnitude_format, earthquake.magnitude)
            binding.eqPlace.text = earthquake.place
            binding.root.setOnClickListener{
                if(::onItemClickListener.isInitialized){
                    onItemClickListener(earthquake)
                } else {
                    Log.e(TAG, "OnItemClickListener not initialized")
                }
                binding.executePendingBindings()
            }
        }
    }
}