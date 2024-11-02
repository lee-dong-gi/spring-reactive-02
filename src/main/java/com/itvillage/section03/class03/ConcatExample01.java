package com.itvillage.section03.class03;

import com.itvillage.utils.Logger;
import reactor.core.publisher.Flux;

/**
 * concat 기본 개념 예제
 *  - 파라미터로 입력된 Publisher Sequence 들을 연결해서 차례대로 emit한다.
 *  - 연결된 시퀀스는 각각 구독이 따로 이루어짐
 *  - 앞서 온 구독의 emit이 종료될 때까지 뒤의 시퀀스는 대기함
 */
public class ConcatExample01 {
    public static void main(String[] args) {
        Flux
            .concat(Flux.just(1, 2, 3), Flux.just(4, 5))
            .subscribe(Logger::onNext);
    }
}
