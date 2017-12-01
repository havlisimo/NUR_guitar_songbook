package cz.cvut.fit.nurguitarsongbook.model.entity.json

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SongsImport {

    @SerializedName("songs")
    @Expose
    var songs: List<Song>? = null

}
