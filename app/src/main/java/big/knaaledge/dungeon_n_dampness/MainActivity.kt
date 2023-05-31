package big.knaaledge.dungeon_n_dampness

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.interaction.DragInteraction
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import big.knaaledge.dungeon_n_dampness.JSON.JsonReader
import big.knaaledge.dungeon_n_dampness.components.GetActionButton
import big.knaaledge.dungeon_n_dampness.components.SlowText
import big.knaaledge.dungeon_n_dampness.data.Action
import big.knaaledge.dungeon_n_dampness.data.Scene
import big.knaaledge.dungeon_n_dampness.ui.theme.DungeonNDampnessTheme
import kotlinx.coroutines.*

var output = mutableStateListOf<String>()
var messageQueue = mutableStateListOf<String>()
var readLines = mutableStateOf(0)
var possibleActions = mutableStateListOf<Action>()
var sceneIndexStack = mutableStateListOf<Int>(1)

var scenes = mutableStateListOf<Scene>()
var player = mutableStateOf(Player())

var addedInitialText = mutableStateOf(false)
var addedInitialActions = mutableStateOf(false)

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ReadScenesFromJson()
        setContent {
            AddInitialText()
            AddInitialActions()

            DungeonNDampnessTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    PrintOutput()
                    AddButtons()
                }
            }
        }
    }

    fun ReadScenesFromJson(){
        var reader = JsonReader(this)
        var sceneList = reader.readScenes()
        for (scene in sceneList){
            scenes.add(scene)
        }

        var counter = 0
        for (scene in scenes){
            Log.e("SCENE", "Displaying scene ${++counter}")
            Log.e("SCENE", "First description ${scene.descriptions.first()}")
            Log.e("SCENE", "First action ${scene.actions.first()}")
        }
    }
}

//region Console output
@Composable
fun AddInitialText(){
    if(addedInitialText.value)
        return
    addedInitialText.value = true
    messageQueue.add("Welcome to Dungeon & Dampness.")
    messageQueue.add("You awaken in a damp cell. You are scheduled for execution. With a slight preference for living you decide to escape instead.")
    messageQueue.add("You wave to the game")
    messageQueue.add("The game waves back")
    for (i in 1..3){
        messageQueue.add("Reeeee")
    }
}

@Composable
fun PrintOutput() {
    if (output.size <= 0)
        DequeueMessage()
    LazyColumn(
        Modifier
            .padding(16.dp)
            .padding(0.dp, 0.dp, 0.dp, 200.dp)){
        items(output){message ->
                SlowText(message = message, readSlowly = readLines.value <= output.lastIndexOf(message),
                    onFinishedWriting = {DequeueMessage(); readLines.value++ })
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

fun <T>EnqueueMessage(vararg messages: T){
    for (t in messages)
        EnqueueMessage(t.toString())
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

fun AddInitialActions(){
    if(addedInitialActions.value)
        return
    addedInitialActions.value = true
    StartScene("Welcome to Dungeon & Dampness.", 1)
}

fun ClearActions(includeInventory: Boolean = true){
    possibleActions.clear()
    if (includeInventory){ //TODO only include if have item
        var inventoryAction = Action( message = "Check inventory",
            description = "It's a wonder your pockets still hold up")
        inventoryAction.action = {
            scenes.get(0).PopAction()
            scenes.get(0).AddAction(Action("Go back", "You stop looking at your stuff and get back to the task at hand.",
                custom_action = true,
                action = { StartScene("You stop looking at your stuff and get back to the task at hand.")}))
            StartScene(inventoryAction.description, 0, includeInventory = false) }
        possibleActions.add(inventoryAction)
    }
}

//endregion

//region Scenes
fun StartScene(actionDescription: String, sceneIndex: Int, includeInventory: Boolean = true, goBack: Boolean = false){
    if (!goBack)
        sceneIndexStack.add(sceneIndex)
    ClearOutput(actionDescription, scenes[sceneIndex].GetDescription(player.value))
    ClearActions(includeInventory)
    scenes[sceneIndex].GetActions(possibleActions, player.value)
}

fun StartScene(actionDescription: String){
    sceneIndexStack.removeLast()
    StartScene(actionDescription, sceneIndexStack.last(), goBack = true)
}
//endregion

