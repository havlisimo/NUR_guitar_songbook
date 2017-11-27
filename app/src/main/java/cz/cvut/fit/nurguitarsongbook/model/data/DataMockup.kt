package cz.cvut.fit.nurguitarsongbook.model.data

import cz.cvut.fit.nurguitarsongbook.R
import cz.cvut.fit.nurguitarsongbook.model.entity.Chord
import cz.cvut.fit.nurguitarsongbook.model.entity.Song

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
    val onlineSongs: MutableList<Song> by lazy { val list = ArrayList<Song>()
       SongsMockup.songs
    }
    val songbooks: MutableList<Chord> by lazy { ArrayList<Chord>() }

}