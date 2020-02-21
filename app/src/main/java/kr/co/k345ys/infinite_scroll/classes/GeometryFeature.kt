package kr.co.k345ys.infinite_scroll.classes

import kr.co.k345ys.infinite_scroll.AdapterType
import kr.co.k345ys.infinite_scroll.interfaces.ViewTypeInterface

class GeometryFeature () : ViewTypeInterface {

    override fun getViewType() = AdapterType.FEATURE

    companion object Factory {
        fun create(): GeometryFeature =
            GeometryFeature()
    }

    public var aindex : String? = null
    public var blkLot: String? = null
    public var blkNum: String? = null
    public var fromSt: String? = null
    public var lotNum: String? = null

    public fun GeometryFeature()
    {
        aindex = ""
        blkLot = ""
        blkNum = ""
        fromSt = ""
        lotNum = ""
    }
}