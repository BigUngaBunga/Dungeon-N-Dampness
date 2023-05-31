package big.knaaledge.dungeon_n_dampness.data

import androidx.compose.runtime.snapshots.SnapshotStateList
import big.knaaledge.dungeon_n_dampness.Player

//var descriptions : List<Description> = listOf<Description>()
//var actions : MutableList<Action> = mutableListOf<Action>()

data class Scene(var descriptions : List<Description>, var actions : MutableList<Action>) {
    fun GetDescription(player: Player) : String {
        for (description in descriptions){
            if (player.AllFlagsSet(description.required_flags))
                return description.text
        }
        return descriptions.last().text
    }

    fun AddAction(action: Action){
        actions.add(action)
    }

    fun PopAction(){
        actions.removeLast()
    }

    fun GetActions(possibleActions: SnapshotStateList<Action>, player: Player){
        for (action in actions){
            if (action.IsAvailable(player)){
                possibleActions.add(action)
            }
        }
    }
}