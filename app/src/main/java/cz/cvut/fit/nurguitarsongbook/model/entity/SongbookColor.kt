package cz.cvut.fit.nurguitarsongbook.model.entity

/**
 * Created by vasek on 11/25/2017.
 */
class SongbookColor(var a: Int, var r: Int, var g: Int, var b: Int) {

    fun toArgb(): Int {
        return a and 0xff shl 24 or (r and 0xff shl 16) or (g and 0xff shl 8) or (b and 0xff)
    }
}