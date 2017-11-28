package cz.cvut.fit.nurguitarsongbook.main.options

import android.content.DialogInterface
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import cz.cvut.fit.nurguitarsongbook.R
import cz.cvut.fit.nurguitarsongbook.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_options.view.*
import org.jetbrains.anko.toast
import yogesh.firzen.filelister.FileListerDialog
import android.system.Os.mkdir
import java.io.File


/**
 * Created by vasek on 11/28/2017.
 */
class OptionsFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val v = inflater!!.inflate(R.layout.fragment_options, container, false)
        v.item_backup.setOnClickListener{_-> createBackup()}
        v.item_restore.setOnClickListener{_-> loadBackup()}
        v.import_song.setOnClickListener{_-> importSong()}
        v.import_songbook.setOnClickListener{_-> importSongbook()}
        return v
    }

    fun createBackup() {
        toast("create backup")
//        FileNameU
        try {
            val newFolder = File(Environment.getExternalStorageDirectory(), "TestFolder")
            if (!newFolder.exists()) {
                val crt = newFolder.mkdir()
                toast("new folder exists ${crt}")
            }
            try {
                val file = File(newFolder, "MyTest" + ".txt")
                file.createNewFile()
            } catch (ex: Exception) {
                println("ex: " + ex)
            }

        } catch (e: Exception) {
            println("e: " + e)
        }

    }

    fun loadBackup() {
        toast("load backup")
        val dialog = FileListerDialog.createFileListerDialog(activity);
//        toast(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).absolutePath.toString())
        dialog.setFileFilter(FileListerDialog.FILE_FILTER.ALL_FILES)
        dialog.setDefaultDir(Environment.getExternalStorageDirectory())
        dialog.setOnFileSelectedListener { file, path -> run {
            toast(path.toString())
        } }
        dialog.show()
    }

    fun importSong() {
        toast("import song")
    }

    fun importSongbook() {
        toast("import songbook")
    }
}