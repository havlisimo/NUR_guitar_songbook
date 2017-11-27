package cz.cvut.fit.nurguitarsongbook.model.entity

/**
 * Created by vasek on 11/25/2017.
 */
class SongbookColor(var a: Int, var r: Int, var g: Int, var b: Int) {

    constructor(argb: Int) : this(argb shr 24 and 0xff, argb shr 16 and 0xff, argb shr 8 and 0xff, argb and 0xff)

    fun toArgb(): Int {
        return a shl 24 or (r and 0xff shl 16) or (g and 0xff shl 8) or (b and 0xff)
    }
}