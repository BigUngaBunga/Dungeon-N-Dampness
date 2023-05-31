package big.knaaledge.dungeon_n_dampness
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
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
var sceneIndexStack = mutableStateListOf<Int>(1)

var endString: String = "> Placeholder"

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
                    //CreateGameScreen()
                    Navigation()
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

@Composable
fun Navigation(){
    val navController = rememberNavController()
    NavHost(navController, startDestination = Screen.Win.route) {
        composable(route = Screen.Main.route){
            MainMenu(navController)
        }
        composable(route = Screen.Win.route){
            WinScreen(navController, endString)
        }
        composable(route = Screen.Lose.route){
            GameOverScreen(navController, endString)
        }
        composable(route = Screen.Game.route){
            GameScreen()
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
fun GameScreen() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        CreateGameScreen()
    }
}

@Composable
fun GameOverScreen(navController: NavController, output: String) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        SlowText(
            message = output,
            modifier = Modifier.padding(16.dp)
        )
        Box{
            TextButton(
                onClick = { navController.navigate(Screen.Main.route) },
                modifier = Modifier
                    .offset(128.dp, 600.dp)
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
fun WinScreen(navController: NavController, output: String) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        SlowText(
            message = output,
            modifier = Modifier.padding(16.dp)
        )
        Box{
            TextButton(
                onClick = { navController.navigate(Screen.Main.route) },
                modifier = Modifier
                    .offset(128.dp, 600.dp)
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
        message = "> Dungeons 'n' Dampness",
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
    object Win: Screen("win")
    object Lose: Screen("lose")
    object Game: Screen("game")
    object Credits: Screen("credits")
}

