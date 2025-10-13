package todolist.domain.models;

class TodoList(
    private val todos: MutableList<Todo> = mutableListOf()
) {

    fun editTodoText(id: Int, newTodoText: String) {
        val todo = todos.find { todo -> todo.id == id }

        if(todo != null){
            todo.edit(newTodoText)
            println("Todo $id content was edited successfully")
        } else {
            println("Todo $id was not found")
        }
    }

    fun addTodo(todoContent: String) {
        if(todoContent.trim().isEmpty()){
            println("Error: Invalid input!")

            println("Returning to the menu...")
            Thread.sleep(1000)

        } else{
            val todo = Todo(todoContent)
            todos.add(todo)
        }
    }

    fun editTodoStatus(id: Int, newStatus: NoteStatus) {
        val todo = todos.find {todo -> todo.id == id }

        if(todo != null){
            todo.changeStatus(newStatus)
            println("Todo $id status was edited successfully")
        } else {
            println("Todo $id was not found")
        }
    }

    fun deleteTodo(id: Int) {
        todos.removeIf { todo -> todo.id == id }
    }

    fun displayTodos() {

        if(todos.isNotEmpty()) {
            println("---------------------------------Todo List!---------------------------------")
            todos.forEach { todo -> todo.displayTodo() }
        } else{
            println("Please add Todos to display!")
        }
    }
}