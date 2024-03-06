package com.jbrunoo.codinghealth

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jbrunoo.codinghealth.ui.theme.CodingHealthTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CodingHealthTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TodoScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoScreen(todoViewModel: TodoViewModel = viewModel()) {
    var openAlertDialog by remember { mutableStateOf(false) }
    var currentTodoItem by remember { mutableStateOf<TodoItem?>(null) }

    fun clearDialogState() {
        currentTodoItem = null
        openAlertDialog = false
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { Text(text = "todo-list") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { openAlertDialog = true }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "add")
            }
        }
    ) { paddingValues ->
        Content(
            paddingValues = paddingValues,
            todoList = todoViewModel.uiState,
            onCheckedChange = { todoItemUiState, checked ->
                todoViewModel.checkItem(todoItemUiState, checked = checked)
            },
            onDeleteTodoItem = { todoItemUiState ->
                todoViewModel.removeTodoItem(todoItemUiState)
            },
            onUpdateTodoItem = { todoItemUiState ->
                currentTodoItem = todoItemUiState
                openAlertDialog = true
            }
        )
        if (openAlertDialog) {
            TodoItemDialog(
                currentTodoItem = currentTodoItem,
                onDismissRequest = {
                    clearDialogState()
                },
                onConfirmation = { todoItemUiState ->
                    if (currentTodoItem != null) {
                        todoViewModel.undateTodoItem(todoItemUiState, todoItemUiState.body)
                    } else {
                        todoViewModel.addTodoItem(todoItemUiState)
                    }
                    clearDialogState()
                }
            )
        }
    }
}

@Composable
private fun Content(
    paddingValues: PaddingValues,
    todoList: List<TodoItem>,
    onCheckedChange: (TodoItem, Boolean) -> Unit,
    onDeleteTodoItem: (TodoItem) -> Unit,
    onUpdateTodoItem: (TodoItem) -> Unit
) {
    LazyColumn(
        modifier = Modifier.padding(paddingValues),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(
            items = todoList,
            key = { todoItem -> todoItem.id }
        ) { todoItem ->
            TodoItemCard(
                body = todoItem.body,
                checked = todoItem.checked,
                onCheckedChange = { checked ->
                    onCheckedChange(todoItem, checked)
                },
                onDeleteTodoItem = { onDeleteTodoItem(todoItem) },
                onUpdateTodoItem = { onUpdateTodoItem(todoItem) }
            )
        }
    }
}

@Composable
fun TodoItemCard(
    body: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    onDeleteTodoItem: () -> Unit,
    onUpdateTodoItem: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onUpdateTodoItem() }
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(checked = checked, onCheckedChange = { onCheckedChange(!checked) })
            Text(
                text = body,
                textDecoration = if (checked) TextDecoration.LineThrough else TextDecoration.None
            )
            IconButton(onClick = onDeleteTodoItem) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "delete")
            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoItemDialog(
    currentTodoItem: TodoItem? = null,
    onDismissRequest: () -> Unit,
    onConfirmation: (TodoItem) -> Unit,
) {
    var body by remember { mutableStateOf(currentTodoItem?.body ?: "") }
    var isError by remember { mutableStateOf(false) }

    fun validate(body: String) {
        isError = body.isEmpty()
    }

    AlertDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            TextButton(onClick = {
                if (body.isNotEmpty()) {
                    if (currentTodoItem != null) {
                        onConfirmation(
                            TodoItem(
                                id = currentTodoItem.id,
                                initialBody = body,
                                initialChecked = currentTodoItem.checked
                            )
                        )
                    } else {
                        onConfirmation(TodoItem(initialBody = body))
                    }
                } else isError = true
            }) {
                Text(text = "저장")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text(text = "취소")
            }
        },
        title = { Text(text = "todo-list") },
        text = {
            Column {
                OutlinedTextField(
                    value = body, onValueChange = { body = it },
                    label = { Text(text = "to-do") },
                    singleLine = true,
                    isError = isError,
                    supportingText = {
                        if (isError)
                            Text(
                                text = "text is empty",
                                color = MaterialTheme.colorScheme.error
                            )
                    },
                    keyboardActions = KeyboardActions { validate(body) }
                )
            }
        }
    )
}
