package com.example.baitapmobile.tuan4.homework.baitap1

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.mutableStateListOf

class LibraryViewModel : ViewModel() {
    private val library = Library(
        listOf(
            Book(1, "Dế Mèn Phiêu Lưu Ký", "Tô Hoài"),
            Book(2, "Tuổi Thơ Dữ Dội", "Phùng Quán"),
            Book(3, "Harry Potter", "J.K. Rowling"),
            Book(4, "Đắc Nhân Tâm", "Dale Carnegie")
        )
    )

    private var _student by mutableStateOf<Student?>(null)
    private var _showAvailableBooks by mutableStateOf(false)

    private val _availableBooks = mutableStateListOf<Book>()
    private val _borrowedBooks = mutableStateListOf<Book>()

    init {
        _availableBooks.addAll(library.getAvailableBooks())
    }

    fun updateStudent(name: String) {
        // Nếu student mới khác tên thì tạo mới, không thì giữ nguyên để không mất sách mượn
        if (_student == null || _student?.name != name) {
            _student = Student(name)
        }
        _showAvailableBooks = false
        refreshAvailableBooks()
        refreshBorrowedBooks()
    }

    // Trả ra State để Compose theo dõi
    fun borrowedBooks(): List<Book> = _borrowedBooks

    fun getAvailableBooks(): List<Book> = _availableBooks

    fun toggleBorrow(book: Book) {
        _student?.let {
            if (it.hasBorrowed(book.id)) {
                library.returnBook(book.id, it)
            } else {
                library.borrowBook(book.id, it)
            }
            refreshAvailableBooks()
            refreshBorrowedBooks()
        }
    }

    private fun refreshAvailableBooks() {
        _availableBooks.clear()
        _availableBooks.addAll(library.getAvailableBooks())
    }

    private fun refreshBorrowedBooks() {
        _borrowedBooks.clear()
        _student?.let {
            _borrowedBooks.addAll(library.getBorrowedBooksBy(it))
        }
    }

    fun toggleShowAvailable() {
        _showAvailableBooks = !_showAvailableBooks
    }

    fun isShowingAvailable(): Boolean = _showAvailableBooks
}
