package todolist.presentation.cli

import todolist.models.NoteStatus
import todolist.application.auth.AuthService
import todolist.application.auth.UserSession
import todolist.application.todo.TodoService
import todolist.todolist.application.auth.SessionServices

class TodoCLI {

    private var sessionId: String? = null
    private var session: UserSession? = null
    private var todoService: TodoService? = null

    private fun handleRegister() {
        println("-Please enter the following credentials to create an account:")

        var username: String
        var email: String
        var password: String

        while(true){
            print("Username: ")
            username = readlnOrNull() ?: ""

            if(username.isNotBlank()) break
            println("-Username cannot be empty. Please try again.")
        }

        while(true){
            print("E-mail: ")
            email = readlnOrNull() ?: ""

            if(email.isBlank()){
                println("-Email cannot be empty.")
                continue
            } else if (!email.matches(Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"))){
                println("-Invalid email format. Try again.")
                continue
            }
            break
        }

        while(true){
            print("Password: ")
            password = readlnOrNull() ?: ""

            if (password.length < 6) {
                println("-Password must be at least 6 characters long.")
                continue
            }
            break
        }
        if(!AuthService.register(username, email, password)){
            println("\n-User with such email exists already")
            println("-Please repeat the operation\n")

            handleRegister()
        }

        sessionId = AuthService.login(email, password)
        sessionId?.let { session = SessionServices.getSession(it) }


    }


    private fun handleLogin() {
        println("-Please enter the following credentials to log in into an account:")

        var email: String
        var password: String

        while(true){
            print("E-mail: ")
            email = readlnOrNull() ?: ""

            if(email.isBlank()){
                println("-Email cannot be empty.")
                continue
            } else if (!email.matches(Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"))){
                println("-Invalid email format. Try again.")
                continue
            }
            break
        }

        while(true){
            print("Password: ")
            password = readlnOrNull() ?: ""

            if (password.length < 6) {
                println("-Password must be at least 6 characters long.")
                continue
            }
            break
        }

        if(AuthService.login(email, password) == null){
            println("\n-Invalid credentials!")
            println("-Please repeat the operation\n")

            handleLogin()
        }

        sessionId = AuthService.login(email, password)
        sessionId?.let { session = SessionServices.getSession(it) } }

    fun start() {
        println("----------------------------Welcome to TodoList----------------------------")

        while (session == null){
            println("-------------------Please sign in / sign up using the menu below-------------------")
            println("-Choose an action: \n[1] Sign up \n[2] Login \n[0] Exit")

            when(readlnOrNull()){
                "0" -> {
                    println("Exiting program...")
                    Thread.sleep(500)
                    return
                }
                "1" -> handleRegister()
                "2" -> handleLogin()
            }
        }

        mainMenu()
    }

    fun mainMenu(){
        println("-------------------Please chose action below at the menu-------------------")

        todoService = TodoService(
            session = session!!
        )

        while(true){
            println("-------------------------------TodoList Menu!-------------------------------")
            println("-Choose an action: \n[1] Display \n[2] Add \n[3] Edit \n[4] Edit Todo Status \n[5] Delete \n[0] Exit")
            try{
                when(readlnOrNull()){
                    "0" -> {
                        println("-Exiting program...")
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

    private fun displayTodoList(){
            val todoList = todoService?.getAllTodos()
            todoList?.forEach {todo -> println("ID [${todo.id}] ${todo.content} -> ${todo.status}")}
    }

    private fun addTodo(){
        print("Please enter your Todo: ")
        val todoContent = readln()

        todoService?.addTodo(todoContent)
        println("-Adding todo to the list...")
        Thread.sleep(1000)
    }

    private fun editTodo(){
        displayTodoList()

        print("Which todo content would you like to modify? (Enter valid ID): ")
        val id = readln().toIntOrNull() ?: return println("Invalid todo ID!")

        print("Please enter new content for $id todo: ")
        val todoContent = readln()

        todoService?.editTodo(id, todoContent)
    }

    private fun editTodoStatus() {
        displayTodoList()

        print("Which todo status would you like to modify? (Enter valid ID): ")
        val id = readln().toIntOrNull() ?: return println("Invalid todo ID!")

        println("-Please select new status for $id todo: \n[1] COMPLETED \n[2] IN PROGRESS \n[3] TODO")
        when(readlnOrNull()){
            null -> println("-Invalid Status!")
            "1" -> todoService?.editTodoStatus(id, NoteStatus.COMPLETED)
            "2" -> todoService?.editTodoStatus(id, NoteStatus.IN_PROGRESS)
            "3" -> todoService?.editTodoStatus(id, NoteStatus.TODO)
        }
    }

    private fun deleteTodo(){
        displayTodoList()

        print("Which todo would you like to delete? (Enter valid ID): ")
        val id = readln().toIntOrNull() ?: return println("-Invalid todo ID!")

        println("-Deleting Todo...")
        Thread.sleep(1000)
        todoService?.deleteTodo(id)
    }


}