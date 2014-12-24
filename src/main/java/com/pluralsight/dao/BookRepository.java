package com.pluralsight.dao;

import com.pluralsight.model.Book;

import java.util.Collection;

/**
 * @author Filip Pacholczyk
 */
public interface BookRepository {
    Collection<Book> getBooks();

    Book getBook(String id);

    Book addBook(Book book);
}
