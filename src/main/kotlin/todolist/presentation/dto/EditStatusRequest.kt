package todolist.todolist.presentation.dto

import todolist.models.NoteStatus

data class EditStatusRequest(
    val status: NoteStatus
)
