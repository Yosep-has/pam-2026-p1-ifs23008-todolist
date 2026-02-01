package services

interface ITodoService {
    fun showTodos()
    fun addTodo(title: String)

    fun removeTodo(id: Int): Boolean
    fun updateTodo(id: Int, title: String, isFinished: Boolean): Boolean

    fun searchTodo(keyword: String)
    fun sortTodo(sortBy: String, isAscending: Boolean)
}
