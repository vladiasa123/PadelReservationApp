import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.padel.ViewModels.ProfileViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpwardPopUpCard(modifier: Modifier = Modifier) {
var viewModel: ProfileViewModel = viewModel()
    Box(Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .clip(
                    RoundedCornerShape(30.dp, 30.dp, 0.dp, 0.dp)
                )
                .background(Color.White)
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(300.dp)
                .background(MaterialTheme.colorScheme.onPrimary)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ElevatedCard(modifier = Modifier.fillMaxWidth(), shape = RectangleShape) {
                    Text(
                        "Court 1",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier
                            .padding(top = 10.dp, bottom = 10.dp)
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 50.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                ElevatedCard(
                    modifier = Modifier
                        .height(50.dp)
                        .width(200.dp)
                        .padding(end = 10.dp)
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(), contentAlignment = Center
                    ) {
                        Text(
                            "January 15",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Light
                        )
                    }
                }

                ElevatedCard(
                    modifier = Modifier
                        .height(50.dp)
                        .width(150.dp)
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(), contentAlignment = Center
                    ) {
                        Text(
                            "14:00 - 15:00",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Light
                        )
                    }
                }
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 50.dp)
                .pointerInput(Unit) {
                    detectTapGestures { offset ->
                        viewModel.tappedOutside.value = true
                    }
                },
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            var clicked by remember {
                mutableStateOf(false)
            }
            ElevatedButton(
                onClick = { clicked = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp)
            ) {
                Text("Purchase")
            }
        }
    }
}


@Preview
@Composable
fun PopUpCardPreview() {
    UpwardPopUpCard()
}