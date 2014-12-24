package com.pluralsight.dao;

import com.google.common.collect.ImmutableList;
import com.pluralsight.model.Book;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class BookRepositoryStub implements BookRepository {
    private Map<String, Book> books;

    public BookRepositoryStub() {
        books = new ConcurrentHashMap<>();
    }

    @Override
    public Collection<Book> getBooks() {
        return ImmutableList.copyOf(books.values());
    }

    @Override
    public Book getBook(String id) {
        return books.get(id);
    }

    @Override
    public Book addBook(Book book) {
        book.setId(UUID.randomUUID().toString());
        books.put(book.getId(), book);
        return book;
    }
}
