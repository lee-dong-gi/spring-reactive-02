package com.itvillage.section04.class00;

import com.itvillage.utils.Logger;
import reactor.core.publisher.Flux;

/**
 * doOnTerminate()와 doAfterTerminate()의 차이점을 이해하기 위한 예제
 *  - doOnTerminate()는 upstream operator가 종료할 때 호출된다.
 *  - doAfterTerminate는 전체 Sequence가 종료할 때 Downstream에서 Upstream으로 전파(propagation)되면서 호출된다.
 */
public class DoAfterTerminateExample01 {
    public static void main(String[] args) {
        Flux
            .just("HI", "HELLO") // 0. downstream으로 emit
            .filter(data -> data.equals("HI")) // 1. HI만 필터링
            .doOnTerminate(() -> Logger.doOnTerminate("filter")) // 4. filter 시퀀스 종료 시
            .doAfterTerminate(() -> Logger.doAfterTerminate("filter")) // 8. 시퀀스 전체 종료 후
            .map(data -> data.toLowerCase()) // 2. HI를 hi로 변환
            .doOnTerminate(() -> Logger.doOnTerminate("map")) // 5. map 시퀀스 종료 시
            .doAfterTerminate(() -> Logger.doAfterTerminate("map")) // 7. 시퀀스 전체 종료 후
            .subscribe(
                    data -> Logger.onNext(data), // 3. hi 출력
                    error -> {},
                    () -> Logger.doOnComplete()); // 6. 시퀀스 전체 종료
    }
}
