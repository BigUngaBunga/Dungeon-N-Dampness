package big.knaaledge.dungeon_n_dampness.data

data class Action(val message: String = "",
                  val description: String = "",
                  val performMoreThanOnce: Boolean = false,
                  val wasPerformed: Boolean = false,
                  var action: () -> Unit = {}) {
    fun IsAvailable() : Boolean {
        return performMoreThanOnce || !wasPerformed
    }
}