package com.itvillage.section03.class06;

import com.itvillage.utils.Logger;
import com.itvillage.utils.TimeUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

/**
 * and 기본 개념 예제
 *  - Mono와 파라미터로 입력된 Publisher가 종료할때 까지 대기한 후, Mono<Void>를 반환한다.
 *  - when과 동일하나 하나의 시퀀스만 전달함
 */
public class AndExample01 {
    public static void main(String[] args) {
        Mono
            .just("Okay") // subscribe(downstream)로 전달안됨
            .delayElement(Duration.ofSeconds(1))
            .doOnNext(Logger::doOnNext)
            .and( // Mono와 파라미터로 입력된 Publisher가 종료되었음을 알림
                Flux
                    .just("Hi", "Tom") // subscribe(downstream)로 전달안되고 onComplete signal만 전달함
                    .delayElements(Duration.ofSeconds(2))
                    .doOnNext(Logger::doOnNext)
            )
            .subscribe(
                Logger::onNext, // 아무것도 안옴
                Logger::onError,// 아무것도 안옴 
                Logger::onComplete // and operator에서 전달온 onComplete signal만 전달받음
            );

        TimeUtils.sleep(5000);
    }
}
