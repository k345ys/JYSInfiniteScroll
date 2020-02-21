package kr.co.k345ys.infinite_scroll.adapters

import android.view.ViewGroup
import androidx.collection.SparseArrayCompat
import androidx.recyclerview.widget.RecyclerView
import kr.co.k345ys.infinite_scroll.*
import kr.co.k345ys.infinite_scroll.classes.GeometryFeature
import kr.co.k345ys.infinite_scroll.interfaces.ItemAdapterInterface
import kr.co.k345ys.infinite_scroll.interfaces.LoadingItemInterface
import kr.co.k345ys.infinite_scroll.interfaces.ViewTypeInterface

class FeatureAdapter(var clickCallback: LoadingItemInterface) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    // FEATURE 혹은 LOADING 아이템의 종류를 파악하기 위해
    private var mItemTypes: ArrayList<ViewTypeInterface>

    // 두 종류의 어댑터를 위한 배열 컬렉션
    private var delegateAdapters = SparseArrayCompat<ItemAdapterInterface>()

    // 로딩용 아이템은 미리 생성해 놓자
    private val loadingItem = object : ViewTypeInterface {
        override fun getViewType() = AdapterType.LOADING
    }

    // 생성시 초기화 되는 블록
    init {
        delegateAdapters.put(
            AdapterType.LOADING,
            LoadingItemAdapter(clickCallback)
        )
        delegateAdapters.put(
            AdapterType.FEATURE,
            FeatureItemAdapter()
        )
        mItemTypes = ArrayList()
        mItemTypes.add(loadingItem)     // 로딩용 아이템
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        delegateAdapters.get(viewType)!!.onCreateViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        delegateAdapters.get(getItemViewType(position))!!.onBindViewHolder(holder, mItemTypes[position])
    }

    override fun getItemCount(): Int = mItemTypes.size

    // 여러 종류의 보기유형을 나타내기 위해
    override fun getItemViewType(position: Int) = mItemTypes[position].getViewType()

    fun addMovieList(fList: List<GeometryFeature>) {

        // 로딩 아이템 제거
        val initPosition = mItemTypes.size - 1
        mItemTypes.removeAt(initPosition)
        notifyItemRemoved(initPosition) // 특정 Position에 데이터를 하나 제거할 때 이벤트 알림

        // 모든 목록을 추가하고 마지막은 로딩용 아이템 추가
        mItemTypes.addAll(fList)
        mItemTypes.add(loadingItem)
        notifyItemRangeChanged(initPosition, mItemTypes.size + 1)
    }

    fun getFeatureList(): List<GeometryFeature> = mItemTypes
        .filter { it.getViewType() == AdapterType.FEATURE }
        .map { it as GeometryFeature }
}