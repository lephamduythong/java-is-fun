package com.ltfullstack.bookservice.query.controller;

import com.ltfullstack.bookservice.query.model.BookResponseModel;
import com.ltfullstack.bookservice.query.queries.GetAllBookQuery;
import com.ltfullstack.commonservice.model.BookResponseCommonModel;
import com.ltfullstack.commonservice.queries.GetBookDetailQuery;
import com.ltfullstack.commonservice.services.KafkaService;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
public class BookQueryController {

    @Autowired
    private QueryGateway queryGateway;

    @Autowired
    private KafkaService kafkaService;

    @GetMapping
    public List<BookResponseModel> getAllBooks(){
        GetAllBookQuery query = new GetAllBookQuery();
        var bookFuture = queryGateway.query(query, ResponseTypes.multipleInstancesOf(BookResponseModel.class));
        bookFuture.thenAccept(books -> {
            books.forEach(book -> {
                System.out.println("Book ID: " + book.getId());
                // System.out.println("Book Title: " + book.getTitle());
                System.out.println("Book Author: " + book.getAuthor());
            });
        });
        return bookFuture.join();
    }

    @GetMapping("{bookId}")
    public BookResponseModel getBookDetail(@PathVariable String bookId){
        GetBookDetailQuery query = new GetBookDetailQuery(bookId);
        return queryGateway.query(query,ResponseTypes.instanceOf(BookResponseModel.class)).join();
    }

    @PostMapping("/sendMessage")
    public void sendMessage(@RequestBody String message){
        kafkaService.sendMessage("test",message);
    }
}