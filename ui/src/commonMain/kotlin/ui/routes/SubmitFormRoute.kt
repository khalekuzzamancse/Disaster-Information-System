package ui.routes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ui.form.BaseDescriptionFormManager
import ui.form.CompactForm

@Composable
fun SubmitFormRoutes(
    formStateManager: BaseDescriptionFormManager,
    modifier: Modifier=Modifier,
    onSubmitClick: () -> Unit,
) {
    Column(modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        CompactForm(
            modifier = Modifier.padding(16.dp).fillMaxSize(),
            data = formStateManager.fromState.collectAsState().value,
            onEvent = formStateManager::onFormEvent
        )
        Spacer(Modifier.height(8.dp))
        Button(onClick = onSubmitClick){
            Icon(Icons.Default.Done, null)
            Spacer(Modifier.width(8.dp))
            Text(
                text = "Submit",
                modifier = Modifier.padding(start = 8.dp, end = 8.dp)
            )
        }
    }


}