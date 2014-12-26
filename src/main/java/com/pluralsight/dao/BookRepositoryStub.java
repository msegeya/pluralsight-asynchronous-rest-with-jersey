package com.pluralsight.dao;

import com.google.common.collect.ImmutableList;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.pluralsight.model.Book;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;

public class BookRepositoryStub implements BookRepository {
    private Map<String, Book> books;
    private ListeningExecutorService executorService;

    public BookRepositoryStub() {
        books = new ConcurrentHashMap<>();
        executorService = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(10));
    }

    @Override
    public Book getBook(String id) {
        return books.get(id);
    }

    @Override
    public ListenableFuture<Book> getBookAsync(final String id) {
        ListenableFuture<Book> future =
                executorService.submit(new Callable<Book>() {
                    public Book call() throws Exception {
                        return getBook(id);
                    }
                });
        return future;
    }

    @Override
    public Collection<Book> getBooks() {
        return ImmutableList.copyOf(books.values());
    }

    @Override
    public ListenableFuture<Collection<Book>> getBooksAsync() {
        ListenableFuture<Collection<Book>> future =
                executorService.submit(new Callable<Collection<Book>>() {
                    public Collection<Book> call() throws Exception {
                        return getBooks();
                    }
                });
        return future;
    }

    @Override
    public Book addBook(Book book) {
        book.setId(UUID.randomUUID().toString());
        books.put(book.getId(), book);
        return book;
    }

    @Override
    public ListenableFuture<Book> addBookAsync(final Book book) {
        ListenableFuture<Book> future =
                executorService.submit(new Callable<Book>() {
                    public Book call() throws Exception {
                        return addBook(book);
                    }
                });
        return future;
    }
}
