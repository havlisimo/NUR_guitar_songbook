package cz.cvut.fit.nurguitarsongbook.model.data

import cz.cvut.fit.nurguitarsongbook.R
import cz.cvut.fit.nurguitarsongbook.model.entity.Chord
import cz.cvut.fit.nurguitarsongbook.model.entity.Song
import cz.cvut.fit.nurguitarsongbook.model.entity.Songbook
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
    val onlineSongs: MutableList<Song> by lazy {
       SongsMockup.songs
    }
    val songbooks: MutableList<Songbook> by lazy {
        val list = ArrayList<Songbook>()
        list.add(Songbook(getSongbookId(), "aaa", SongbookColor(255, 255, 0, 0), arrayListOf(3, 4, 5)))
        list.add(Songbook(getSongbookId(), "bbb", SongbookColor(255, 0, 255, 0), arrayListOf(8, 9)))
        list.add(Songbook(getSongbookId(), "ccc empty", SongbookColor(255, 0, 0, 255), ArrayList()))
        list
    }

    private var songbookId = -1

    fun getSongbookId() : Int {
        songbookId++
        return songbookId
    }

    private var songId = -1

    fun getSongId() : Int {
        songId++
        return songId
    }

    fun getSongsForSongbookId(sbId: Int): List<Song> {
        val songbook = getSongbookById(sbId)
        return songs.filter { song ->  songbook.songIds.contains(song.id)}
    }

    fun getSongbookById(sbId: Int): Songbook {
        return songbooks.first { songbook -> songbook.id == sbId }
    }

    fun getSongsNotInSongbook(sbId: Int): List<Song> {
        val songbook = getSongbookById(sbId)
        return songs.filter { song ->  !songbook.songIds.contains(song.id)}
    }

    fun getSongById(songId: Int): Song {
        return songs.first { song -> song.id == songId }
    }

}