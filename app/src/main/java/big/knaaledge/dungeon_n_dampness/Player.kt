package big.knaaledge.dungeon_n_dampness

import android.util.Log
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import kotlin.random.Random

class Player {
    var flags = mutableStateMapOf<String, Boolean>()
    var itemFlags = mutableStateOf("")
    init {
        AddFlags()
        AddItemFlags()

        Log.e("DEBUG", "Initialised player")
    }

    private fun AddFlags(){
        flags.set("ouch", false)
        flags.set("box_moved", false)
        flags.set("slab_lifted", false)
        flags.set("rocks_cleared", false)
        flags.set("flow_increased", false)
        flags.set("gator_got_gotten", false)
        flags.set("door_unlocked", false)
        flags.set("in_first_cell", true)
        flags.set("cat_be_ded", Random.nextInt(0, 2) == 0)
        flags.set("file_bars_1", false)
        flags.set("file_bars_2", false)
        flags.set("file_bars_3", false)
        flags.set("sleepy_guard", false)
        flags.set("herb_mah_drink", false)
    }

    private fun AddItemFlags(){
        var items = listOf("has_key", "has_file", "has_book", "has_herb","has_leg","has_arm",
            "has_boomerang", "has_bucket")

        var stringBuilder = java.lang.StringBuilder()

        for (item in items){
            flags.set(item, false)
            stringBuilder.append(item)
            stringBuilder.append(", ")
        }
        stringBuilder.setLength(stringBuilder.length -2)
        itemFlags.value = stringBuilder.toString()
    }

    fun IsFlagSet(flag : String) : Boolean? {
        if (flag == "")
            return true
        else if (flag != null && flag.first() == '!') {
            return !flags[flag.substringAfter('!')]!!
        }
        return flags[flag]
    }

    fun AllFlagsSet(flags: String): Boolean{
        val parsedFlags = flags.split(", ")

        for (flag in parsedFlags){
            if (!IsFlagSet(flag)!!)
                return false
        }
        return true
    }

    fun AnyFlagSet(flags: String): Boolean{
        val parsedFlags = flags.split(", ")

        for (flag in parsedFlags){
            if(IsFlagSet(flag)!!)
                return true
        }
        return false
    }

    fun SetFlags(flagString: String){
        val parsedFlags = flagString.split(", ")
        for (flag in parsedFlags){
            flags[flag.substringAfter('!')] = flag.first() != '!'
        }
    }
}