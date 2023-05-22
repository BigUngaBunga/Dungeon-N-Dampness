package big.knaaledge.dungeon_n_dampness

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import big.knaaledge.dungeon_n_dampness.ui.theme.DungeonNDampnessTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            DungeonNDampnessTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Column(Modifier.padding(8.dp)){
                        PrintSlowly("Welcome to Dungeon & Dampness")
                        PerformAction("Wave")
                        PrintSlowly("The game waves back")
                        PrintSlowly(">_")
                    }
                }


            }
        }
    }
}

@Composable
fun PrintSlowly(message: String) {
    Text(text = message)
    //TODO print letter by letter
    //TODO add formating
}

@Composable
fun PerformAction(action: String){
    PrintSlowly(message = "> ${action}")
}