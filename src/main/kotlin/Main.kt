import repositories.ITodoRepository
import repositories.TodoRepository
import services.ITodoService
import services.TodoService
import views.TodoView

fun main() {
    val todoRepository: ITodoRepository = TodoRepository()
    val todoService: ITodoService = TodoService(todoRepository)
    val todoView = TodoView(todoService)

    todoView.showTodos()
}
