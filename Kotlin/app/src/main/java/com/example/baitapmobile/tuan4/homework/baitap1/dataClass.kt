package com.example.baitapmobile.tuan4.homework.baitap1

data class Book(
    val id: Int,
    val title: String,
    val author: String
)

open class User(open val name: String)

class Student(override val name: String) : User(name) {
    private val borrowedBookIds = mutableSetOf<Int>()

    fun borrow(bookId: Int) {
        borrowedBookIds.add(bookId)
    }

    fun returnBook(bookId: Int) {
        borrowedBookIds.remove(bookId)
    }

    fun hasBorrowed(bookId: Int): Boolean = bookId in borrowedBookIds
}

class Library(private val allBooks: List<Book>) {
    private val borrowedMap = mutableMapOf<Int, Student>()

    fun getAvailableBooks(): List<Book> =
        allBooks.filter { it.id !in borrowedMap }

    fun getBorrowedBooksBy(student: Student): List<Book> =
        allBooks.filter { student.hasBorrowed(it.id) }

    fun borrowBook(bookId: Int, student: Student) {
        if (bookId !in borrowedMap) {
            borrowedMap[bookId] = student
            student.borrow(bookId)
        }
    }

    fun returnBook(bookId: Int, student: Student) {
        if (borrowedMap[bookId] == student) {
            borrowedMap.remove(bookId)
            student.returnBook(bookId)
        }
    }
}

