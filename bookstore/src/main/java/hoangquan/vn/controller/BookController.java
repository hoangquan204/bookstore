package hoangquan.vn.controller;

import hoangquan.vn.dto.response.ApiResponse;
import hoangquan.vn.entity.Book;
import hoangquan.vn.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
@CrossOrigin(origins = "*")
public class BookController {
    @Autowired
    private BookService bookService;

    @GetMapping("/hello-world")
    public ApiResponse<String> getHello(){
        return ApiResponse.<String>builder()
                .code(200)
                .message("Hello world!")
                .build();
    }

    @GetMapping
    public ApiResponse<List<Book>> getBooks(){
        return ApiResponse.<List<Book>>builder()
                .code(200)
                .message("Get books successfully!")
                .result(bookService.getBooks())
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<Book> getBookById(@PathVariable int id){
        return ApiResponse.<Book>builder()
                .code(200)
                .message("Get book successfully!")
                .result(bookService.getBookById(id))
                .build();
    }

    @PostMapping
    public ApiResponse<Book> addBook(@RequestBody Book book){
        return ApiResponse.<Book>builder()
                .code(200)
                .message("Add book successfully!")
                .result(bookService.addBook(book))
                .build();
    }

    @PutMapping("{id}")
    public ApiResponse<Book> updateBook(@PathVariable int id, @RequestBody Book book){
        book.setId(id);
        return ApiResponse.<Book>builder()
                .code(200)
                .message("Add book successfully!")
                .result(bookService.updateBook(book))
                .build();
    }

    @DeleteMapping("{id}")
    public ApiResponse<String> deleteBook(@PathVariable int id){
        bookService.deleteBook(id);
        return ApiResponse.<String>builder()
                .code(200)
                .message("Delete book successfully!")
                .build();
    }
}
