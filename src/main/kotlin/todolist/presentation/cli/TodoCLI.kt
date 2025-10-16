package todolist.presentation.cli

import todolist.models.NoteStatus
import todolist.application.todo.TodoService

class TodoCLI (
    private val todoService: TodoService = TodoService()
){

    private fun displayTodoList(){
            val todoList = todoService.getAllTodos()
            todoList.forEach {todo -> println("ID [${todo.id}] ${todo.todo} -> ${todo.status}")}
    }


    private fun addTodo(){
        print("Please enter your Todo: ")
        val todoContent = readln()

        todoService.addTodo(todoContent)
        println("Adding todo to the list...")
        Thread.sleep(1000)
    }

    private fun editTodo(){
        displayTodoList()

        print("Which todo content would you like to modify? (Enter valid ID): ")
        val id = readln().toIntOrNull() ?: return println("Invalid todo ID!")

        print("Please enter new content for $id todo: ")
        val todoContent = readln()

        todoService.editTodo(id, todoContent)
    }

    private fun editTodoStatus() {
        displayTodoList()

        print("Which todo status would you like to modify? (Enter valid ID): ")
        val id = readln().toIntOrNull() ?: return println("Invalid todo ID!")

        println("Please select new status for $id todo: \n[1] COMPLETED \n[2] IN PROGRESS \n[3] TODO")
        when(readlnOrNull()){
            null -> println("Invalid Status!")
            "1" -> todoService.editTodoStatus(id, NoteStatus.COMPLETED)
            "2" -> todoService.editTodoStatus(id, NoteStatus.IN_PROGRESS)
            "3" -> todoService.editTodoStatus(id, NoteStatus.TODO)
        }
    }

    private fun deleteTodo(){
        displayTodoList()

        print("Which todo would you like to delete? (Enter valid ID): ")
        val id = readln().toIntOrNull() ?: return println("Invalid todo ID!")

        println("Deleting Todo...")
        Thread.sleep(1000)
        todoService.deleteTodo(id)
    }

    fun start(){
        println("----------------------------Welcome to TodoList!----------------------------")
        println("-------------------Please chose action below at the menu!-------------------")


        while(true){
            println("-------------------------------TodoList Menu!-------------------------------")
            println("Choose an action: \n[1] Display \n[2] Add \n[3] Edit \n[4] Edit Todo Status \n[5] Delete \n[0] Exit")
            try{
                when(readlnOrNull()){
                    "0" -> {
                        println("Exiting program...")
                        Thread.sleep(500)
                        break
                    }
                    "1" -> displayTodoList()
                    "2" -> addTodo()
                    "3" -> editTodo()
                    "4" -> editTodoStatus()
                    "5" -> deleteTodo()
                }
            } catch (e: Exception){
                println("${e.message}")
                println("Returning to menu...")
                Thread.sleep(1000)
                continue
            }

        }
    }
}