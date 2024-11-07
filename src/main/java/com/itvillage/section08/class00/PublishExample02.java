package com.itvillage.section08.class00;

import com.itvillage.utils.Logger;
import com.itvillage.utils.TimeUtils;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;

import java.sql.Time;
import java.time.Duration;

/**
 * publish() 활용 예제
 *  - 다수의 Subscriber와 Flux를 공유한다.
 *  - Cold Sequence를 Hot Sequence로 변환한다.
 *  - connect()가 호출 되기 전 까지는 데이터를 emit하지 않는다.
 */
public class PublishExample02 {
    private static ConnectableFlux<String> publisher;
    private static int checkedAudienceNumbers;
    static {
        publisher =
                Flux
                    .just("Concert part1", "Concert part2", "Concert part3")
                    .delayElements(Duration.ofMillis(300L))
                    .publish(); // 이녀석은 connect 해야지 emit됨
    }

    public static void main(String[] args) {
        checkAudienceNumbers(); // 0명

        TimeUtils.sleep(500L);
        publisher.subscribe(data -> Logger.info("# audience 1 is watching {}", data)); // 첫번째 관객
        checkedAudienceNumbers++;

        checkAudienceNumbers(); // 1명

        TimeUtils.sleep(500L);
        publisher.subscribe(data -> Logger.info("# audience 2 is watching {}", data)); // 두번째 관객
        checkedAudienceNumbers++; // 2명

        checkAudienceNumbers(); // 2명 이상이라 connect() 실행, 콘서트 1부부터 끝가지 관람 가능

        TimeUtils.sleep(500L);
        publisher.subscribe(data -> Logger.info("# audience 3 is watching {}", data)); // 세번째 관객, 콘서트 2부부터 관람 가능

        TimeUtils.sleep(1000L);
    }

    public static void checkAudienceNumbers() {
        if (checkedAudienceNumbers >= 2) { // 관객이 2명이상이면 콘서트 시작
            publisher.connect();
        }
    }
}
