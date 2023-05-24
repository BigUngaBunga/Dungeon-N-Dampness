package big.knaaledge.dungeon_n_dampness

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import big.knaaledge.dungeon_n_dampness.components.GetActionButton
import big.knaaledge.dungeon_n_dampness.components.SlowText
import big.knaaledge.dungeon_n_dampness.data.ActionItem
import big.knaaledge.dungeon_n_dampness.ui.theme.DungeonNDampnessTheme
import kotlinx.coroutines.flow.MutableStateFlow


//var outputList by mutableStateOf<MutableList<String>>()

lateinit var output: MutableList<String>
lateinit var addedSymbols: MutableState<Int>
lateinit var possibleActions: MutableList<ActionItem>


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            possibleActions = remember { mutableListOf() }
            AddInitialText()
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
}

@Composable
fun AddInitialText(){
    addedSymbols = remember { mutableStateOf(-1) }
    output = remember { mutableListOf() }
    output.add("Welcome to Dungeon & Dampness.")
    output.add("You awaken in a damp cell. You are scheduled for execution. With a slight preference for living you decide to escape instead.")
    output.add("You wave to the game")
    output.add("The game waves back")
    for (i in 1..3){
        output.add("Reeeee")
    }
}

@Composable
fun PrintOutput() {
    LazyColumn(
        Modifier
            .padding(16.dp)
            .padding(0.dp, 0.dp, 0.dp, 200.dp)){
        val text = ListToString()
        var readSymbols = text.length - addedSymbols.value
        if (addedSymbols.value == -1)
            readSymbols = 0
        item { SlowText(text, readSymbols) }
    }
}

fun UpdateOutput(newLine : String){
    addedSymbols.value = newLine.length
    output.add(newLine)
}

@Composable
fun AddButtons(){
    val numberOfRows = 3
    val modifier = Modifier.fillMaxWidth()


    Column(verticalArrangement = Arrangement.Bottom, modifier = modifier) {
//        for (i in 1..numberOfRows){
//            Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = modifier.padding(1.dp, 4.dp)) {
//                var buttonModifier = Modifier.weight(0.25f).height(64.dp).padding(2.dp)
//                GetActionButton("", buttonModifier)
//                GetActionButton("'Ello", buttonModifier)
//            }
//        }

        Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = modifier.padding(1.dp, 4.dp)) {
            val buttonModifier = Modifier
                .weight(0.25f)
                .height(64.dp)
                .padding(2.dp)
            GetActionButton(
                ActionItem("Check inventory", action = { output.add("Inventoooory")}),
                buttonModifier)
            GetActionButton(
                ActionItem("Scrounge around", action = {}),
                buttonModifier)
        }
        Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = modifier.padding(1.dp, 4.dp)) {
            val buttonModifier = Modifier
                .weight(0.25f)
                .height(64.dp)
                .padding(2.dp)
            GetActionButton(ActionItem("Watchu want??!?"), buttonModifier)
            GetActionButton(modifier =  buttonModifier)
        }
        Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = modifier.padding(1.dp, 4.dp)) {
            val buttonModifier = Modifier
                .weight(0.25f)
                .height(64.dp)
                .padding(2.dp)
            GetActionButton(modifier =  buttonModifier)
            GetActionButton(modifier =  buttonModifier)
        }
    }
}

fun ListToString(): String{
    val stringBuilder = java.lang.StringBuilder()
    for (message in output){
        stringBuilder.append("> ")
        stringBuilder.append(message)
        stringBuilder.appendLine()
        stringBuilder.appendLine()
    }

    return stringBuilder.toString()
}

