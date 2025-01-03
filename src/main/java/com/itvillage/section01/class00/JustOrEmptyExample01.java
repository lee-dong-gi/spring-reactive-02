package com.itvillage.section01.class00;

import com.itvillage.utils.Logger;
import reactor.core.publisher.Mono;

/**
 * just()에 null 값을 입력하면 NullPointException 이 발생하는 예제
 */
public class JustOrEmptyExample01 {
    public static void main(String[] args) {
        Mono
            .just(null) // NPE 발생
            .log()
            .subscribe(Logger::onNext);
    }
}
