package com.itvillage.section06.class01;

import com.itvillage.common.Book;
import com.itvillage.common.SampleData;
import com.itvillage.utils.Logger;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

/**
 * onErrorResume 활용 예제
 *  - 예외가 발생했을 때, onError signal을 Downstream으로 전송하지 않고, 대체 Publisher로 데이터를 emit하고자 할 경우
 */
public class OnErrorResumeExample01 {
    public static void main(String[] args) {
        final String keyword = "DDD";
        getBooksFromCache(keyword)
                .onErrorResume(error -> getBooksFromDatabase(keyword)) // NoSuchBookException 시그널을 전달받아 실행됨
                .subscribe(data -> Logger.onNext(data.getBookName()),
                        Logger::onError);
    }

    private static Flux<Book> getBooksFromCache(final String keyword) {
        return Flux
                .fromIterable(SampleData.books)
                .filter(book -> book.getBookName().contains(keyword)) // DDD라는 도서명은 없음
                .switchIfEmpty(Flux.error(new NoSuchBookException("No such Book"))); // 이게 작동함
    }

    private static Flux<Book> getBooksFromDatabase(final String keyword) {
        List<Book> books = new ArrayList<>(SampleData.books);
        
        // 만약 해당 코드가 없다면 subscribe쪽에 NoSuchBookException 시그널이 전송됨
        books.add(new Book(11, "DDD: Domain Driven Design",
                "Joy", "ddd-man", 35000, 200));

        return Flux // 대체 Publisher 동작
                .fromIterable(books)
                .filter(book -> book.getBookName().contains(keyword))
                .switchIfEmpty(Flux.error(new NoSuchBookException("No such Book")));
    }

    private static class NoSuchBookException extends RuntimeException {
        NoSuchBookException(String message) {
            super(message);
        }
    }
}