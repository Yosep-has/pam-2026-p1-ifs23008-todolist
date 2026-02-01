package entities

data class Todo
    (   val id: Int = generateId(),
        var title: String = "",
        var isFinished: Boolean = false
) {
    override fun toString(): String {
        val status = if (isFinished) "Selesai" else "Belum Selesai"
        return "$id | $title | $status"
    }

    companion object {
        private var counter = 0;
        private fun generateId(): Int {
            counter++
            return counter
        }
        fun getTotalCreated(): Int = counter
    }
}



