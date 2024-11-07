package com.itvillage.section08.class00;

import com.itvillage.utils.Logger;
import com.itvillage.utils.TimeUtils;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;

import java.time.Duration;

/**
 * publish() 개념 예제
 *  - 다수의 Subscriber와 Flux를 공유한다.
 *  - Cold Sequence를 Hot Sequence로 변환한다.(타임라인이 하나만 생성됨, Subscribe 한 시점 이후에 emit된 데이터만 받을 수 있음)
 *  - connect()가 호출 되기 전 까지는 데이터를 emit하지 않는다.
 *  - connect()가 호출 되는 시점에 비로소 데이터가 emit됨
 */
public class PublishExample01 {
    public static void main(String[] args) {
        ConnectableFlux<Integer> flux =
                Flux
                    .range(1, 5)
                    .delayElements(Duration.ofMillis(300L))
                    .publish(); // 이녀석은 connect 해야지 emit됨

        TimeUtils.sleep(500L);
        flux.subscribe(data -> Logger.onNext("subscriber1: ", data));

        TimeUtils.sleep(200L);
        flux.subscribe(data -> Logger.onNext("subscriber2: ", data));

        flux.connect(); // 위쪽 지연시간은 아무 의미 없음, 비로소 emit됨

        TimeUtils.sleep(1000L);
        flux.subscribe(data -> Logger.onNext("subscriber3: ", data)); // 이 구독은 4,5 만 전달 받음

        TimeUtils.sleep(2000L);
    }
}