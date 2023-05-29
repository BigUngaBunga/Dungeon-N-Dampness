package big.knaaledge.dungeon_n_dampness.data

import androidx.compose.runtime.snapshots.SnapshotStateList
import big.knaaledge.dungeon_n_dampness.Player

var descriptions : MutableList<String> = mutableListOf<String>()
var actions : MutableList<Action> = mutableListOf<Action>()


class Scene() {
    fun AddDescription(description: String){
        descriptions.add(description)
    }

    fun GetDescription() : String {
        return descriptions.first()
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