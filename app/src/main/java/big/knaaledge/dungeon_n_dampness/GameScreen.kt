package big.knaaledge.dungeon_n_dampness

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import big.knaaledge.dungeon_n_dampness.components.GetActionButton
import big.knaaledge.dungeon_n_dampness.components.SlowText
import big.knaaledge.dungeon_n_dampness.data.Action
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun CreateGameScreen(navController: NavController) {
    StartGame()
    PrintOutput()
    AddButtons(navController)
}

//region Actions
@Composable
fun AddButtons(navController: NavController){
        var actionsIncluded = 0
        val numberOfRows = 3
        val containerModifier = Modifier.fillMaxWidth()

        Column(verticalArrangement = Arrangement.Bottom, modifier = containerModifier) {
            for (i in 1..numberOfRows){
                Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = containerModifier.padding(1.dp, 4.dp)) {
                    val buttonModifier = Modifier.weight(0.25f).height(64.dp).padding(2.dp)
                    AddActionButton(actionsIncluded++, buttonModifier, navController)
                    AddActionButton(actionsIncluded++, buttonModifier, navController)
                }
            }
        }
    }

@Composable
fun AddActionButton(actionIdex: Int, modifier: Modifier, navController: NavController){
    if (actionIdex > possibleActions.size - 1)
        GetActionButton(modifier = modifier, navController = navController)
    else
        GetActionButton(possibleActions[actionIdex], modifier, navController)
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
    if (sceneIndexStack.last() != 0 && player.value.AnyFlagSet(player.value.itemFlags.value)){
        var inventoryAction = Action( message = "Check inventory",
            description = "")
        inventoryAction.action = {
            scenes.get(0).PopAction()
            scenes.get(0).AddAction(
                Action("Go back", "You stop looking at your stuff and get back to the task at hand.",
                    custom_action = true,
                    action = { StartScene("Go back", "You stop looking at your stuff and get back to the task at hand.")})
            )
            StartScene(inventoryAction.message, inventoryAction.description, 0, includeInventory = false) }
        possibleActions.add(inventoryAction)
    }
}

fun EvaluateWinLoss(navController: NavController){
    if(player.value.AllFlagsSet("file_bars_3")){
        if(player.value.AllFlagsSet("sleepy_guard")){
            endState = "YOU ESCAPED"
            endString = "> The last of the bars fall to the floor of the cell with a clank. The guard is still snoring comfortably. You squeeze through the hole and sneak away through the corridor..."
            navController.navigate(Screen.End.route)
        }
        else{
            endState = "YOU GOT CAUGHT"
            endString = "> Just as you're about to finish with the bars you're caught red-handed. The guard loudly raises the alarm. Looks like your execution has been expedited..."
            navController.navigate(Screen.End.route)
        }
    }
    else if(player.value.AllFlagsSet("swimming_2")){
        if(player.value.AllFlagsSet("bucket_on_head")){
            endState = "YOU ESCAPED"
            endString = "> You can't have expected this to really work right? Yet somehow it does. The bucket keeps the water from filling your lungs and despite not seeing where you're heading, you eventually breach the surface. You remove the bucket and find yourself in a lake outside the city."
            navController.navigate(Screen.End.route)
        }
        else{
            endState = "YOU DROWNED"
            endString = "> You were desperate and out of options, but you should have known swimming head first into running water wouldn't end well. The last bit of air leaves your lungs and you lose consciousness."
            navController.navigate(Screen.End.route)
        }
    }
    else if(player.value.AllFlagsSet("gator_gotem")){
        endState = "YOU WERE EATEN"
        endString = "> The maw is indeed large enough to swallow you whole. Your attempt at escape ended as snack for a hungry alligator."
        navController.navigate(Screen.End.route)
    }
    else if(player.value.AllFlagsSet("climbed_out")){
        endState = "YOU ESCAPED"
        endString = "> Your muscles strain as you climb up the rope. You emerge through the well into a busy marketplace. You and your sewage-drenched clothes catch some side eye, but you quickly disappear in the crowd."
        navController.navigate(Screen.End.route)
    }
    else if (player.value.AllFlagsSet("overslept")){
        endState = "YOU DIDN'T EVEN TRY"
        endString = "> You awake with a loud yawn only to notice that two guards are hoisting you out of your cell. Perhaps sleeping wasn't the best course of action."
        navController.navigate(Screen.End.route)
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
            if (!t.toString().isNullOrEmpty())
                EnqueueMessage(t.toString())
    }
}
//endregion

//region Scenes
fun StartScene(actionMessage : String, actionDescription: String, sceneIndex: Int, includeInventory: Boolean = true, goBack: Boolean = false){
    if (!goBack)
        sceneIndexStack.add(sceneIndex)
    ClearOutput("> $actionMessage", actionDescription, scenes[sceneIndex].GetDescription(player.value))
    UpdateActions()
}

fun StartScene(actionMessage : String, actionDescription: String){
    sceneIndexStack.removeLast()
    StartScene(actionMessage, actionDescription, sceneIndexStack.last(), goBack = true)
}
//endregion