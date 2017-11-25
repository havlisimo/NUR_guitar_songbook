package cz.cvut.fit.nurguitarsongbook.model.data

import cz.cvut.fit.nurguitarsongbook.R
import cz.cvut.fit.nurguitarsongbook.model.entity.Chord
import cz.cvut.fit.nurguitarsongbook.model.entity.Songbook
import cz.cvut.fit.nurguitarsongbook.model.entity.Song
import cz.cvut.fit.nurguitarsongbook.model.entity.SongbookColor

/**
 * Created by tomas on 01.11.2017.
 */
object DataMockup {

    val chords: MutableList<Chord> by lazy {
        val list = ArrayList<Chord>()
        list.add(Chord("C dur", R.drawable.ic_guitar))
        list.add(Chord("D dur", R.drawable.ic_guitar))
        list.add(Chord("E dur", R.drawable.ic_guitar))
        list.add(Chord("F dur", R.drawable.ic_guitar))
        list.add(Chord("G dur", R.drawable.ic_guitar))
        list.add(Chord("A dur", R.drawable.ic_guitar))
        list.add(Chord("H dur", R.drawable.ic_guitar))
        list
    }
    val songs: MutableList<Song> by lazy {
        val list = ArrayList<Song>()
        list.add(Song("Highway to Hell", "AC/DC", "Moje oblibena <3"))
        list.add(Song("Rap God", "Eminem", "hehehe"))
        list
    }
    val onlineSongs: MutableList<Chord> by lazy { ArrayList<Chord>() }
    val songbooks: MutableList<Songbook> by lazy {
        val list = ArrayList<Songbook>()
        list.add(Songbook(1, "aaa", SongbookColor(255, 255, 0, 0)))
        list.add(Songbook(1, "bbb", SongbookColor(255, 0, 255, 0)))
        list.add(Songbook(1, "bbb", SongbookColor(255, 0, 0, 255)))
        list
    }

}