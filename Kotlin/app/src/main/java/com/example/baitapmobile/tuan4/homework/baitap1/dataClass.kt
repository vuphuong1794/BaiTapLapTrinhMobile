package com.example.baitapmobile.tuan4.homework.baitap1

data class Book(
    val id: Int,
    val title: String,
    val author: String
)

open class User(open val name: String)

class Student(override val name: String) : User(name) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Student) return false
        return name == other.name
    }

    override fun hashCode(): Int = name.hashCode()
}

class Library(initialBooks: List<Book> = emptyList()) {
    private val _allBooks = initialBooks.toMutableList()

    private val borrowedMap = mutableMapOf<Int, Student>()

    fun addBook(bookId: Int, title: String, author: String): Boolean {
        // Kiểm tra xem ID đã tồn tại chưa
        if (_allBooks.any { it.id == bookId }) {
            return false // ID đã tồn tại
        }

        val newBook = Book(bookId, title, author)
        _allBooks.add(newBook)
        return true
    }

    fun removeBook(bookId: Int): Boolean {
        // Không thể xóa sách đang được mượn
        if (borrowedMap.containsKey(bookId)) {
            return false
        }

        return _allBooks.removeIf { it.id == bookId }
    }

    fun getAllBooks(): List<Book> {
        return _allBooks.toList()
    }

    fun borrowBook(bookId: Int, student: Student): Boolean {
        // Kiểm tra sách có tồn tại không
        if (!_allBooks.any { it.id == bookId }) {
            return false
        }

        if (!borrowedMap.containsKey(bookId)) {
            borrowedMap[bookId] = student
            return true
        }
        return false
    }

    fun returnBook(bookId: Int, student: Student): Boolean {
        if (borrowedMap[bookId] == student) {
            borrowedMap.remove(bookId)
            return true
        }
        return false
    }

    fun getAvailableBooks(): List<Book> {
        return _allBooks.filter { !borrowedMap.containsKey(it.id) }
    }

    fun getBorrowedBooksBy(student: Student): List<Book> {
        return _allBooks.filter { borrowedMap[it.id] == student }
    }
}