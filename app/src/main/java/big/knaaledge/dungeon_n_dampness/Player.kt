package big.knaaledge.dungeon_n_dampness

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import big.knaaledge.dungeon_n_dampness.InventoryItem
import big.knaaledge.dungeon_n_dampness.data.Action

class Player {
    var flags = mutableStateMapOf<String, Boolean>()
    init {
        AddFlags()
    }

    private fun AddFlags(){
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
    }

    fun IsFlagSet(flag : String) : Boolean? {
        if (!flag.isNullOrEmpty() && flag.first() == '!')
            return !flags[flag.substringAfter('!')]!!
        else
            return flags[flag]
    }
}