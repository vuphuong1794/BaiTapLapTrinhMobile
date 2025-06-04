
// ViewModel cập nhật
package com.example.baitapmobile.tuan4.homework.baitap1

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.State

class LibraryViewModel : ViewModel() {
    private val initialBooks = listOf(
        Book(1, "Sách A", "Tác giả A"),
        Book(2, "Sách B", "Tác giả B"),
        Book(3, "Sách C", "Tác giả C"),
        Book(4, "Sách D", "Tác giả D")
    )

    private val library = Library(initialBooks)
    private val _currentStudent = mutableStateOf<Student?>(null)
    val showAvailable = mutableStateOf(false)
    private val _borrowStateTrigger = mutableStateOf(0)
    private val _bookListTrigger = mutableStateOf(0) // Trigger cho danh sách sách

    val currentStudent: State<Student?> get() = _currentStudent

    fun updateStudent(name: String) {
        if (name.isNotBlank()) {
            _currentStudent.value = Student(name.trim())
        } else {
            _currentStudent.value = null
        }
    }

    fun toggleShowAvailable() {
        showAvailable.value = !showAvailable.value
    }

    fun isShowingAvailable(): Boolean = showAvailable.value

    fun getDisplayBooks(): List<Book> {
        val student = _currentStudent.value ?: return emptyList()
        _borrowStateTrigger.value // Đọc để trigger re-compose
        _bookListTrigger.value // Đọc để trigger re-compose khi có thay đổi danh sách
        return if (showAvailable.value) {
            library.getAvailableBooks()
        } else {
            library.getBorrowedBooksBy(student)
        }
    }

    fun getAllBooks(): List<Book> {
        _bookListTrigger.value // Đọc để trigger re-compose
        return library.getAllBooks()
    }

    fun toggleBorrow(book: Book) {
        val student = _currentStudent.value ?: return
        val isCurrentlyBorrowed = library.getBorrowedBooksBy(student).any { it.id == book.id }
        if (isCurrentlyBorrowed) {
            library.returnBook(book.id, student)
        } else {
            library.borrowBook(book.id, student)
        }
        _borrowStateTrigger.value++
    }

    fun isBookBorrowed(book: Book): Boolean {
        val student = _currentStudent.value ?: return false
        _borrowStateTrigger.value
        return library.getBorrowedBooksBy(student).any { it.id == book.id }
    }

    // Phương thức thêm sách mới
    fun addBook(title: String, author: String): Boolean {
        if (title.isBlank() || author.isBlank()) {
            return false
        }

        // Tự động generate ID mới
        val newId = (library.getAllBooks().maxOfOrNull { it.id } ?: 0) + 1
        val success = library.addBook(newId, title.trim(), author.trim())

        if (success) {
            _bookListTrigger.value++ // Trigger re-compose
        }

        return success
    }

    // Phương thức xóa sách
    fun removeBook(bookId: Int): Boolean {
        val success = library.removeBook(bookId)
        if (success) {
            _bookListTrigger.value++
            _borrowStateTrigger.value++ // Cũng cần update borrow state
        }
        return success
    }
}