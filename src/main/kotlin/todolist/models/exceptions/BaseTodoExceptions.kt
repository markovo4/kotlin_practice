package todolist.models.exceptions

open class TodoException(message: String) : Exception(message)

class TodoNotFoundException(id: Int) : TodoException("Todo with $id not found.")
class TodoInvalidContentException() : TodoException("Todo cannot be assigned / created with an empty content.")
class TodoListNoContentException() : TodoException("Todo cannot be displayed with an empty content.")