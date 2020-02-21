package kr.co.k345ys.infinite_scroll.Fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.fragment_setting.*
import kr.co.k345ys.infinite_scroll.R
import kr.co.k345ys.infinite_scroll.utils.inflate

class SettingFragment : Fragment() {

    // Fragment에서 발생되는 이벤트를 메인Activity에 알릴 필요가 있을 때 사용
    private var mFragmentListener: OnSettingFragmentListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return container?.inflate(R.layout.fragment_setting)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // Spinner
        val countAdapter = ArrayAdapter.createFromResource(
            context!!,
            R.array.item_count,
            android.R.layout.simple_spinner_item
        )
        countAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinCount.adapter = countAdapter
        spinCount.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                // MainActivity에 아이템이 변경되었음을 알리자
                mFragmentListener?.onSettingFragmentInteraction(parent?.getItemAtPosition(position)?.toString())
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnSettingFragmentListener) {
            mFragmentListener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mFragmentListener = null
    }

    interface OnSettingFragmentListener {
        // TODO: Update argument type and name
        fun onSettingFragmentInteraction(itemCount: String?)
    }
}
