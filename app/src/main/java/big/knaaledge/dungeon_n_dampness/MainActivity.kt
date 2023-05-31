package big.knaaledge.dungeon_n_dampness
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
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
    NavHost(navController, startDestination = Screen.Main.route) {
        composable(route = Screen.Main.route){
            MainMenu(navController)
        }
        composable(route = Screen.Win.route){
            WinScreen(navController)
        }
        composable(route = Screen.Lose.route){
            GameOverScreen(navController)
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
            Button(
                onClick = { navController.navigate("main") },
                modifier = Modifier
                    .offset(120.dp, 300.dp)
                    .size(150.dp, 50.dp),
            ) {
                Text(
                    "Back",
                    color = MaterialTheme.colors.background
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
fun GameOverScreen(navController: NavController) {
    Button(
        onClick = { navController.navigate(Screen.Main.route) }
    ) {
        Text("Return to Main Menu")
    }
}

@Composable
fun WinScreen(navController: NavController) {
    Button(
        onClick = { navController.navigate(Screen.Main.route) }
    ) {
        Text("Return to Main Menu")
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
            Button(
                onClick = { navController.navigate(Screen.Game.route) },
                modifier = Modifier
                    .offset(120.dp, 300.dp)
                    .size(150.dp, 50.dp),

            ){
                Text(
                    "Start Game",
                    color = MaterialTheme.colors.background
                )
            }
        }
        Box{
            Button(
                onClick = { navController.navigate(Screen.Credits.route) },
                modifier = Modifier
                    .offset(120.dp, 370.dp)
                    .size(150.dp, 50.dp),

                ){
                Text(
                    "Credits",
                    color = MaterialTheme.colors.background
                )
            }
        }
    }
}
@Composable
fun Title() {
    Text(
        text = "Dungeons 'n' Dampness",
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Bold,
        fontSize = 30.sp,
        modifier = Modifier.offset(0.dp, 200.dp)
            .padding(16.dp),
    )
}

@Composable
fun Credits() {
    Text(
        text = "A game by Truls Rockström and Pär Ängquist",
        textAlign = TextAlign.Center,
        fontSize = 16.sp,
        modifier = Modifier.offset(0.dp, 200.dp)
            .padding(16.dp),
    )
}
sealed class Screen(val route: String){
    object Main: Screen("main")
    object Win: Screen("win")
    object Lose: Screen("lose")
    object Game: Screen("game")
    object Credits: Screen("credits")
}

