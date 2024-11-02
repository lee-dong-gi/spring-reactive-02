package com.itvillage.section03.class06;

import com.itvillage.utils.Logger;
import com.itvillage.utils.TimeUtils;
import reactor.core.publisher.Mono;

import java.time.Duration;

/**
 * when 활용 예제
 *  - 1개 이상의 task가 모두 끝났을 때, Complete Signal을 전달해서 추가 task를 수행하는 예제
 */
public class WhenExample02 {
    public static void main(String[] args) {
        Mono.when(restartApplicationServer(), restartDBServer(), restartStorageServer())
            .subscribe(
                    Logger::onNext, // 아무것도 안옴
                    Logger::onError,
                    () -> Logger.onComplete("Send an email to Administrator: " +
                            "All Servers are restarted successfully") // 이것만 실행됨
            );

        TimeUtils.sleep(6000L);
    }

    private static Mono<String> restartApplicationServer() {
        return Mono
                .just("Application Server was restarted successfully.")
                .delayElement(Duration.ofSeconds(2))
                .doOnNext(Logger::doOnNext);
    }

    private static Mono<String> restartDBServer() {
        return Mono
                .just("DB Server was restarted successfully.")
                .delayElement(Duration.ofSeconds(4))
                .doOnNext(Logger::doOnNext);
    }

    private static Mono<String> restartStorageServer() {
//        return Mono
//                .just("Storage Server was restarted successfully.")
//                .delayElement(Duration.ofSeconds(3))
//                .doOnNext(Logger::doOnNext);
        return Mono.error(new RuntimeException("test"));
    }
}
