package big.knaaledge.dungeon_n_dampness.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import big.knaaledge.dungeon_n_dampness.data.ActionItem

@Composable
fun GetActionButton(actionItem: ActionItem = ActionItem(), modifier: Modifier = Modifier) {
    val colour = MaterialTheme.colors.primary

    if (actionItem.message == ""){
        val stroke = Stroke(width = 2f,
            pathEffect = PathEffect.dashPathEffect(floatArrayOf(12f, 6f), 0f))

        TextButton(onClick = { /*Empty action*/ }, modifier = modifier.drawBehind {
            drawRoundRect(color = colour, style = stroke) }
        ) {
            Text(text = "", color = MaterialTheme.colors.primary)
        }

    }
    else{
        val stroke = BorderStroke(2.dp, Brush.radialGradient(colors = listOf(colour, colour)))
        TextButton(onClick = { actionItem.action() }, modifier = modifier, border = stroke
        ) {
            Text(text = actionItem.message, color = colour)
        }
    }
}