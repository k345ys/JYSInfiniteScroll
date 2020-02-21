package kr.co.k345ys.infinite_scroll.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.feature_rows.view.*
import kr.co.k345ys.infinite_scroll.interfaces.ItemAdapterInterface
import kr.co.k345ys.infinite_scroll.R
import kr.co.k345ys.infinite_scroll.interfaces.LoadingItemInterface
import kr.co.k345ys.infinite_scroll.interfaces.ViewTypeInterface
import kr.co.k345ys.infinite_scroll.utils.inflate

class LoadingItemAdapter(var clickCallback: LoadingItemInterface) : ItemAdapterInterface {

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return LoadingViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewTypeInterface) {
        holder as LoadingViewHolder
        holder.itemView.txtFeatureDesc.text = "아이템 더 보기"
        holder.itemView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                clickCallback.onClickCallBack()
            }
        });
    }

    // 이너 클래스에서는 바깥 클래스의 프로퍼티 등을 접근 할 수 있다.
    inner class LoadingViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        parent.inflate(R.layout.feature_rows)) {
    }

}