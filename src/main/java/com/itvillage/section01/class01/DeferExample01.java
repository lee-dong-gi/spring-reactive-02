package com.itvillage.section01.class01;

import com.itvillage.utils.Logger;
import com.itvillage.utils.TimeUtils;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

/**
 * Defer 사용 예제
 *  - 실제로 구독이 발생하는 시점에 데이터를 emit 하는 예제.
 */
public class DeferExample01 {
    public static void main(String[] args) {
        Logger.info("# Starting");

        Mono<LocalDateTime> justMono = Mono.just(LocalDateTime.now()); // 선언한 시점에 emit함
        Mono<LocalDateTime> deferMono = Mono.defer(() -> Mono.just(LocalDateTime.now())); // 구독한 시점에 emit함

        TimeUtils.sleep(2000);

        justMono.subscribe(data -> Logger.onNext("just1", data)); // 이미 emit되어 있음
        deferMono.subscribe(data -> Logger.onNext("defer1", data)); // 여기서 emit됨

        TimeUtils.sleep(2000);

        justMono.subscribe(data -> Logger.onNext("just2", data));  // 이미 emit되어 있음
        deferMono.subscribe(data -> Logger.onNext("defer2", data)); // 여기서 emit됨
    }
}
