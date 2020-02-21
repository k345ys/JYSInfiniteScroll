package kr.co.k345ys.infinite_scroll.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.feature_rows.view.*
import kr.co.k345ys.infinite_scroll.classes.GeometryFeature
import kr.co.k345ys.infinite_scroll.interfaces.ItemAdapterInterface
import kr.co.k345ys.infinite_scroll.R
import kr.co.k345ys.infinite_scroll.interfaces.ViewTypeInterface
import kr.co.k345ys.infinite_scroll.utils.inflate

class FeatureItemAdapter : ItemAdapterInterface {

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return FeatureViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewTypeInterface) {
        holder as FeatureViewHolder
        holder.bind(item as GeometryFeature)
    }

    // 이너 클래스에서는 바깥 클래스의 프로퍼티 등을 접근 할 수 있다.
    inner class FeatureViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        parent.inflate(R.layout.feature_rows)) {

        fun bind(item: GeometryFeature) {

            val aindex: String = item?.aindex ?: ""
            val blkLot: String = item?.blkLot ?: ""
            val blkNum: String = item?.blkNum ?: ""
            val fromSt: String = item?.fromSt ?: ""
            val lotNum: String = item?.lotNum ?: ""

            itemView.txtFeatureDesc.text = "$aindex:$blkLot/$blkNum/$fromSt/$lotNum"
        }
    }

}