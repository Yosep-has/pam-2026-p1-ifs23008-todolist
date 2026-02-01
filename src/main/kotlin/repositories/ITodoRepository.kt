package repositories

import entities.Todo

interface ITodoRepository {
    fun getAllTodos(): List<Todo>
    fun addTodo(newTodo: Todo)
    fun removeTodo(id: Int): Boolean
    fun updateTodo(id: Int, title: String?, isFinished: Boolean?): Boolean
    fun searchTodos(query: String): List<Todo>

    // Diperbarui untuk mendukung parameter sorting (Ascending/Descending)
    fun sortTodos(sortBy: String, isAscending: Boolean)

}