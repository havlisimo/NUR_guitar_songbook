package cz.cvut.fit.nurguitarsongbook.main.options

import android.Manifest
import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.graphics.Path
import android.os.Bundle
import android.os.Environment
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
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
import android.text.Editable
import android.text.TextWatcher
import com.flask.colorpicker.ColorPickerView
import cz.cvut.fit.nurguitarsongbook.model.data.DataMockup
import cz.cvut.fit.nurguitarsongbook.model.data.OptionsMockup
import cz.cvut.fit.nurguitarsongbook.model.entity.Songbook
import cz.cvut.fit.nurguitarsongbook.model.entity.SongbookColor
import kotlinx.android.synthetic.main.dialog_songbook.*
import kotlinx.android.synthetic.main.dialog_songbook.view.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.design.longSnackbar
import org.jetbrains.anko.noButton
import org.jetbrains.anko.yesButton
import java.io.File


/**
 * Created by vasek on 11/28/2017.
 */
class OptionsFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val v = inflater!!.inflate(R.layout.fragment_options, container, false)
        v.item_backup.setOnClickListener{_-> checkpermissions({createBackup()})}
        v.item_restore.setOnClickListener{_-> checkpermissions({loadBackup()})}
        v.import_song.setOnClickListener{_-> checkpermissions({importSong()})}
        v.import_songbook.setOnClickListener{_-> checkpermissions({importSongbook()}) }
        return v
    }

    fun createBackup() {
        val builder = getDialogBuilder(activity)
        builder.setPositiveButton(R.string.songbook_create,  null)
        builder.setTitle(R.string.songbook_title)
        val diag = builder.create()
        diag.show()
        diag.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener({v -> run {
            if (diag.wrapper.error != null) {
                toast(activity.getString(R.string.songbook_errors));
            } else {
                val name = diag.name.text.toString() + ".json"
                val file = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), name)
                if (file.exists()) {
                    alert(R.string.options_backup_exists) {
                        yesButton {
                            file.delete()
                            createFile(name)
                        }
                        noButton {  }
                    }.show()
                }
                else {
                    createFile(name)
                }
                diag.dismiss()
            }

        }})
    }

    fun createFile(name: String) {
        val res = OptionsMockup.createBackup(name)
        if (res) toast(R.string.options_backup_success)
        else toast(R.string.options_backup_errors)
    }

    fun loadBackup() {
        val dialog = prepDialog()
        dialog.setOnFileSelectedListener { file, path -> run {
            val res = OptionsMockup.loadBackup(path)
            if (!res) toast(R.string.options_file_review)
            else toast(R.string.options_file_import_success)
        } }
    }

    fun importSong() {
        val dialog = prepDialog()
        dialog.setOnFileSelectedListener { file, path -> run {
            val res = OptionsMockup.importSong(path)
            if (!res) toast(R.string.options_file_review)
            else toast(R.string.options_file_import_success)
        } }
        dialog.show()
    }

    fun importSongbook() {
        val dialog = prepDialog()
        dialog.setOnFileSelectedListener { file, path -> run {
            val res = OptionsMockup.importSongbook(path)
            if (!res) toast(R.string.options_file_review)
            else toast(R.string.options_file_import_success)
        } }
        dialog.show()
    }

    val MY_PERMISSIONS_REQUEST = 1

    var permissionsDo : () -> Unit = {}

    fun checkpermissions(passed: () -> Unit) {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissionsDo = passed
            ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE), MY_PERMISSIONS_REQUEST)
        }
        else {
            createArbitraryFiles()
            passed()
        }

    }

    fun createArbitraryFiles() {
        val file = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "song.json")
        if (!file.exists()) file.createNewFile()
        val file2 = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "songbook.json")
        if (!file2.exists()) file2.createNewFile()
        val file3 = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "backup1.json")
        if (!file3.exists()) file3.createNewFile()

    }

    fun prepDialog() : FileListerDialog {
        val dialog = FileListerDialog.createFileListerDialog(activity);
        dialog.setFileFilter(FileListerDialog.FILE_FILTER.ALL_FILES)
        dialog.setDefaultDir(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS))
        return dialog
    }

    private fun getDialogBuilder(context: Context) : AlertDialog.Builder {
        val builder = AlertDialog.Builder(context)
        val view = activity.layoutInflater.inflate(R.layout.dialog_backup, null);
        view.wrapper.error = context.getString(R.string.options_file_name)
        view.name.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s.toString().isEmpty()) view.wrapper.error = context.getString(R.string.options_file_name)
                else view.wrapper.error = null
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int)  {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
        builder.setView(view)
        builder.setNegativeButton(R.string.songbook_cancel, {dialog, _ -> dialog.dismiss() })
        return builder
    }

    fun permissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (grantResults!!.size  == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            permissionsDo()
        }
        else toast("permissions not granted")
    }


}