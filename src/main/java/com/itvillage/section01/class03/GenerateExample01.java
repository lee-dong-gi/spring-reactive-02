package com.itvillage.section01.class03;

import com.itvillage.utils.Logger;
import reactor.core.publisher.Flux;

/**
 * generate 개념 이해 예제
 *  - 파라미터
 *      - Callable(함수형 인터페이스): 초기 상태 값 또는 객체를 제공한다.(State Supplier)
 *      - BiFunction<S, T, S>(함수형 인터페이스): SynchronousSink 와 현재 상태(state)를 사용하여 single signal 을 생성한다.
 */
public class GenerateExample01 {
    public static void main(String[] args) {
        Flux
            .generate(() -> 0, (state, sink) -> { // state가 0 부터 시작해서 순차적으로 실행
                sink.next(state); // downstream으로 emit
                if (state == 10) // 10 이면 종료
                    sink.complete();
                return ++state; // state 증가 후 리턴
            })
            .subscribe(Logger::onNext);
    }
}
