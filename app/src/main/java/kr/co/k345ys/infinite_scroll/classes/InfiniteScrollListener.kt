package kr.co.k345ys.infinite_scroll.classes

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class InfiniteScrollListener (
    val func: () -> Unit,
    val layoutManager: LinearLayoutManager) : RecyclerView.OnScrollListener() {

        // 이전 데이터 개수
        private var mPreviousTotal = 0
        private var mLoading = true

        private var mVisibleThreshold = 2
        private var mFirstVisibleItem = 0
        private var mVisibleItemCount = 0
        private var mTotalItemCount = 0

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            if (dy > 0) {

                mVisibleItemCount = recyclerView.childCount
                mTotalItemCount = layoutManager.itemCount
                mFirstVisibleItem = layoutManager.findFirstVisibleItemPosition()

                if (mLoading) {
                    // 새로운 데이터 로딩이 끝나면
                    if (mTotalItemCount > mPreviousTotal) {
                        mLoading = false
                        mPreviousTotal = mTotalItemCount
                    }
                }

                // 끝에 도달 했을 때
                // 첫 번째로 표시되는 아이템의 인덱스와 표시되어 있는 아이템의 개수의 합과 총 아이템의 개수를 배교
                if (!mLoading && (mTotalItemCount - mVisibleItemCount) <= (mFirstVisibleItem + mVisibleThreshold)) {

                    func() // 매개변수로 넘겨받은 람다식 함수 호출
                    mLoading = true
                }
            }
        }

}