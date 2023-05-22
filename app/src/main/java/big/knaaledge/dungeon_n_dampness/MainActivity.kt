package big.knaaledge.dungeon_n_dampness

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import big.knaaledge.dungeon_n_dampness.components.SlowText
import big.knaaledge.dungeon_n_dampness.ui.theme.DungeonNDampnessTheme

lateinit var output: MutableList<String>
lateinit var shownIndex: MutableState<Int>

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        shownIndex = mutableStateOf(0)
        output = mutableListOf()
        output.add("Welcome to Dungeon & Dampness.")
        output.add("You awaken in a damp cell. You are scheduled for execution. With a slight preference for living you decide to escape instead.")
        output.add("You wave to the game")
        output.add("The game waves back")
        for (i in 1..3){
            output.add("Reeeee")
        }


        setContent {
            DungeonNDampnessTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    PrintOutput()
                }
            }
        }
    }
}

@Composable
fun PrintOutput() {
    LazyColumn(Modifier.padding(16.dp)){
        item { SlowText(ListToString()) }
    }
}

fun ListToString(): String{
    var stringBuilder = java.lang.StringBuilder()
    for (message in output){
        stringBuilder.append("> ")
        stringBuilder.append(message)
        stringBuilder.appendLine()
        stringBuilder.appendLine()
    }

    return stringBuilder.toString()
}