package hoangquan.vn.service;

import hoangquan.vn.entity.Book;
import hoangquan.vn.exception.AppException;
import hoangquan.vn.exception.ErrorCode;
import hoangquan.vn.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    public List<Book> getBooks(){
        return bookRepository.findAll();
    }

    public Book addBook(Book book){
        return bookRepository.save(book);
    }

    public Book getBookById(int id){
        return bookRepository.findById(id)
                .orElseThrow(()-> new AppException(ErrorCode.BOOK_NOT_FOUND));
    }

    public Book updateBook(Book book){
        Book temp = bookRepository.findById(book.getId())
                .orElseThrow(()-> new AppException(ErrorCode.BOOK_NOT_FOUND));

        temp.mapContent(book);

        return bookRepository.save(temp);
    }

    public void deleteBook(int id){
        bookRepository.deleteById(id);
    }

}
