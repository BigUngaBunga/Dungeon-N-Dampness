package big.knaaledge.dungeon_n_dampness.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SlowTextContainer(val message: String,
                        val output: MutableState<String>){
    suspend fun UpdateTextSlowly(readSlowly: Boolean,
                                 delayTime : Long,
                                 newLineFactor: Long = 3,
                                 punctuationFactor : Long = 3){

        if (!readSlowly){
            output.value = message
            return
        }

        for (value in message){
            output.value = output.value.plus(value)
            if(value == '>')
                delay(delayTime * newLineFactor)
            else if (value == '.' || value == '?' || value == '!')
                delay(delayTime * punctuationFactor)
            else
                delay(delayTime)
        }
    }
}


@Composable
fun SlowText(message : String, readSlowly : Boolean = true, onFinishedWriting: () -> Unit = {}) {
    val output = remember{ mutableStateOf("")}
    var textContainer = remember { SlowTextContainer(message, output) }

    Text(text = "${output.value}", modifier = Modifier.padding(0.dp, 2.dp))

    LaunchedEffect(key1 = textContainer){
        coroutineScope{
            var writingJob = launch{
                textContainer.UpdateTextSlowly(readSlowly = readSlowly,
                                                delayTime = 30, newLineFactor = 5)}
            writingJob.join()
            onFinishedWriting()
        }
    }
}