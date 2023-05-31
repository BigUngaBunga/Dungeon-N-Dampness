package big.knaaledge.dungeon_n_dampness
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import big.knaaledge.dungeon_n_dampness.JSON.JsonReader
import big.knaaledge.dungeon_n_dampness.data.Action
import big.knaaledge.dungeon_n_dampness.data.Scene
import big.knaaledge.dungeon_n_dampness.ui.theme.DungeonNDampnessTheme

var output = mutableStateListOf<String>()
var messageQueue = mutableStateListOf<String>()
var readLines = mutableStateOf(0)
var possibleActions = mutableStateListOf<Action>()
var sceneIndexStack = mutableStateListOf<Int>(1)

var scenes = mutableStateListOf<Scene>()
var player = mutableStateOf(Player())

var addedInitialActions = mutableStateOf(false)

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ReadScenesFromJson()
        setContent {
            DungeonNDampnessTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    CreateGameScreen()
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
    }
}

