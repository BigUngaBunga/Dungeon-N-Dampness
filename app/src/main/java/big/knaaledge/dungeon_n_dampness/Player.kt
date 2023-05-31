package big.knaaledge.dungeon_n_dampness

import android.util.Log
import androidx.compose.runtime.mutableStateMapOf

class Player {
    var flags = mutableStateMapOf<String, Boolean>()
    init {
        AddFlags()
    }

    private fun AddFlags(){
        flags["ouch"] = false
        flags.set("ouch", false)
        flags.set("box_moved", false)
        flags.set("slab_lifted", false)
        flags.set("rocks_cleared", false)
        flags.set("flow_increased", false)
        flags.set("gator_got_gotten", false)
        flags.set("door_unlocked", false)

        flags.set("has_key", false)
        flags.set("has_file", false)
        flags.set("has_book", false)
        flags.set("has_herb", false)
        flags.set("has_leg", false)
        flags.set("has_arm", false)
        flags.set("has_boomerang", false)
        flags.set("has_bucket", false)
        Log.e("Debug", "${flags.keys}")
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
}