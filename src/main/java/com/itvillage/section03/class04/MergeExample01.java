package com.itvillage.section03.class04;

import com.itvillage.utils.Logger;
import com.itvillage.utils.TimeUtils;
import reactor.core.publisher.Flux;

import java.time.Duration;

/**
 * merge 기본 개념 예제
 *  - 파라미터로 입력된 Publisher Sequence에서 emit된 데이터를 emit된 시간이 빠른 순서대로 merge한다.
 *  - concat처럼 lazy하게 구독하는 것이 아닌 즉시 모든 구독이 이루어짐
 */
public class MergeExample01 {
    public static void main(String[] args) {
        Flux
            .merge(
                    Flux.just(1, 2, 3).delayElements(Duration.ofMillis(300L)), // 0.3 초에 한번씩 emit
                    Flux.just(4, 5, 6).delayElements(Duration.ofMillis(500L)) // 0.5초에 한번씩 emit
            )
            .subscribe(Logger::onNext);

        TimeUtils.sleep(3500L);
    }
}
