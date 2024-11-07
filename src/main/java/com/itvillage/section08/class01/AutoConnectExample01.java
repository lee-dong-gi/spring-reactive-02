package com.itvillage.section08.class01;

import com.itvillage.utils.Logger;
import reactor.core.publisher.Flux;

import java.time.Duration;

/**
 * autoConnect() 기본 개념 예제
 *  - 다수의 Subscriber와 Flux를 공유한다.
 *  - Cold Sequence를 Hot Sequence로 변환한다.
 *  - 파라미터로 입력한 숫자 만큼의 구독이 발생하는 시점에 connect()가 자동으로 호출된다.
 *  - connect()를 직접 호출 할 필요가 없음
 */
public class AutoConnectExample01 {
    public static void main(String[] args) throws InterruptedException {
        Flux<String> publisher =
                Flux
                    .just("Concert part1", "Concert part2", "Concert part3")
                    .delayElements(Duration.ofMillis(300L))
                    .publish()
                    .autoConnect(2); // 두번(2)의 구독이 발생하는 시점에 데이터가 emit됨 

        Thread.sleep(500L);
        publisher.subscribe(data -> Logger.info("# audience 1 is watching {}", data)); // 1,2,3부 모두 관람 가능

        Thread.sleep(500L);
        publisher.subscribe(data -> Logger.info("# audience 2 is watching {}", data)); // 1,2,3부 모두 관람 가능

        Thread.sleep(500L);
        publisher.subscribe(data -> Logger.info("# audience 3 is watching {}", data)); // 이녀석은 2부와 3부만 관람 가능

        Thread.sleep(1000L);
    }
}
