package com.example.bttuan6

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlin.plus

data class Task(val title: String, val description: String)

@Composable
fun TaskListScreen(navController: NavController, tasks: List<Task>, addTask: (Task) -> Unit) {
    Box(modifier = Modifier.fillMaxSize().padding(30.dp)) {
        Column() {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    color = colorResource(R.color.Button),
                    shape = RoundedCornerShape(18.dp),
                    modifier = Modifier
                        .width(45.dp)
                        .height(45.dp)
                        .clickable {  }
                ) {
                    Image(
                        painter = painterResource(R.drawable.baseline_arrow_back_ios_24),
                        contentDescription = null,
                        modifier = Modifier.padding(top = 10.dp, start = 15.dp, end = 10.dp, bottom = 10.dp),
                        colorFilter = ColorFilter.tint(colorResource(R.color.white))
                    )
                }
                Text(
                    text = "List",
                    color = colorResource(R.color.Button),
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.width(240.dp),
                    textAlign = TextAlign.Center
                )
                Surface(
                    color = colorResource(R.color.Button),
                    shape = RoundedCornerShape(18.dp),
                    modifier = Modifier
                        .width(45.dp)
                        .height(45.dp)
                        .clickable { navController.navigate("addTask") }
                ) {
                    Image(
                        painter = painterResource(R.drawable.baseline_add_24),
                        contentDescription = null,
                        modifier = Modifier.padding(top = 10.dp, start = 15.dp, end = 10.dp, bottom = 10.dp),

                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            LazyColumn {
                items(3) { index ->
                    Surface(
                        color = colorResource(R.color.teal_700),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Column(modifier = Modifier.padding(8.dp)) {
                            Text(
                                text = "Complete Android Project ",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.fillMaxWidth()
                            )
                            Text(
                                text = "Finish the UI, integrate API, and write documentation",
                                fontSize = 16.sp,
                                modifier = Modifier.fillMaxWidth()

                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
            LazyColumn {
                items(tasks) { task ->
                    Surface(
                        color = colorResource(R.color.teal_700),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Column(modifier = Modifier.padding(8.dp)) {
                            Text(
                                text = task.title,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.fillMaxWidth()
                            )
                            Text(
                                text = task.description,
                                fontSize = 16.sp,
                                modifier = Modifier.fillMaxWidth()

                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
        }
    }
}

@Composable
fun AddTaskScreen(navController: NavController, addTask: (Task) -> Unit) {
    var taskTitle by remember { mutableStateOf("") }
    var taskDescription by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().padding(20.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Surface(
                color = colorResource(R.color.Button),
                shape = RoundedCornerShape(18.dp),
                modifier = Modifier
                    .width(45.dp)
                    .height(45.dp)
                    .clickable { navController.navigate("taskList") }
            ) {
                Image(
                    painter = painterResource(R.drawable.baseline_arrow_back_ios_24),
                    contentDescription = null,
                    modifier = Modifier.padding(top = 10.dp, start = 15.dp, end = 10.dp, bottom = 10.dp),
                    colorFilter = ColorFilter.tint(colorResource(R.color.white))
                )
            }
            Text(
                text = "AddList",
                color = colorResource(R.color.Button),
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        // TextField for task title
        Text("Task",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(10.dp))
        TextField(
            value = taskTitle,
            onValueChange = { taskTitle = it },
            label = { Text("Do homework") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text("Description",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(10.dp))

        TextField(
            value = taskDescription,
            onValueChange = { taskDescription = it },
            label = { Text("Don't give up") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Button to add task
        Button(
            onClick = {
                if (taskTitle.isNotBlank() && taskDescription.isNotBlank()) {
                    val newTask = Task(taskTitle, taskDescription)
                    addTask(newTask)  // Thêm task vào danh sách
                    Log.d("AddTask", "Task added: $newTask")
                    navController.popBackStack()  // Quay lại màn hình danh sách task
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(colorResource(R.color.Button))
        ) {
            Text("Add Task")
        }
    }
}

@Composable
fun TaskApp() {
    val navController = rememberNavController()
    var tasks by remember { mutableStateOf(listOf<Task>()) }

    // Hàm thêm task vào danh sách
    val addTask: (Task) -> Unit = { newTask ->
        tasks = tasks + newTask  // Cập nhật danh sách task với task mới
    }

    NavHost(navController, startDestination = "taskList") {
        composable("taskList") { TaskListScreen(navController = navController, tasks = tasks, addTask = addTask) }
        composable("addTask") { AddTaskScreen(navController = navController, addTask = addTask) }
    }
}
