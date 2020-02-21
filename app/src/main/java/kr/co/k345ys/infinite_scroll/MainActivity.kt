package kr.co.k345ys.infinite_scroll

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kr.co.k345ys.infinite_scroll.Fragments.ListFragment
import kr.co.k345ys.infinite_scroll.Fragments.SettingFragment

class MainActivity : AppCompatActivity(), SettingFragment.OnSettingFragmentListener, ListFragment.OnFragmentInteractionListener{

    private var mItemCount: Int = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragSetting, SettingFragment())
            .commit()
    }

    override fun onFragmentInteraction(uri: Uri)
    {

    }

    override fun onSettingFragmentInteraction(itemCount : String?)
    {
        var strItemCount = itemCount?: "10"
        mItemCount = strItemCount.toInt()

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragList, ListFragment())
            .addToBackStack(null)   // 백스택에 넣기
            .commit()
    }

    public fun getItemCount() : Int = mItemCount
}
