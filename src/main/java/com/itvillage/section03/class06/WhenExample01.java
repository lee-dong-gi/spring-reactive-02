package com.itvillage.section03.class06;

import com.itvillage.utils.Logger;
import com.itvillage.utils.TimeUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

/**
 * when 기본 개념 예제
 *  - 파라미터로 입력된 Publisher들이 종료할 때 까지 대기한 후, Mono<Void>를 반환한다.
 *  - and와 동일하나 여러개의 시퀀스를 전달함
 */
public class WhenExample01 {
    public static void main(String[] args) {
        Mono
            .when(
                Flux
                    .just("Hi", "Tom")
                    .delayElements(Duration.ofSeconds(2))
                    .doOnNext(Logger::doOnNext),
                Flux
                    .just("Hello", "David")
                    .delayElements(Duration.ofSeconds(1))
                    .doOnNext(Logger::doOnNext)
            )
            .subscribe(
                Logger::onNext, // 작동 안함 데이터 전달 안옴
                Logger::onError,
                Logger::onComplete // 이것만 전달옴(여래개)
            );

        TimeUtils.sleep(5000);
    }
}
