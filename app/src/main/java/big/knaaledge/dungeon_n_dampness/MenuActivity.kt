package big.knaaledge.dungeon_n_dampness

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHost
import big.knaaledge.dungeon_n_dampness.components.GetActionButton
import big.knaaledge.dungeon_n_dampness.components.SlowText
import big.knaaledge.dungeon_n_dampness.data.ActionItem
import big.knaaledge.dungeon_n_dampness.ui.theme.DungeonNDampnessTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

var addedTitle = mutableStateOf(false)

class MenuActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DungeonNDampnessTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Title()
                }
            }
        }
    }
}

@Composable
fun Title() {
    //Text(text = "Dungeons 'n' Dampness")
    if(addedTitle.value)
        return
    addedTitle.value = true
    messageQueue.add("Dungeons 'n' Dampness")
}

@Composable
fun Navigation(){
    val navController = rememberNavController()
    NavHost(navController, startDestination = "") {
        composable(route = ""){

        }
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

fun ClearOutput(){
    messageQueue.clear()
    output.clear()
    readLines.value = 0
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

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    DungeonNDampnessTheme {
        Title()
    }
}