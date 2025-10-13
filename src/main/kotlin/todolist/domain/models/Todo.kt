package todolist.domain.models

class Todo(
    private var todo: String,
    private var status: NoteStatus = NoteStatus.TODO,
    id: Int = nextId()
) {
    fun edit(newTodo: String) {
        println("Edit note: $todo -> $newTodo")
        todo = newTodo
    }

    fun changeStatus(newStatus: NoteStatus) {
        println("Edit status: $todo -> $newStatus")
        status = newStatus
    }

    var id: Int = id
        private set

    fun displayTodo() {
        println("ID [$id] $todo -> $status")
    }

    companion object {
        private var lastId = 0;
        private fun nextId(): Int {
            lastId++
            return lastId
        }
    }
}