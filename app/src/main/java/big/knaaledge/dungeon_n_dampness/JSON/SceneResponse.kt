package big.knaaledge.dungeon_n_dampness.JSON

import big.knaaledge.dungeon_n_dampness.data.Action
import big.knaaledge.dungeon_n_dampness.data.Description
import big.knaaledge.dungeon_n_dampness.data.Scene

data class SceneResponse (var sceneID : String,
                          var descriptions : MutableList<Description>,
                          var actions : MutableList<Action>) {}

fun SceneResponse.toScene(): Scene = Scene(descriptions, actions)
