package com.itvillage.section02.class05;

import com.itvillage.common.SampleData;
import com.itvillage.utils.Logger;
import reactor.core.publisher.Flux;

/**
 * next 기본 예제
 *  - emit 된 데이터 중에서 첫번째 데이터만 Downstream 으로 emit 한다.
 */
public class NextExample {
    public static void main(String[] args) {
        // .next는 조건이 맞으면 조건에 맞는 데이터만 emit하고 이후 데이터는 emit하지 않음 .next 주석하고 테스트해보기
        Flux
            .fromIterable(SampleData.btcTopPricesPerYear)
            .doOnNext(Logger::doOnNext)
            .filter(tuple -> tuple.getT1() == 2015)
            .next() //  emit 된 데이터 중에서 첫번째 데이터만 Downstream 으로 emit 한다. 없으면 empty
            .subscribe(tuple -> Logger.onNext(tuple.getT1(), tuple.getT2()));
    }
}
