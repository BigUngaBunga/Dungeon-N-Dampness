package big.knaaledge.dungeon_n_dampness.data

import androidx.compose.runtime.snapshots.SnapshotStateList

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

    fun GetActions(possibleActions: SnapshotStateList<Action>){
        for (action in actions){
            if (action.IsAvailable()){
                possibleActions.add(action)
            }
        }
    }
}