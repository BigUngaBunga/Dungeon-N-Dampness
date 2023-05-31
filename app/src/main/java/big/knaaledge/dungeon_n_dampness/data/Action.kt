package big.knaaledge.dungeon_n_dampness.data

import android.util.Log
import androidx.compose.foundation.interaction.DragInteraction
import big.knaaledge.dungeon_n_dampness.EnqueueMessage
import big.knaaledge.dungeon_n_dampness.Player
import big.knaaledge.dungeon_n_dampness.StartScene

class Action(val message: String = "",
                  val description: String = "",
                  val required_flags: String = "",
                  val perform_once: Boolean = true,
                  var wasPerformed: Boolean = false,
                  var go_to_scene: String = "",
                  var custom_action: Boolean = false,
                  var action: () -> Unit = {},
                  var HasBeenInitialised: Boolean = false
                    ) {

    fun OnFirstRun(){
        if (!custom_action){
            if (go_to_scene.isEmpty()){
                action = { EnqueueMessage(description); wasPerformed = true}
            }
            else{
                action = { StartScene(description, go_to_scene.toInt()); wasPerformed = true }
            }
        }
        HasBeenInitialised = true
    }

    fun IsAvailable(player: Player) : Boolean {
        if (!HasBeenInitialised)
            OnFirstRun()
        var flagIsSet = required_flags.isEmpty() || player.AllFlagsSet(required_flags)
        return flagIsSet && (!perform_once || !wasPerformed)
    }
}