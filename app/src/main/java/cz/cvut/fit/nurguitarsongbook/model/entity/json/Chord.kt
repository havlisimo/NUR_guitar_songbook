package cz.cvut.fit.nurguitarsongbook.model.entity.json

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Chord {

    @SerializedName("index")
    @Expose
    var index: Int? = null
    @SerializedName("name")
    @Expose
    var name: String? = null

}
