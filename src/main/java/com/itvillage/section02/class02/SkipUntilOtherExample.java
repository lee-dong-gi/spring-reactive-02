package com.itvillage.section02.class02;

import com.itvillage.utils.Logger;
import com.itvillage.utils.TimeUtils;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

/**
 * 파라미터로 입력된 Publisher가 onNext 또는 onComplete signal을 발생시킬 때까지 Upstream에서 emit된 데이터를 건너뛴다.
 */
public class SkipUntilOtherExample {
    public static void main(String[] args) {
        Flux.interval(Duration.ofSeconds(1))
                .skipUntilOther(doSomeTask()) // 0,1 건너뛰고 2,3만 출력 onNext 또는 onComplete 시그널 발생한 이후 전달
                .subscribe(Logger::onNext);

        TimeUtils.sleep(4000);
    }

    private static Publisher<?> doSomeTask() {
        return Mono.empty().delay(Duration.ofMillis(2500)); // 2.5초 뒤 onComplete signal 발생
    }
}
