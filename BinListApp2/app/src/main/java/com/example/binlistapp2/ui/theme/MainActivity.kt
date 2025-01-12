package com.example.binlistapp2.ui.theme

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.binlistapp2.viewmodel.MainViewModel
import com.example.binlistapp2.viewmodel.MainViewModelFactory
import com.example.binlistapp2.data.BinInfoEntity
import android.content.Intent
import android.net.Uri

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BinListApp2Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen(viewModel: MainViewModel = viewModel(factory = MainViewModelFactory(BinRepositoryImpl(AppDatabase.getDatabase(applicationContext)))) {
    var bin by remember { mutableStateOf("") }
    var isHistoryVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.loadHistory()
    }

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            value = bin,
            onValueChange = { bin = it },
            label = { Text("Enter BIN") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { viewModel.fetchBinInfo(bin) }) {
            Text("Fetch Info")
        }
        Spacer(modifier = Modifier.height(8.dp))

        when {
            viewModel.isLoading.value -> CircularProgressIndicator()
            viewModel.errorMessage.value != null -> Text(text = "Error: ${viewModel.errorMessage.value}")
            viewModel.binInfo.value != null -> DisplayBinInfo(viewModel.binInfo.value!!)
            else -> Text("Enter a BIN to fetch information.")
        }

        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { isHistoryVisible = !isHistoryVisible }) {
            Text("Show History")
        }
        if (isHistoryVisible) {
            HistoryScreen(viewModel.history.collectAsState().value)
        }
    }
}

    @Composable
@Composable
fun DisplayBinInfo(binInfo: BinInfoEntity) {
    Column(modifier = Modifier.padding(8.dp)) {
        Text(text = "Scheme: ${binInfo.scheme}", style = MaterialTheme.typography.bodyMedium)
        Divider(modifier = Modifier.padding(vertical = 4.dp))
        Text(text = "Type: ${binInfo.type}", style = MaterialTheme.typography.bodyMedium)
        Divider(modifier = Modifier.padding(vertical = 4.dp))
        Text(text = "Brand: ${binInfo.brand}", style = MaterialTheme.typography.bodyMedium)
        Divider(modifier = Modifier.padding(vertical = 4.dp))
        Text(text = "Country: ${binInfo.countryName}", style = MaterialTheme.typography.bodyMedium)
        Divider(modifier = Modifier.padding(vertical = 4.dp))
        Text(text = "Bank: ${binInfo.bankName}", style = MaterialTheme.typography.bodyMedium)
        Divider(modifier = Modifier.padding(vertical = 4.dp))
        Text(text = "URL: ${binInfo.bankUrl}", style = MaterialTheme.typography.bodyMedium)
        Divider(modifier = Modifier.padding(vertical = 4.dp))
        Text(text = "Phone: ${binInfo.bankPhone}", style = MaterialTheme.typography.bodyMedium)
        Divider(modifier = Modifier.padding(vertical = 4.dp))
        Text(text = "City: ${binInfo.bankCity}", style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
@Composable
fun HistoryScreen(history: List<BinInfoEntity>) {
    Column {
        Text(text = "History", style = MaterialTheme.typography.h6)
        Spacer(modifier = Modifier.height(8.dp))
        if (history.isEmpty()) {
            Text(text = "No history available.", style = MaterialTheme.typography.bodyMedium)
        } else {
            for (item in history) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = "BIN: ${item.bin}", style = MaterialTheme.typography.bodyMedium)
                        Divider(modifier = Modifier.padding(vertical = 4.dp))
                        Text(text = "Scheme: ${item.scheme}", style = MaterialTheme.typography.bodyMedium)
                        Divider(modifier = Modifier.padding(vertical = 4.dp))
                        Text(text = "Type: ${item.type}", style = MaterialTheme.typography.bodyMedium)
                        Divider(modifier = Modifier.padding(vertical = 4.dp))
                        Text(text = "Brand: ${item.brand}", style = MaterialTheme.typography.bodyMedium)
                        Divider(modifier = Modifier.padding(vertical = 4.dp))
                        Text(text = "Country: ${item.countryName}", style = MaterialTheme.typography.bodyMedium)
                        Divider(modifier = Modifier.padding(vertical = 4.dp))
                        Text(text = "Bank: ${item.bankName}", style = MaterialTheme.typography.bodyMedium)
                        Divider(modifier = Modifier.padding(vertical = 4.dp))
                        Text(text = "URL: ${item.bankUrl}", style = MaterialTheme.typography.bodyMedium)
                        Divider(modifier = Modifier.padding(vertical = 4.dp))
                        Text(text = "Phone: ${item.bankPhone}", style = MaterialTheme.typography.bodyMedium)
                        Divider(modifier = Modifier.padding(vertical = 4.dp))
                        Text(text = "City: ${item.bankCity}", style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
        }
    }
}

@Composable
fun DisplayBinInfo(binInfo: BinInfoEntity) {
    Column(modifier = Modifier.padding(8.dp)) {
        ClickableText(text = "Scheme: ${binInfo.scheme}", onClick = {})
        Divider(modifier = Modifier.padding(vertical = 4.dp))
        ClickableText(text = "Type: ${binInfo.type}", onClick = {})
        Divider(modifier = Modifier.padding(vertical = 4.dp))
        ClickableText(text = "Brand: ${binInfo.brand}", onClick = {})
        Divider(modifier = Modifier.padding(vertical = 4.dp))
        ClickableText(text = "Country: ${binInfo.countryName}", onClick = {})
        Divider(modifier = Modifier.padding(vertical = 4.dp))
        ClickableText(text = "Bank: ${binInfo.bankName}", onClick = {})
        Divider(modifier = Modifier.padding(vertical = 4.dp))
        ClickableText(text = "URL: ${binInfo.bankUrl}") {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(binInfo.bankUrl))
            startActivity(intent)
        }
        Divider(modifier = Modifier.padding(vertical = 4.dp))
        ClickableText(text = "Phone: ${binInfo.bankPhone}") {
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${binInfo.bankPhone}"))
            startActivity(intent)
        }
        Divider(modifier = Modifier.padding(vertical = 4.dp))
        ClickableText(text = "City: ${binInfo.bankCity}", onClick = {})
    }
}

@Composable
fun ClickableText(text: String, onClick: () -> Unit) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyMedium,
        modifier = Modifier.clickable(onClick = onClick)
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BinListApp2Theme {
        MainScreen()
    }
}