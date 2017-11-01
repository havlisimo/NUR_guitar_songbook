package cz.cvut.fit.nurguitarsongbook.model.data

import cz.cvut.fit.nurguitarsongbook.R
import cz.cvut.fit.nurguitarsongbook.model.entity.Chord

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
    val songs: MutableList<Chord> by lazy { ArrayList<Chord>() }
    val onlineSongs: MutableList<Chord> by lazy { ArrayList<Chord>() }
    val songbooks: MutableList<Chord> by lazy { ArrayList<Chord>() }

}