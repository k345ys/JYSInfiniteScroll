package kr.co.k345ys.infinite_scroll.interfaces

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

interface ItemAdapterInterface {

    fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder

    fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewTypeInterface)
}