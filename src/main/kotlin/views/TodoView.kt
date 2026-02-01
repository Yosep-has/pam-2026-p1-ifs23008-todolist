package views

import services.ITodoService
import utils.InputUtil

class TodoView(private val todoService: ITodoService) {

    fun showTodos() {
        while (true) {
            todoService.showTodos()

            println("Menu:")
            println("1. Tambah")
            println("2. Ubah")
            println("3. Cari")
            println("4. Urutkan")
            println("5. Hapus")
            println("x. Keluar")

            val input = InputUtil.input("Pilih")
            val stop = when (input) {
                "1" -> { addTodo(); false }
                "2" -> { updateTodo(); false }
                "3" -> { searchTodo(); false }
                "4" -> { sortTodo(); false }
                "5" -> { removeTodo(); false }
                "x" -> true
                else -> {
                    println("[!] Pilihan tidak dimengerti.")
                    false
                }
            }
            if (stop) break
        }
    }

    /**
     * Menambah Todo
     * - Start: Langsung (tanpa jarak)
     * - Cancel: Silent + Newline (agar "Daftar Todo" turun)
     */
    fun addTodo() {
        println("[Menambah Todo]")
        val title = InputUtil.input("Judul (x Jika Batal)")

        if (title == "x") {
            println()
            return
        }

        todoService.addTodo(title)
        println()
    }

    /**
     * Menghapus Todo
     * - Start: Langsung (tanpa jarak)
     * - Cancel: Silent + Newline
     * - Error: Menggunakan tanda titik di akhir kalimat.
     */
    fun removeTodo() {
        println("[Menghapus Todo]")
        val strIdTodo = InputUtil.input("[ID Todo] yang dihapus (x Jika Batal)")

        if (strIdTodo == "x") {
            println() // Force newline
            return
        }

        val idTodo = strIdTodo.toIntOrNull()
        if (idTodo == null) {
            println("[!] ID harus berupa angka.")
        } else {
            val success = todoService.removeTodo(idTodo)
            if (!success) {
                // Perbaikan: Menambah titik (.) di akhir sesuai output TC
                println("[!] Gagal menghapus todo dengan ID: $idTodo.")
                println()
            }else{
                println()
            }
        }

    }

    /**
     * Mengubah Todo
     * - Start: Ada Newline di awal
     * - Cancel: Verbose (Pesan teks) + Newline
     */
    /**
     * Mengubah Todo
     * - Start: Ada Newline di awal
     * - Input: ID -> Judul -> Status (y/n)
     * - Pesan Error: "Gagal memperbarui..." (bukan menemukan) + titik di akhir.
     */
    fun updateTodo() {
        println() // Jarak baris awal
        println("[Memperbarui Todo]")

        // 1. Input ID
        val strId = InputUtil.input("[ID Todo] yang diubah (x Jika Batal)")

        if (strId == "x") {
            println("[x] Pembaruan todo dibatalkan.")
            println()
            return
        }

        val id = strId.toIntOrNull()
        if (id == null) {
            println("[!] ID harus berupa angka.")
            println()
            return
        }

        // 2. Input Judul Baru
        val newTitle = InputUtil.input("Judul Baru (x Jika Batal)")


        if (newTitle == "x") {
            println("[x] Pembaruan todo dibatalkan.")
            println()
            return
        }

        // 3. Input Status (PERBAIKAN UTAMA: Meminta status y/n)
        val statusStr = InputUtil.input("Apakah todo sudah selesai? (y/n)")
        // Konversi y menjadi true, selain itu false (n = false)
        val isFinished = statusStr.equals("y", ignoreCase = true)

        // 4. Panggil Service
        val success = todoService.updateTodo(id, newTitle, isFinished)

        if (!success) {
            // PERBAIKAN PESAN: "Gagal memperbarui..." dan ada titik di akhir
            println("[!] Gagal memperbarui todo dengan ID: $id.")
            println()
        } else {
            println() // Newline saat sukses

        }

    }

    /**
     * Mencari Todo
     * - Start: Ada Newline di awal
     * - Cancel: Verbose + Newline
     */
    fun searchTodo() {
        println() // Jarak baris awal
        println("[Mencari Todo]")
        val query = InputUtil.input("Kata kunci (x Jika Batal)")

        if (query == "x") {
            println("[x] Pencarian todo dibatalkan.")
            println()
            return
        }

        if (query.isNotEmpty()) {
            todoService.searchTodo(query)
            // Service menghandle pesan jika data tidak ditemukan
        } else {
            println("[!] Kata kunci tidak boleh kosong.")
            println()
        }
    }

    /**
     * Mengurutkan Todo
     * - Start: Ada Newline di awal
     * - Cancel: Verbose + Newline
     * - Fitur: Input tambahan untuk Ascending/Descending
     */
    fun sortTodo() {
        println() // Jarak baris awal
        println("[Mengurutkan Todo]")

        // Input kriteria
        val input = InputUtil.input("Urutkan berdasarkan (id/title/finished) (x Jika Batal)")
        //println()
        if (input == "x") {
            println("[x] Pengurutan todo dibatalkan.")
            println()
            return
        }

        // Input arah urutan (Ascending/Descending)
        val ascInput = InputUtil.input("Urutkan secara ascending? (y/n)")
        println()
        val isAscending = ascInput.equals("y", ignoreCase = true)

        todoService.sortTodo(input, isAscending)
    }
}