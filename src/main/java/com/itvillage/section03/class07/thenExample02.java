package com.itvillage.section03.class07;

import com.itvillage.utils.Logger;
import com.itvillage.utils.TimeUtils;
import reactor.core.publisher.Mono;

import java.time.Duration;

/**
 * then 활용 예제
 *  - 1 개의 task가 모두 끝났을 때, Complete Signal을 전달해서 추가 task를 수행하는 예제
 */
public class thenExample02 {
    public static void main(String[] args) {
        restartApplicationServer()
            .then()
            .subscribe(
                    Logger::onNext, // 아무 데이터도 안옴
                    Logger::onError,
                    () -> Logger.onComplete("Send an email to Administrator: " +
                            "Application Server is restarted successfully")
            );

        TimeUtils.sleep(3000L);
    }

    private static Mono<Void> restartApplicationServer() {
        return Mono.error(new RuntimeException("test"));
//        return Mono
//                .just("Application Server was restarted successfully.")
//                .delayElement(Duration.ofSeconds(2))
//                .doOnNext(Logger::doOnNext)
//                .flatMap(notUse -> Mono.empty());
    }
}
