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
        list.add(Chord("C", R.drawable.c))
        list.add(Chord("D", R.drawable.d))
        list.add(Chord("E", R.drawable.e))
        list.add(Chord("F", R.drawable.f))
        list.add(Chord("G", R.drawable.g))
        list.add(Chord("A", R.drawable.a))
        list.add(Chord("B", R.drawable.h))
        list
    }
    val songs: MutableList<Song> by lazy {
        SongsMockup.songs
    }
    val onlineSongs: MutableList<Chord> by lazy { ArrayList<Chord>() }
    val songbooks: MutableList<Songbook> by lazy {
        val list = ArrayList<Songbook>()
        list.add(Songbook(1, "aaa", SongbookColor(255, 255, 0, 0)))
        list.add(Songbook(2, "bbb", SongbookColor(255, 0, 255, 0)))
        list.add(Songbook(3, "bbb", SongbookColor(255, 0, 0, 255)))
        list
    }

}