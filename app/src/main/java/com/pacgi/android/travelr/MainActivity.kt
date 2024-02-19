package com.pacgi.android.travelr

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.pacgi.android.travelr.ui.theme.TravelrTheme
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.TextField
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.lifecycle.viewmodel.compose.viewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TravelrTheme {
                val viewModel = viewModel<MainViewModel>()
                val searchText by viewModel.searchText.collectAsState()
                val destinations by viewModel.destinations.collectAsState()
                val isSearching by viewModel.isSearching.collectAsState()

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    WelcomeBack(user = "John Wick")
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        TextField(value = searchText,
                            onValueChange = viewModel::_onSearchTextChange,
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = { Text(text = "Search") })
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Upcoming",
                            style = MaterialTheme.typography.headlineMedium,
                            modifier = Modifier
                                .padding(vertical = 20.dp)
                        )
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                        ) {
                            val notVisited = destinations.filter{it.nextVisit != ""}
                            items(notVisited) { destination ->
                                                Text(
                                                    text = destination.destName,
                                                    style = MaterialTheme.typography.titleLarge,
                                                    modifier = Modifier
                                                        .padding()
                                                )
                        }
                    }
                        Text(
                            text = "Bucket List",
                            style = MaterialTheme.typography.headlineMedium,
                            modifier = Modifier
                                .padding(vertical = 20.dp)
                        )
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                        ) {
                            val notVisited = destinations.filter{it.nextVisit == ""}
                            items(notVisited) { destination ->
                                Text(
                                    text = destination.destName,
                                    style = MaterialTheme.typography.titleLarge,
                                    modifier = Modifier
                                        .padding()
                                )
                            }
                        }
                }
            }
        }
    }
}

@Composable
fun WelcomeBack(user: String) {
    Row {
       Text(text = "Welcome back, $user",
           style = MaterialTheme.typography.titleLarge,
           modifier = Modifier
               .padding(30.dp)
               .fillMaxWidth()
               .wrapContentSize(Alignment.Center)
       )
        Spacer(modifier = Modifier.width(8.dp))

    }
}



@Preview(name = "Light Mode")
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode"
)

@Composable
fun GreetingPreview() {
    TravelrTheme {

        Surface {
            WelcomeBack("Giovanna")
        }
    }
}
}

