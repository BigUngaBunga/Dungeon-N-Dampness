package big.knaaledge.dungeon_n_dampness.data

data class ActionItem(val message: String = "",
                      val description: String = "",
                      val action: () -> Unit = {}) {}