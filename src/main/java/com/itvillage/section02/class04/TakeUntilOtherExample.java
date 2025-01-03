package com.itvillage.section02.class04;

import com.itvillage.utils.Logger;
import com.itvillage.utils.TimeUtils;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

/**
 * 파라미터로 입력된 Publisher가 onNext 또는 onComplete signal을 발생시킬 때까지 Upstream에서 emit된 데이터만
 * Downstream에 emit 한다.
 */
public class TakeUntilOtherExample {
    public static void main(String[] args) {
        Flux.interval(Duration.ofMillis(300))
                .takeUntilOther(doSomeTask()) // onNext 또는 onComplete 시그널 발생할때까지 전달
                .subscribe(Logger::onNext);

        TimeUtils.sleep(2000);
    }

    private static Publisher<?> doSomeTask() {
        return Mono.empty().delay(Duration.ofSeconds(1));
    }
}
