package com.pluralsight.dao;

import com.google.common.collect.ImmutableList;
import com.pluralsight.model.Book;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class BookRepositoryStub implements BookRepository {
    private Map<String, Book> books;

    public BookRepositoryStub() {
        books = new HashMap<>();

        Book book1 = new Book();
        book1.setId("1");
        book1.setTitle("Title_1");
        book1.setAuthor("Author_1");
        book1.setIsbn("1234");
        book1.setPublished(new Date());
        books.put(book1.getId(), book1);

        Book book2 = new Book();
        book2.setId("2");
        book2.setTitle("Title_2");
        book2.setAuthor("Author_1");
        book2.setIsbn("2345");
        book2.setPublished(new Date());
        books.put(book2.getId(), book2);
    }

    @Override
    public Collection<Book> getBooks() {
        return ImmutableList.copyOf(books.values());
    }

    @Override
    public Book getBook(String id) {
        return books.get(id);
    }
}
