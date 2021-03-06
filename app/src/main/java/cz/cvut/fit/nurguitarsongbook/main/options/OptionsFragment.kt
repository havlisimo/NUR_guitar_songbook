package cz.cvut.fit.nurguitarsongbook.main.options

import android.Manifest
import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.angads25.filepicker.model.DialogConfigs
import com.github.angads25.filepicker.model.DialogProperties
import com.github.angads25.filepicker.view.FilePickerDialog
import cz.cvut.fit.nurguitarsongbook.App
import cz.cvut.fit.nurguitarsongbook.R
import cz.cvut.fit.nurguitarsongbook.base.BaseFragment
import cz.cvut.fit.nurguitarsongbook.model.data.OptionsMockup
import kotlinx.android.synthetic.main.dialog_songbook.*
import kotlinx.android.synthetic.main.dialog_songbook.view.*
import kotlinx.android.synthetic.main.fragment_options.view.*
import kotlinx.android.synthetic.main.item_text_size.view.*
import org.jetbrains.anko.*
import java.io.File


/**
 * Created by vasek on 11/28/2017.
 */
class OptionsFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val v = inflater!!.inflate(R.layout.fragment_options, container, false)
        v.item_text_size.ts_desc.text = textSizeToStr(OptionsMockup.songTextSize)
        v.item_text_size.setOnClickListener{_-> changeTextSize()}
        v.item_backup.setOnClickListener{_-> checkpermissions({createBackup()})}
        v.item_restore.setOnClickListener{_-> checkpermissions({loadBackup()})}
        v.import_song.setOnClickListener{_-> checkpermissions({importSong()})}
        v.import_songbook.setOnClickListener{_-> checkpermissions({importSongbook()}) }
        App.instance.activity?.supportActionBar?.setTitle(R.string.menu_settings)
        return v
    }

    fun textSizeToStr(num : Int) : String {
        when (num) {
            14 -> return getString(R.string.options_ts_small)
            18 ->  return getString(R.string.options_ts_medium)
            22 ->  return getString(R.string.options_ts_large)
        }
        return getString(R.string.options_ts_unk)
    }

    fun strToTextSize(str : String) : Int {
        when (str) {
            getString(R.string.options_ts_small) -> return 14
            getString(R.string.options_ts_medium) ->  return 18
            getString(R.string.options_ts_large) ->  return 22
        }
        return 14
    }

    fun changeTextSize() {
        val sizes = listOf(getString(R.string.options_ts_small), getString(R.string.options_ts_medium), getString(R.string.options_ts_large))
        selector(getString(R.string.options_ts_dialog), sizes, { intf, i ->
            OptionsMockup.songTextSize = strToTextSize(sizes[i])
            view.item_text_size.ts_desc.text = sizes[i]
        })
    }

    fun createBackup() {
        val builder = getDialogBuilder(activity)
        builder.setPositiveButton(R.string.songbook_create,  null)
        builder.setTitle("Create backup file")
        val diag = builder.create()
        diag.show()
        diag.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener({v -> run {
            if (diag.wrapper.error != null) {
                toast(activity.getString(R.string.songbook_errors));
            } else {
                val name = diag.name.text.toString() + ".json"
                val file = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), name)
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
        dialog.setDialogSelectionListener { files -> run {
            val res = OptionsMockup.loadBackup(files[0])
            if (!res) toast(R.string.options_file_review)
            else toast(R.string.options_file_import_success)
        } }
        dialog.show()
    }

    fun importSong() {
        val dialog = prepDialog()
        dialog.setDialogSelectionListener { files -> run {
            val res = OptionsMockup.importSong(files[0])
            if (!res) toast(R.string.options_file_review)
            else toast(R.string.options_file_import_success)
        } }
        dialog.show()
    }

    fun importSongbook() {
        val dialog = prepDialog()
        dialog.setDialogSelectionListener { files -> run {
            val res = OptionsMockup.importSongbook(files[0])
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
        val file = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "song.json")
        if (!file.exists()) file.createNewFile()
        val file2 = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "songbook.json")
        if (!file2.exists()) file2.createNewFile()
        val file3 = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "backup1.json")
        if (!file3.exists()) file3.createNewFile()

    }

    fun getAppThemeId() : Int {
        return activity.packageManager.getActivityInfo(activity.componentName, 0).theme
    }

    fun prepDialog() : FilePickerDialog {

        val properties = DialogProperties()
        properties.selection_mode = DialogConfigs.SINGLE_MODE;
        properties.selection_type = DialogConfigs.FILE_SELECT;
        properties.root = Environment.getExternalStorageDirectory()
        properties.error_dir = File(DialogConfigs.DEFAULT_DIR);
        properties.offset = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        properties.extensions = arrayOf("txt", "json")
        val dialog = FilePickerDialog(activity, properties, getAppThemeId());
        dialog.setTitle(R.string.options_import_dialog_title)
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