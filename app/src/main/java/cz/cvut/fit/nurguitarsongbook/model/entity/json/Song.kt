package cz.cvut.fit.nurguitarsongbook.model.entity.json

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.Collections

class Song {

    @SerializedName("name")
    @Expose
    var name: String = ""
    @SerializedName("artist")
    @Expose
    var artist: String = ""
    @SerializedName("text")
    @Expose
    var text: String = ""
    @SerializedName("chords")
    @Expose
    var chords: List<Chord> = Collections.emptyList()

}
