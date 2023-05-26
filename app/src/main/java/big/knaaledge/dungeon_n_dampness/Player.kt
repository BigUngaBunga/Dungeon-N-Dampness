package big.knaaledge.dungeon_n_dampness

import androidx.compose.runtime.mutableStateListOf
import big.knaaledge.dungeon_n_dampness.InventoryItem

class Player {
    var inventory = mutableStateListOf<InventoryItem>()

    var ouch: Boolean = false
    var boxPushed: Boolean = false
    var slabLifted: Boolean = false
    var rocksCleared: Boolean = false
    var flowIncreased: Boolean = false
    var gatorNeutralised: Boolean = false

}