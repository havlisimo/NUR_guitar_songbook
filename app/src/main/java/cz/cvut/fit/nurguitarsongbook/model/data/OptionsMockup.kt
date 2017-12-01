package cz.cvut.fit.nurguitarsongbook.model.data

import android.os.Environment
import com.google.gson.Gson
import cz.cvut.fit.nurguitarsongbook.model.entity.Song
import cz.cvut.fit.nurguitarsongbook.model.entity.Songbook
import cz.cvut.fit.nurguitarsongbook.model.entity.SongbookColor
import cz.cvut.fit.nurguitarsongbook.model.entity.json.SongsImport
import java.io.File
import java.io.FileReader

/**
 * Created by vasek on 11/28/2017.
 */
object OptionsMockup {

    var songTextSize = 14

    fun createBackup(path: String): Boolean {
        val file = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), path)
        return file.createNewFile()
    }

    fun loadBackup(path: String): Boolean {
        if (!(path.endsWith(".json"))) return false

        val id = DataMockup.getSongId()
        DataMockup.songs.add(Song(id, "backupsong ${id}", "backuped artist ${id}", "backuped comment", "", ArrayList()))
        return true
    }

    fun importSong(path: String): Boolean {
        if (!(path.endsWith(".json") || path.endsWith(".txt"))) return false

        val id = DataMockup.getSongId()

        val gson = Gson()
        val songs = gson.fromJson(FileReader(path), SongsImport::class.java)
        songs.songs?.forEach {
            DataMockup.songs.add(
                Song(id,
                    it.name,
                    it.artist,
                    "",
                    it.text,
                    it.chords.map { chord -> Pair(chord.index!!, chord.name!!)
                    }))
        }

        return true
    }

    fun importSongbook(path: String): Boolean {
        if (!path.endsWith(".json")) return false

        val songbookId = DataMockup.getSongbookId()
        val sb = Songbook(songbookId, "imported songbook ${songbookId}", SongbookColor(255, 244, 66, 149), ArrayList())

        var id = DataMockup.getSongId()
        DataMockup.songs.add(Song(id, "imported ${id}", "imported artist ${id}", "no comment", "", ArrayList()))
        sb.songIds.add(id)
        id = DataMockup.getSongId()
        DataMockup.songs.add(Song(id, "imported ${id}", "imported artist ${id}", "no comment", "", ArrayList()))
        sb.songIds.add(id)
        id = DataMockup.getSongId()
        DataMockup.songs.add(Song(id, "imported ${id}", "imported artist ${id}", "no comment", "", ArrayList()))
        sb.songIds.add(id)

        DataMockup.songbooks.add(sb)

        return true

    }

}