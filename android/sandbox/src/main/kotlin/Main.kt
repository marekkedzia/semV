import java.util.Comparator

val printBooks = { //lambda
        books: List<String> ->
    val attachPrefix = { prefix: String -> { book: String -> println("$prefix $book") } } //currying
    println("Books: {")
    books.forEach(attachPrefix("Book title: ")) //for each
    println("}")
}

fun isTheSameBook(book1: String, book2: String) = book1 == book2  //single expression function

fun sortBooks(books: List<String>, compare: (a: String, b: String) -> Int) = //higher order function
    books.sortedWith(Comparator(compare))

fun printBook(books: List<String>, index: Int) {
    books.getOrNull(index)?.let { println("Book title: $it") } ?: println("No book at index $index") //let
}

fun addBook(books: List<String>, book: String) {
    for (b in books) {
        if (isTheSameBook(b, book)) {
            println("Book with the same title already exists")
            return
        }
    }
    books.plus(book)
}

fun printTitles(books: List<String>, hardlyForbiddenWord: String = "C++", veryForbiddenWord: String = "Java") { //named arguments
    var i = 0;
    while (i < books.size) { //while
        if (books[i].contains(veryForbiddenWord)) {
            println("Book title contains very forbidden word! Printing stopped.")
            break //break
        }

        if (books[i].contains(hardlyForbiddenWord)) {
            println("Book title contains hardly forbidden word! Title skipped.")
            i++
            continue //continue
        }
        printBook(books, i)
        i++
    }
}

fun main() {
    val books =
        arrayListOf("Pragmatyczny programista", "Symofnia C++", "Czysty kod", "Oracle PL/SQL", "Zobacz, Java w akcji")

    val sortedBooks = sortBooks(books) { a, b -> a.compareTo(b) } //lambda
    printBooks(sortedBooks)
    println("Is it the same title? ${isTheSameBook("Pragmatyczny programista", "Pragmatyczny programista")}")
    printBook(sortedBooks, 2)
    printBook(sortedBooks, 10)
    addBook(sortedBooks, "Java w akcji")
    addBook(sortedBooks, "Java w akcji 2")
    printBooks(sortedBooks)
    printTitles(sortedBooks)
}
//Sro0915-1100