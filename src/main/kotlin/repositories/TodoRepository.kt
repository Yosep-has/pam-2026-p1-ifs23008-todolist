package repositories

import entities.Todo

// Menambahkan implementasi interface ITodoRepository
class TodoRepository : ITodoRepository {

    // Menggunakan ArrayList untuk menyimpan data
    private val data = ArrayList<Todo>()



    override fun getAllTodos(): List<Todo> {
        return data
    }

    override fun addTodo(newTodo: Todo) {
        data.add(newTodo)
    }

    override fun updateTodo(id: Int, title: String?, isFinished: Boolean?): Boolean {
        val todo = data.find { it.id == id } ?: return false

        title?.let { todo.title = it }
        isFinished?.let { todo.isFinished = it }

        return true
    }

    override fun sortTodos(sortBy: String, isAscending: Boolean) {
        val category = sortBy.trim().lowercase()

        // 1. Logika Khusus untuk 'finished' Descending (n)
        if (category == "finished" && !isAscending) {
            // STEP A: Urutkan dulu agar yang 'Selesai' naik ke paling atas
            // Kita pakai stable sort bawaan Kotlin agar urutan sisanya tidak berubah dulu
            data.sortWith(compareByDescending<Todo> { it.isFinished })

            // STEP B: Pisahkan yang Belum Selesai, balikkan urutannya, lalu gabung lagi
            val finishedTasks = data.filter { it.isFinished }
            val unFinishedTasks = data.filter { !it.isFinished }.reversed()

            data.clear()
            data.addAll(finishedTasks)
            data.addAll(unFinishedTasks)

            return // Selesai, jangan jalankan kriteria di bawah
        }

        val comparator = when (sortBy.trim().lowercase()) {
            // Tambahkan .thenBy { it.id } agar jika title/finished sama, ID yang menentukan
            "title" -> compareBy<Todo> { it.title }.thenBy { it.id }
            "finished" -> compareBy<Todo> { it.isFinished }.thenBy { it.id }
            "id" -> compareBy<Todo> { it.id }
            else -> compareBy<Todo> { it.id } // Default ke ID jika tidak dikenal
        }

        if (isAscending) {
            data.sortWith(comparator)
        } else {
            // Ini akan membalikkan kriteria utama DAN kriteria cadangan (ID)
            data.sortWith(comparator.reversed())
        }
    }

    override fun removeTodo(id: Int): Boolean {
        val targetTodo = data.find { it.id == id } ?: return false
        return data.remove(targetTodo)
    }

    override fun searchTodos(query: String): List<Todo> {
        return data.filter { it.title.contains(query, ignoreCase = true) }
    }
}