package com.itvillage.section01.class04;

import com.itvillage.utils.Logger;
import com.itvillage.utils.TimeUtils;
import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import java.util.Arrays;
import java.util.List;

/**
 * create 개념 이해 예제
 *  - Subscriber 가 request 할 경우에 next signal 이벤트를 발생하는 예제
 *  - generate operator 와 달리 한번에 여러 건의 next signal 이벤트를 발생 시킬 수 있다.
 * 다운스트림에서 데이터를 요청하면 create 오레이터를 이용해서 요청한 개수만큼의 데이터를 emit하는 예제코드
 */
public class CreateExample01 {
    public static int SIZE = 0;
    public static int COUNT = -1;
    private static final List<Integer> dataSource =
            Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

    // 다운스트림에서 데이터를 요청하면 create 오레이터를 이용해서 요청한 개수만큼의 데이터를 emit하는 예제코드
    public static void main(String[] args) {
        Logger.info("# start");
        Flux.create((FluxSink<Integer> emitter) -> {
            emitter.onRequest(n -> {
                TimeUtils.sleep(1000L);
                for (int i = 0; i < n; i++) {
                    if (COUNT >= 9) {
                        emitter.complete();
                    } else {
                        COUNT++;
                        emitter.next(dataSource.get(COUNT)); // emit
                    }
                }
            });

            emitter.onDispose(() -> Logger.info("# clean up"));
        }).subscribe(new BaseSubscriber<Integer>() {
            @Override
            protected void hookOnSubscribe(Subscription subscription) {
                request(2); // 구독할때 2개 요청함
            }

            @Override
            protected void hookOnNext(Integer value) {
                SIZE++;
                Logger.onNext(value);
                if (SIZE == 2) {
                    request(2); // 업스트림쪽에서 데이터를 전달 받을때 마다 2건씩 요청함
                    SIZE = 0;
                }
            }

            @Override
            protected void hookOnComplete() {
                Logger.onComplete("# onComplete");
            }
        });
    }
}
