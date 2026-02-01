package services

import entities.Todo
import repositories.ITodoRepository

class TodoService(private val todoRepository: ITodoRepository) : ITodoService {

    override fun showTodos() {
        val todos = todoRepository.getAllTodos()

        println("Daftar Todo:")
        if (todos.isEmpty()) {
            println("- Data todo belum tersedia!")
        } else {
            for (todo in todos) {
                val status = if (todo.isFinished) "Selesai" else "Belum Selesai"
                println("${todo.id} | ${todo.title} | $status")
            }
        }
    }

    override fun addTodo(title: String) {
        val newTodo = Todo(title = title)
        todoRepository.addTodo(newTodo)
    }

    override fun removeTodo(id: Int): Boolean {
        return todoRepository.removeTodo(id)
    }

    override fun updateTodo(id: Int, title: String, isFinished: Boolean): Boolean {
        // repo menerima nullable, tapi service mengirim value pasti
        return todoRepository.updateTodo(id, title, isFinished)
    }

    override fun searchTodo(keyword: String) {
        val matchTodos = todoRepository.searchTodos(keyword)

        if (matchTodos.isEmpty()) {
            println("- Data todo tidak ditemukan!")
            println()
        } else {
            for (todo in matchTodos) {
                val status = if (todo.isFinished) "Selesai" else "Belum Selesai"
                println("${todo.id} | ${todo.title} | $status")
            }
            println()
        }
    }

    override fun sortTodo(sortBy: String, isAscending: Boolean) {
        todoRepository.sortTodos(sortBy, isAscending)
    }
}
