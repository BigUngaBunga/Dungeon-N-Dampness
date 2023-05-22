package big.knaaledge.dungeon_n_dampness.components

import androidx.compose.material.Text
import androidx.compose.runtime.*
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SlowTextContainer(val message: String,
                        val output: MutableState<String>){
    suspend fun UpdateTextSlowly(delayTime : Long,
                                 newLineFactor: Long = 3,
                                 punctuationFactor : Long = 3){
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
fun SlowText(message : String, onFinishedWriting: () -> Unit = {}) {
    val output = remember{ mutableStateOf("")}
    var textContainer = remember { SlowTextContainer(message, output) }

    Text(text = "${output.value}")//, lineHeight = 26.sp

    LaunchedEffect(key1 = textContainer){
        coroutineScope{
            var writingJob = launch{
                textContainer.UpdateTextSlowly(delayTime = 42, newLineFactor = 5)}
            writingJob.join()
            onFinishedWriting()
        }
    }
}