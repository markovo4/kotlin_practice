package todolist.models

import todolist.domain.models.NoteStatus

class Todo(
    todo: String,
    status: NoteStatus = NoteStatus.TODO,
    id: Int = nextId()
) {
    fun edit(newTodo: String) {
        todo = newTodo
    }

    fun changeStatus(newStatus: NoteStatus) {
        status = newStatus
    }

    var todo: String = todo
        private set

    var status: NoteStatus = status
        private set

    var id: Int = id
        private set

    companion object {
        private var lastId = 0
        private fun nextId(): Int {
            lastId++
            return lastId
        }
    }
}