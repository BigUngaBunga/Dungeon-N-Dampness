package big.knaaledge.dungeon_n_dampness.data

import big.knaaledge.dungeon_n_dampness.EnqueueMessage
import big.knaaledge.dungeon_n_dampness.Player
import big.knaaledge.dungeon_n_dampness.StartScene
import big.knaaledge.dungeon_n_dampness.UpdateActions

class Action(val message: String = "",
                  val description: String = "",
                  val required_flags: String = "",
                  val set_flags: String = "",
                  val perform_once: Boolean = false,
                  var wasPerformed: Boolean = false,
                  var go_to_scene: String = "",
                  var custom_action: Boolean = false,
                  var action: () -> Unit = {},
                  var HasBeenInitialised: Boolean = false
                    ) {

    fun OnFirstRun(player: Player){
        if (!custom_action){
            var sceneAction = {StartScene(message, description, go_to_scene.toInt())}
            if (go_to_scene.isEmpty() || go_to_scene == "")
                sceneAction = { EnqueueMessage("> $message", true); EnqueueMessage(description); UpdateActions() }
            action = { wasPerformed = true; SetFlagAction(player); sceneAction() }
        }
        HasBeenInitialised = true
    }

    fun SetFlagAction(player: Player){
        if (set_flags.isNullOrEmpty())
            return
        player.SetFlags(set_flags)
    }

    fun IsAvailable(player: Player) : Boolean {
        if (!HasBeenInitialised)
            OnFirstRun(player)
        var flagIsSet = required_flags.isEmpty() || player.AllFlagsSet(required_flags)
        return flagIsSet && (!perform_once || !wasPerformed)
    }
}