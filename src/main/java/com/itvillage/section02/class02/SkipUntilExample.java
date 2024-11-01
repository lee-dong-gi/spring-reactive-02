package com.itvillage.section02.class02;

import com.itvillage.common.SampleData;
import com.itvillage.utils.Logger;
import reactor.core.publisher.Flux;

/**
 * skipUntil 기본 예제
 *  - 파라미터로 입력되는 Predicate가 true가 될때까지 건너뛴다.
 *  - skipWhile과 반대
 */
public class SkipUntilExample {
    public static void main(String[] args) {
        Flux
            .fromIterable(SampleData.btcTopPricesPerYear)
            .skipUntil(tuple -> tuple.getT2() > 10_000_000) // 해당 조건을 만족할때까지 건너뜀
            .subscribe(tuple -> Logger.onNext(tuple.getT1(), tuple.getT2()));
    }
}
