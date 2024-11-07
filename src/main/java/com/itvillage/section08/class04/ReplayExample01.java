package com.itvillage.section08.class04;

import com.itvillage.utils.Logger;
import com.itvillage.utils.TimeUtils;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;

import java.time.Duration;

/**
 * replay() 개념 예제
 *  - 다수의 Subscriber와 Flux를 공유한다.
 *  - Cold Sequence를 Hot Sequence로 변환한다.
 *  - connect()가 호출 되기 전 까지는 데이터를 emit하지 않는다.
 *  - 구독 시점 이전에 emit된 데이터를 모두 캐시한다.
 */
public class ReplayExample01 {
    public static void main(String[] args) {
        ConnectableFlux<Integer> flux =
                Flux
                    .range(1, 5)
                    .delayElements(Duration.ofMillis(300L))
                    .replay(); // 구독시점에 emit하지 않고 connect를 호출하는 시점에 emit함, 구독시점 이전 데이터를 전달 받을 수 있음

        TimeUtils.sleep(500L);
        flux.subscribe(data -> Logger.onNext("subscriber1: ", data));

        TimeUtils.sleep(200L);
        flux.subscribe(data -> Logger.onNext("subscriber2: ", data));

        flux.connect(); // 위에 딜레이타임 의미 없음

        TimeUtils.sleep(1000L);
        flux.subscribe(data -> Logger.onNext("subscriber3: ", data)); // 지나간 1,2,3을 전달받음

        TimeUtils.sleep(2000L);
    }
}