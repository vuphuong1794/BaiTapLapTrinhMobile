package com.example.baitapmobile.tuan4.homework.baitap1

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.State

class LibraryViewModel : ViewModel() {
    private val books = listOf(
        Book(1, "Sách A", "Tác giả A"),
        Book(2, "Sách B", "Tác giả B"),
        Book(3, "Sách C", "Tác giả C"),
        Book(4, "Sách D", "Tác giả D")
    )

    private val library = Library(books)
    private val _currentStudent = mutableStateOf<Student?>(null)
    val showAvailable = mutableStateOf(false)
    private val _borrowStateTrigger = mutableStateOf(0) // Thêm trigger để refresh UI

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
        return if (showAvailable.value) {
            library.getAvailableBooks()
        } else {
            library.getBorrowedBooksBy(student)
        }
    }

    fun toggleBorrow(book: Book) {
        val student = _currentStudent.value ?: return
        val isCurrentlyBorrowed = library.getBorrowedBooksBy(student).any { it.id == book.id }
        if (isCurrentlyBorrowed) {
            library.returnBook(book.id, student)
        } else {
            library.borrowBook(book.id, student)
        }
        _borrowStateTrigger.value++ // Trigger re-compose sau khi thay đổi trạng thái
    }

    fun isBookBorrowed(book: Book): Boolean {
        val student = _currentStudent.value ?: return false
        _borrowStateTrigger.value // Đọc để trigger re-compose
        return library.getBorrowedBooksBy(student).any { it.id == book.id }
    }


}