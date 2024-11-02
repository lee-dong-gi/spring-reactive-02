package com.itvillage.section03.class01;

import com.itvillage.utils.Logger;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * flatMap에서 flat의 의미를 이해하기 위한 예제
 *  - 들어오는 데이터들이 Publisher(Mono 또는 Flux)로 감싸져 있다면 감싸져 있는 Publisher들을 벗겨내고 하나의 Publisher로 평면화 한다.
 */
public class FlatMapExample05 {
    public static void main(String[] args) {
        Flux
                .just("Hello", "World")
                .map(word -> Mono.just(word))

                // 이게 없으면 Mono.just 오브젝트 자체가 전달되서 toString 되서 출력됨,
                // flatmap을 쓰면 2개의 mono가 하나로 합쳐짐(평탄화)
                .flatMap(word -> word)

                .subscribe(Logger::onNext);
    }
}
