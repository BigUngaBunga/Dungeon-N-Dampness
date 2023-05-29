package big.knaaledge.dungeon_n_dampness.data

import big.knaaledge.dungeon_n_dampness.Player

data class Action(val message: String = "",
                  val description: String = "",
                  val requiredFlag: String = "",
                  val performMoreThanOnce: Boolean = false,
                  val wasPerformed: Boolean = false,
                  var action: () -> Unit = {}) {
    fun IsAvailable(player: Player) : Boolean {
        var flagIsSet = requiredFlag == ""
        if (!flagIsSet)
            player.IsFlagSet(requiredFlag)
        return flagIsSet && (performMoreThanOnce || !wasPerformed)
    }
}