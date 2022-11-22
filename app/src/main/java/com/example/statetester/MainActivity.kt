package com.example.statetester

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import com.example.statetester.ui.theme.StateTesterTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StateTesterTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    HelloScreen()
                }
            }
        }
    }
}


@Composable
fun HelloScreen() {
    val names = remember { mutableStateListOf<String>()}
    var newName by rememberSaveable { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize()) {

        Text(text = "Captured Names:",
            style = MaterialTheme.typography.h5)
        Spacer(modifier = Modifier.height(20.dp))
        names.forEach {
            name -> Text(text = name, color = Color.White, fontSize = 30.sp)
        }
        HelloContent(
            newName = newName,
            onNameChange = {
                // when name change event passed back, update the state
                newName = it
            },
            onNameAdd = {
                // when name add event passed back, add to the list
                names.add(newName)
                // clear the state of new name
                newName = ""
            }
        )
    }

}

@Composable
fun HelloContent(newName: String, onNameChange: (String) -> Unit, onNameAdd:() -> Unit) {

    val state = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.DarkGray
    ) {
        Scaffold(
            Modifier.fillMaxSize(),
            scaffoldState = state,
            floatingActionButton = {
                FloatingActionButton(onClick = { scope.launch { state.drawerState.open() } }) {
                    /* FAB content */
                    Text(text = "+", fontSize = 30.sp)
                }
            },
            drawerContent = {

                StateTesterTheme {

                    Text(text = "Start Capture Information",
                        style = MaterialTheme.typography.h3)


                    OutlinedTextField(
                        value = newName,
                        // pass back text change event
                        onValueChange = onNameChange,
                        label = {Text(text = "Enter a new name:")}
                    )
                    Button(
                        // pass back button add event
                        onClick = onNameAdd
                    ){
                        Text(text = "Add")
                    }
                }

            }
        ) {
            // Scaffold body
        }
    }

}


