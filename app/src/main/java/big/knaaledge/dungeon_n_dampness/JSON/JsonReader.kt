package big.knaaledge.dungeon_n_dampness.JSON

import android.content.Context
import big.knaaledge.dungeon_n_dampness.R
import big.knaaledge.dungeon_n_dampness.data.Scene
import java.io.InputStreamReader
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class JsonReader(private val context: Context) {

    private val gson = Gson()
    fun readScenes() : List<Scene>{
        val inputStream = context.resources.openRawResource(R.raw.scenes)

        val itemType = object : TypeToken<List<SceneResponse>>() {}.type
        val reader = InputStreamReader(inputStream)
        return gson.fromJson<List<SceneResponse>>(reader, itemType).map {
            it.toScene()
        }
    }
}