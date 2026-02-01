package utils

object InputUtil {
    fun input(info: String): String {
        print("$info : ")
        return readlnOrNull() ?: ""
    }
}
