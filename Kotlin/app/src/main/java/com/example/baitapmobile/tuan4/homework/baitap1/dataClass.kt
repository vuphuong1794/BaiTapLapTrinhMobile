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

class Library(private val allBooks: List<Book>) {
    private val borrowedMap = mutableMapOf<Int, Student>()

    fun borrowBook(bookId: Int, student: Student): Boolean {
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
        return allBooks.filter { !borrowedMap.containsKey(it.id) }
    }

    fun getBorrowedBooksBy(student: Student): List<Book> {
        return allBooks.filter { borrowedMap[it.id] == student }
    }
}