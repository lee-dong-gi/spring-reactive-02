package com.itvillage.section06.class04;

import com.itvillage.utils.Logger;
import com.itvillage.utils.TimeUtils;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import java.time.Duration;

/**
 * retry 기본 개념 예제
 *  - 에러가 발생했을 때, 지정된 횟수만큼 원본 Publisher의 Sequence를 다시 구독한다.
 */
public class RetryExample01 {
    public static void main(String[] args) throws InterruptedException {
        final int[] count = {1};
        Flux
            .range(1, 3)
            .delayElements(Duration.ofSeconds(1))
            .map(num -> {
                if (num == 3 && count[0] == 1) {
                    count[0]++;
                    TimeUtils.sleep(1000); // 3인 경우 1초 추가시간 소요되어서 실패함 retry에서 다시 1,2,3을 emit하게 함
                }

                return num;
            })
            .timeout(Duration.ofMillis(1500))
            .retry(1) // 1번만 재시도함
            .subscribe(Logger::onNext,
                    Logger::onError,
                    Logger::onComplete);

        TimeUtils.sleep(8000);
    }
}