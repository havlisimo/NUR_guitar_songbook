package cz.cvut.fit.nurguitarsongbook.model.entity

import cz.cvut.fit.nurguitarsongbook.model.data.DataMockup

/**
 * Created by tomas on 01.11.2017.
 */
class Song(
    val id: Int,
    var name: String,
    var artist: String,
    var comment: String,
    var text: String,
    var chords: List<Pair<Int, String>>
) {
    val songbooks: List<Songbook> by lazy { DataMockup.songbooks.filter { it.songIds.contains(id) } }

}