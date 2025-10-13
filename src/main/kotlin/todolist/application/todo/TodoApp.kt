package todolist.application.todo

import todolist.domain.models.NoteStatus
import todolist.domain.models.TodoList

class TodoApp (
    private val todoList: TodoList = TodoList()
){

    private fun displayTodos() {
        todoList.displayTodos()
    }

    private fun addTodo(){
        print("Please enter your Todo: ")
        val todoContent = readln()

        println("Adding todo to the list...")
        Thread.sleep(1000)
        todoList.addTodo(todoContent)
    }

    private fun editTodo(){
        print("Which todo content would you like to modify? (Enter valid ID): ")
        val todoId = readln().toIntOrNull()

        if(todoId == null){
            println("Invalid todo ID!")
            return
        }

        print("Please enter new content for $todoId todo: ")
        val todoContent = readln()

        todoList.editTodoText(todoId, todoContent)
    }

    private fun editTodoStatus() {
        print("Which todo status would you like to modify? (Enter valid ID): ")
        val todoId = readln().toIntOrNull()

        if(todoId == null){
            println("Invalid todo ID!")
            return
        }

        println("Please select new status for $todoId todo: \n[1] COMPLETED \n[2] IN PROGRESS \n[3] TODO")
        when(readlnOrNull()){
            null -> println("Invalid Status!")
            "1" -> todoList.editTodoStatus(todoId, NoteStatus.COMPLETED)
            "2" -> todoList.editTodoStatus(todoId, NoteStatus.IN_PROGRESS)
            "3" -> todoList.editTodoStatus(todoId, NoteStatus.TODO)
        }
    }

    private fun deleteTodo(){
        print("Which todo would you like to delete? (Enter valid ID): ")
        val todoId = readln().toIntOrNull()

        if(todoId == null){
            println("Invalid todo ID!")
            return
        }

        println("Deleting Todo...")
        Thread.sleep(1000)
        todoList.deleteTodo(todoId)
    }

    fun start(){
        println("----------------------------Welcome to TodoList!----------------------------")
        println("-------------------Please chose action below at the menu!-------------------")
        while(true){
            println("-------------------------------TodoList Menu!-------------------------------")
            println("Choose an action: \n[1] Display \n[2] Add \n[3] Edit \n[4] Edit Todo Status \n[5] Delete \n[0] Exit")
            when(readlnOrNull()){
                "0" -> {
                    println("Exiting program...")
                    Thread.sleep(500)
                    break
                }
                "1" -> displayTodos()
                "2" -> addTodo()
                "3" -> editTodo()
                "4" -> editTodoStatus()
                "5" -> deleteTodo()
            }
        }
    }
}