package com.jbrunoo.codinghealth.ui.theme.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
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
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jbrunoo.codinghealth.local.TodoItem
import com.jbrunoo.codinghealth.vm.TodoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoScreen(todoViewModel: TodoViewModel) {
    val todoItemList = todoViewModel.todoItemList.collectAsStateWithLifecycle()
    var currentTodoItemId by remember { mutableLongStateOf(-1) }
    var currentTodoItemBody by remember { mutableStateOf("") }

    var openTodoDialog by remember { mutableStateOf(false) }
    var openDeleteDialog by remember { mutableStateOf(false) }

    fun clearTodoDialogState() {
        currentTodoItemId = -1
        currentTodoItemBody = ""
        openTodoDialog = false
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { Text(text = "todo-list") })
        },
        floatingActionButton = {
            Column {
                FloatingActionButton(onClick = { openTodoDialog = true }) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "add-icon")
                }
            }
        }
    ) { paddingValues ->
        Content(
            paddingValues = paddingValues,
            todoItemList = todoItemList.value,
            onCheckedChange = { id, checked ->
                todoViewModel.updateTodo(TodoItem(id = id, checked = checked))
            },
            onDeleteTodoItem = { todoItem ->
                todoViewModel.deleteTodo(todoItem)
            },
            onCheckCurrentTodoItem = { todoItem ->
                // mutableStateOf<TodoItem?>(null)로 받으니 null check 문제 있어서 id와 body로 구현함..
                currentTodoItemId = todoItem.id
                currentTodoItemBody = todoItem.body
                openTodoDialog = true
            }
        )
        if (openTodoDialog) {
            TodoItemDialog(
                value = currentTodoItemBody,
                onDismissRequest = {
                    clearTodoDialogState()
                },
                onConfirmation = { newBody ->
                    if (currentTodoItemId == -1L) todoViewModel.addTodo(TodoItem(body = newBody))
                    else todoViewModel.updateTodo(TodoItem(id = currentTodoItemId, body = newBody))
                    clearTodoDialogState()
                }
            )
        }
    }
}


@Composable
private fun Content(
    paddingValues: PaddingValues,
    todoItemList: List<TodoItem>,
    onCheckedChange: (Long, Boolean) -> Unit,
    onDeleteTodoItem: (TodoItem) -> Unit,
    onCheckCurrentTodoItem: (TodoItem) -> Unit
) {
    LazyColumn(
        modifier = Modifier.padding(paddingValues),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(
            items = todoItemList
        ) { todoItem ->
            TodoItemCard(
                body = todoItem.body,
                checked = todoItem.checked,
                onCheckedChange = { checked ->
                    onCheckedChange(todoItem.id, checked)
                },
                onDeleteTodoItem = { onDeleteTodoItem(todoItem) },
                onCheckCurrentTodoItem = { onCheckCurrentTodoItem(todoItem) }
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun TodoItemCard(
    body: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    onDeleteTodoItem: () -> Unit,
    onCheckCurrentTodoItem: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onCheckCurrentTodoItem() }
    )
    {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(checked = checked, onCheckedChange = { onCheckedChange(!checked) })
            Text(
                text = body,
                modifier = Modifier.weight(0.8f),
                textDecoration = if (checked) TextDecoration.LineThrough else TextDecoration.None,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2
            )
            IconButton(onClick = onDeleteTodoItem, modifier = Modifier.weight(0.2f)) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "delete")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TodoItemDialog(
    value: String = "",
    onDismissRequest: () -> Unit,
    onConfirmation: (String) -> Unit,
) {
    var body by remember { mutableStateOf(value) }
    var isError by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            TextButton(onClick = {
                if (body.isNotEmpty()) onConfirmation(body)
                else isError = !isError
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
                    trailingIcon = {
                        if (isError) Icon(
                            imageVector = Icons.Default.Create,
                            contentDescription = "create-icon"
                        )
                    },
                    supportingText = {
                        if (isError) Text(
                            text = "text is empty",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                )
            }
        }
    )
}

