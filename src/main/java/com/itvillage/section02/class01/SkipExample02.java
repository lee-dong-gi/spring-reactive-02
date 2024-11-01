package com.itvillage.section02.class01;

import com.itvillage.utils.Logger;
import com.itvillage.utils.TimeUtils;
import reactor.core.publisher.Flux;

import java.time.Duration;

/**
 * skip 기본 개념 예제
 *  - 파라미터로 입력한 시간만큼 Upsteam에서 emit 되는 데이터를 건너뛴 후, 건너뛴 다음 데이터부터 Downstream으로 emit 한다.
 */
public class SkipExample02 {
    public static void main(String[] args) {
        Flux
            .interval(Duration.ofSeconds(1))
            .skip(Duration.ofMillis(2500)) // 2.5초간 건너뜀
            .subscribe(Logger::onNext); // 0,1 건너뛰고 2부터 찍힘

        TimeUtils.sleep(5000L);
    }
}
