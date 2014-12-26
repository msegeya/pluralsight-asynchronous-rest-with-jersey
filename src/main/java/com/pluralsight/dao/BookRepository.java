package com.pluralsight.dao;

import com.google.common.util.concurrent.ListenableFuture;
import com.pluralsight.model.Book;

import java.util.Collection;

/**
 * @author Filip Pacholczyk
 */
public interface BookRepository {
    Book getBook(String id);

    ListenableFuture<Book> getBookAsync(String id);

    Book addBook(Book book);

    Collection<Book> getBooks();

    ListenableFuture<Collection<Book>> getBooksAsync();

    ListenableFuture<Book> addBookAsync(Book book);
}
