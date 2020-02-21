package kr.co.k345ys.infinite_scroll.Fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_list.*
import kr.co.k345ys.infinite_scroll.MainActivity
import kr.co.k345ys.infinite_scroll.R
import kr.co.k345ys.infinite_scroll.utils.inflate
import kr.co.k345ys.infinite_scroll.adapters.FeatureAdapter
import kr.co.k345ys.infinite_scroll.classes.GeometryFeature
import kr.co.k345ys.infinite_scroll.classes.InfiniteScrollListener
import kr.co.k345ys.infinite_scroll.interfaces.LoadingItemInterface

class ListFragment : Fragment(), LoadingItemInterface {

    // Fragment에서 발생되는 이벤트를 메인Activity에 알릴 필요가 있을 때 사용
    private var mFragmentListener: OnFragmentInteractionListener? = null

    // 데이터베이스로부터 데이터를 읽어올때 몇개의 데이터를 읽어올 것인가
    private var mFetchCount: Int = 10
    // 데이터베이스로부터 읽어온 데이터를 저장하는 멤버 변수
    private lateinit var mFetchList: MutableList<GeometryFeature>

    // Firebase realtime database
    private lateinit var mDatabase: DatabaseReference

    private lateinit var mAdapter: FeatureAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mDatabase = FirebaseDatabase.getInstance().reference
        mFetchList = mutableListOf()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return container?.inflate(R.layout.fragment_list)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // RecyclerView의 리소스 id
        rvFeature.apply {
            setHasFixedSize(true)

            // 세로 형태의 레이아웃
            val linearLayout = LinearLayoutManager(context)
            layoutManager = linearLayout
            clearOnScrollListeners()

            // 스크롤 감지를 위한 리스너 등록
            addOnScrollListener(InfiniteScrollListener({
                requestFeature()
            }, linearLayout))
        }

        //  어댑터의 연결
        if (rvFeature.adapter == null) {
            rvFeature.adapter = FeatureAdapter(this)
        }

        // SettingFragment의 조회개수를 기준으로 한번에 읽어올 데이터의 양을 정하자
        if (activity != null && activity as MainActivity != null)
            mFetchCount = (activity as MainActivity).getItemCount()
        if (mFetchCount < 0) mFetchCount = 10

        // 처음에 읽어올때는 처음데이터 부터 시작
        mDatabase.child("features").orderByKey().startAt("0").endAt((mFetchCount-1).toString()).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                loadFeatureList(dataSnapshot)
            }
            override fun onCancelled(databaseError: DatabaseError) {
            }
        })
    }

    private fun requestFeature() {

        // 현재까지 읽은 부분의 다음부터 데이터를 읽어옴
        val startOffset = (rvFeature.adapter as FeatureAdapter).getFeatureList().count()

        // 현재까지 읽은 부분에서 조회개수만큼의 데이터를 읽어옴
        val endOffset = startOffset + (mFetchCount -1)

        mDatabase.child("features").orderByKey().startAt(startOffset.toString()).endAt(endOffset.toString()).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                loadFeatureList(dataSnapshot)
            }
            override fun onCancelled(databaseError: DatabaseError) {
            }
        })
    }

    private fun loadFeatureList(dataSnapshot: DataSnapshot) {

        // 기존에 읽어온 데이터는 이미 표시되고 있으므로
        mFetchList.clear()

        val tasks = dataSnapshot.children.iterator()
        while (tasks.hasNext()) {

            val listIndex = tasks.next()
            val arrayKey = listIndex.key

            val itemsIterator = listIndex.children.iterator()
            while (itemsIterator.hasNext()) {

                //get current task
                val currentItem = itemsIterator.next()
                if (currentItem.key.equals("properties", true)) {
                    val feature = GeometryFeature.create()

                    feature.aindex = arrayKey

                    if(currentItem.hasChild("BLKLOT"))
                        feature.blkLot = currentItem.child("BLKLOT").value as String
                    if(currentItem.hasChild("BLOCK_NUM"))
                        feature.blkNum = currentItem.child("BLOCK_NUM").value as String
                    if(currentItem.hasChild("FROM_ST"))
                        feature.fromSt = currentItem.child("FROM_ST").value as String
                    if(currentItem.hasChild("LOT_NUM"))
                        feature.lotNum = currentItem.child("LOT_NUM").value as String

                    mFetchList.add(feature)
                    break
                }
            }
        }

        // 생성된 데이터를 RecyclerView에 추가
        (rvFeature.adapter as FeatureAdapter).addMovieList(mFetchList)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            mFragmentListener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mFragmentListener = null
    }

    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(uri: Uri)
    }

    override fun onClickCallBack() {
        requestFeature()
    }
}
