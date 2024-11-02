package com.itvillage.section03.class07;

import com.itvillage.utils.Logger;
import com.itvillage.utils.TimeUtils;
import reactor.core.publisher.Mono;

import java.time.Duration;

/**
 * then 기본 개념 예제
 *  - Upstream Mono의 sequence가 종료되면, Mono<Void>를 Downstream으로 전달한다.
 *  - 하나의 작업(Mono)의 끝난 후 onComplete 시그널을 전송함
 */
public class thenExample01 {
    public static void main(String[] args) {
        Mono
            .just("Hi")
            .delayElement(Duration.ofSeconds(1))
            .doOnNext(Logger::doOnNext)
            .then() // onComplete 시그널만 전달
            .subscribe(
                Logger::onNext, // 아무 데이터도 전달안됨
                Logger::onError,
                Logger::onComplete
            );

        TimeUtils.sleep(1500);
    }
}
