package com.itvillage.section01.class00;

import com.itvillage.common.SampleData;
import com.itvillage.utils.Logger;
import reactor.core.publisher.Flux;

/**
 * range()의 사용 예제
 *  - range()를 사용해서 list의 특정 index에 해당하는 데이터를 조회하는 예제
 */
public class RangeExample03 {
    public static void main(String[] args) {
        Flux
            .range(7, 5) // 7부터 이후 5개
            .map(SampleData.btcTopPricesPerYear::get)
            .subscribe(tuple -> Logger.onNext(tuple.getT1() + "'s: " + tuple.getT2()));
    }
}
