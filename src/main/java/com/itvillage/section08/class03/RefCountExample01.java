package com.itvillage.section08.class03;

import com.itvillage.utils.Logger;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;

import java.time.Duration;

/**
 * refCount() Operator
 *  - 다수의 Subscriber와 Flux를 공유한다.
 *  - Cold Sequence를 Hot Sequence로 변환한다.
 *  - 파라미터로 입력한 숫자만큼의 구독이 발생하는 시점에 connect()가 자동으로 호출된다.
 *  - 파라미터로 입력한 숫자만큼의 구독이 취소되면 Upstream 소스와의 연결을 해제한다.
 */
public class RefCountExample01 {
    public static void main(String[] args) throws InterruptedException {
        Flux<Long> publisher =
                Flux
                    .interval(Duration.ofMillis(500))
//                    .publish().autoConnect(1); s1: 0123 / s2: 45678 -> Upstream 소스와의 연결을 해제되지 않고 이어지기 때문에
                    .publish().refCount(1); // s1: 0123 / s2: 01234 -> Upstream 소스와의 연결이 해제되고 다시 연결 되기 때문에 처음부터 받음
        Disposable disposable =
                publisher.subscribe(data -> Logger.info("# subscriber 1: {}", data));

        Thread.sleep(2100L);
        disposable.dispose(); // 3까지 받고 취소됨

        publisher.subscribe(data -> Logger.info("# subscriber 2: {}", data));

        Thread.sleep(2500L); // autoConnect 기준 45678을 받음, refCount 기준 01234를 받음
    }
}
