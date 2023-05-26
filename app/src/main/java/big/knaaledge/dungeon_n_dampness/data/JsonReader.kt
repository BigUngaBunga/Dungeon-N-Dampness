package big.knaaledge.dungeon_n_dampness.data

import android.content.Context
import java.io.InputStreamReader
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class JsonReader(private val context: Context) {

    private val gson = Gson()
//TODO gör om till att läsa in scener

//    fun readGroups(): List<Group>{
//        val inputStream = context.resources.openRawResource(R.raw.groups)
//
//        val itemType = object : TypeToken<List<GroupResponse>>() {}.type
//        val reader = InputStreamReader(inputStream)
//        return gson.fromJson<List<GroupResponse>>(reader, itemType).map {
//            it.toGroup()
//        }
//    }
}