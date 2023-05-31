package big.knaaledge.dungeon_n_dampness

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import big.knaaledge.dungeon_n_dampness.components.GetActionButton
import big.knaaledge.dungeon_n_dampness.components.SlowText
import big.knaaledge.dungeon_n_dampness.data.Action
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun CreateGameScreen() {
    StartGame()
    PrintOutput()
    AddButtons()
}

//region Actions
@Composable
fun AddButtons(){
        var actionsIncluded = 0
        val numberOfRows = 3
        val containerModifier = Modifier.fillMaxWidth()

        Column(verticalArrangement = Arrangement.Bottom, modifier = containerModifier) {
            for (i in 1..numberOfRows){
                Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = containerModifier.padding(1.dp, 4.dp)) {
                    val buttonModifier = Modifier.weight(0.25f).height(64.dp).padding(2.dp)
                    AddActionButton(actionsIncluded++, buttonModifier)
                    AddActionButton(actionsIncluded++, buttonModifier)
                }
            }
        }
    }

@Composable
fun AddActionButton(actionIdex: Int, modifier: Modifier){
    if (actionIdex > possibleActions.size - 1)
        GetActionButton(modifier = modifier)
    else
        GetActionButton(possibleActions[actionIdex], modifier)
}

fun StartGame(){
    if(addedInitialActions.value)
        return
    addedInitialActions.value = true
    ClearOutput(scenes[sceneIndexStack.last()].GetDescription(player.value))
    ClearActions()
    scenes[sceneIndexStack.last()].GetActions(possibleActions, player.value)
}

fun UpdateActions(){
    ClearActions()
    scenes[sceneIndexStack.last()].GetActions(possibleActions, player.value)
}

fun ClearActions(){
    possibleActions.clear()
    if (player.value.AnyFlagSet(player.value.itemFlags.value)){ //TODO only include if have item
        var inventoryAction = Action( message = "Check inventory",
            description = "It's a wonder your pockets still hold up")
        inventoryAction.action = {
            scenes.get(0).PopAction()
            scenes.get(0).AddAction(
                Action("Go back", "You stop looking at your stuff and get back to the task at hand.",
                    custom_action = true,
                    action = { StartScene("You stop looking at your stuff and get back to the task at hand.")})
            )
            StartScene(inventoryAction.description, 0, includeInventory = false) }
        possibleActions.add(inventoryAction)
    }
}
//endregion

//region Console output
@Composable
fun PrintOutput() {
    if (output.size <= 0)
        DequeueMessage()
    LazyColumn(
        Modifier
            .padding(16.dp)
            .padding(0.dp, 0.dp, 0.dp, 200.dp)){
        items(output){message ->
            SlowText(
                message = message, readSlowly = readLines.value <= output.lastIndexOf(message),
                onFinishedWriting = {DequeueMessage(); readLines.value++ },
            )
        }
    }
}

fun DequeueMessage(){
    if (messageQueue.size <= 0)
        return
    var message = messageQueue.first()
    messageQueue.removeFirst()
    output.add("> $message")
}

fun EnqueueMessage(message: String){
    messageQueue.add(message)
    if (readLines.value >= output.size)
        DequeueMessage()
}

fun <T>ClearOutput(vararg messages: T){
    messageQueue.clear()
    output.clear()
    readLines.value = 0

    CoroutineScope(Dispatchers.Main).launch {
        delay(100)
        for (t in messages)
            EnqueueMessage(t.toString())

    }
}
//endregion

//region Scenes
fun StartScene(actionDescription: String, sceneIndex: Int, includeInventory: Boolean = true, goBack: Boolean = false){
    if (!goBack)
        sceneIndexStack.add(sceneIndex)
    ClearOutput(actionDescription, scenes[sceneIndex].GetDescription(player.value))
    UpdateActions()
}

fun StartScene(actionDescription: String){
    sceneIndexStack.removeLast()
    StartScene(actionDescription, sceneIndexStack.last(), goBack = true)
}
//endregion