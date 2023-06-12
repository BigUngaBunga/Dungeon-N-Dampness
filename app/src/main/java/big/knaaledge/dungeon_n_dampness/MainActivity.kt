package big.knaaledge.dungeon_n_dampness
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import big.knaaledge.dungeon_n_dampness.JSON.JsonReader
import big.knaaledge.dungeon_n_dampness.components.SlowText
import big.knaaledge.dungeon_n_dampness.data.Action
import big.knaaledge.dungeon_n_dampness.data.Scene
import big.knaaledge.dungeon_n_dampness.ui.theme.DungeonNDampnessTheme

var output = mutableStateListOf<String>()
var messageQueue = mutableStateListOf<String>()
var readLines = mutableStateOf(0)
var possibleActions = mutableStateListOf<Action>()
var sceneIndexStack = mutableStateListOf(1)

var endString: String = "> Placeholder"
var endState: String = "Win/Lose"

var scenes = mutableStateListOf<Scene>()
var player = mutableStateOf(Player())

var addedInitialActions = mutableStateOf(false)
var wroteState = mutableStateOf(false)

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
                    Navigation { ClearSession(); ReadScenesFromJson() }
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

    fun ClearSession(){
        scenes.clear()
        player.value = Player()
        sceneIndexStack.clear()
        sceneIndexStack.add(1)
        messageQueue.clear()
        output.clear()
        addedInitialActions.value = false
        wroteState.value = false
    }
}


@Composable
fun Navigation(resetGame: () -> Unit = {}){
    val navController = rememberNavController()
    NavHost(navController, startDestination = Screen.Main.route) {
        composable(route = Screen.Main.route){
            MainMenu(navController)
        }
        composable(route = Screen.End.route){
            EndScreen(navController, endState, endString, resetGame)
        }
        composable(route = Screen.Game.route){
            GameScreen(navController)
        }
        composable(route = Screen.Credits.route){
            CreditScreen(navController)
        }
    }
}

@Composable
fun CreditScreen(navController: NavController) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Credits()
        Box{
            TextButton(
                onClick = { navController.navigate(Screen.Main.route) },
                modifier = Modifier
                    .offset(128.dp, 300.dp)
                    .size(128.dp, 64.dp)
                    .padding(2.dp),
                border = BorderStroke(2.dp, Brush.radialGradient(colors = listOf(MaterialTheme.colors.primary, MaterialTheme.colors.primary)))
            ) {
                Text(
                    "Back",
                    color = MaterialTheme.colors.primary
                )
            }
        }
    }
}

@Composable
fun GameScreen(navController: NavController) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        CreateGameScreen(navController)
    }
}

@Composable
fun EndScreen(navController: NavController, state: String, description: String, resetGame: () -> Unit = {}) {
    Column(
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colors.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        SlowText(
            message = state,
            modifier = Modifier
                .padding(16.dp),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            delayTime = 60,
            onFinishedWriting = { wroteState.value = true}
        )
        if (wroteState.value){
            SlowText(
                message = description,
                modifier = Modifier.padding(16.dp)
            )
        }
        Box{
            TextButton(
                onClick = { navController.navigate(Screen.Main.route); resetGame() },
                modifier = Modifier
                    .offset(0.dp, 16.dp)
                    .size(128.dp, 64.dp)
                    .padding(2.dp),
                border = BorderStroke(2.dp, Brush.radialGradient(colors = listOf(MaterialTheme.colors.primary, MaterialTheme.colors.primary)))
            ) {
                Text(
                    "Return to Main Menu",
                    color = MaterialTheme.colors.primary
                )
            }
        }
    }
}

@Composable
fun MainMenu(navController: NavController) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Title()
        Box{
            TextButton(
                onClick = { navController.navigate(Screen.Game.route) },
                modifier = Modifier
                    .offset(128.dp, 300.dp)
                    .size(128.dp, 64.dp)
                    .padding(2.dp),
                    border = BorderStroke(2.dp, Brush.radialGradient(colors = listOf(MaterialTheme.colors.primary, MaterialTheme.colors.primary)))
            ){
                Text(
                    "Start Game",
                    color = MaterialTheme.colors.primary
                )
            }
        }
        Box{
            TextButton(
                onClick = { navController.navigate(Screen.Credits.route) },
                modifier = Modifier
                    .offset(128.dp, 370.dp)
                    .size(128.dp, 64.dp)
                    .padding(2.dp),
                    border = BorderStroke(2.dp, Brush.radialGradient(colors = listOf(MaterialTheme.colors.primary, MaterialTheme.colors.primary)))
                ){
                Text(
                    "Credits",
                    color = MaterialTheme.colors.primary
                )
            }
        }
    }
}
@Composable
fun Title() {
    SlowText(
        message = "> Dungeons & Dampness",//'n'
        modifier = Modifier
            .offset(0.dp, 200.dp)
            .padding(16.dp),
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Bold,
        fontSize = 30.sp,
        delayTime = 60,
    )
}

@Composable
fun Credits() {
    SlowText(
        message = "A game by Truls Rockström and Pär Ängquist",
        textAlign = TextAlign.Center,
        fontSize = 18.sp,
        modifier = Modifier
            .offset(0.dp, 200.dp)
            .padding(16.dp),
        delayTime = 60,
    )
}
sealed class Screen(val route: String){
    object Main: Screen("main")
    object End: Screen("end")
    object Game: Screen("game")
    object Credits: Screen("credits")
}

