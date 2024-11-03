package com.itvillage.section04.class00;

import com.itvillage.utils.Logger;
import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;

/**
 * DoOnXXXX() Operator의 기본 개념 예제
 *
 * doOnSubscribe()	Publisher가 구독 중일 때 트리거 되는 동작을 추가할 수 있다.
 * doOnRequest()	Publisher가 요청을 수신할 때 트리거 되는 동작을 추가할 수 있다.
 * doOnNext()	Publisher가 데이터를 emit할 때 트리거 되는 동작을 추가할 수 있다.
 * doOnComplete()	Publisher가 성공적으로 완료되었을 때 트리거 되는 동작을 추가할 수 있다.
 * doOnError()	Publisher가 에러가 발생한 상태로 종료되었을 때 트리거 되는 동작을 추가할 수 있다.
 * doOnCancel()	Publisher가 취소되었을 때 트리거 되는 동작을 추가할 수 있다.
 * doOnTerminate()	Publisher가 성공적으로 완료되었을 때 또는 에러가 발생한 상태로 종료되었을 때 트리거 되는 동작을 추가할 수 있다.
 * doOnEach()	doOnNext(), doOnComplete(), doOnError()의 역할을 할 수 있다.
 * doOnDiscard()	Upstream에 있는 전체 Operator 체인의 동작 중에서 Operator에 의해 폐기되는 요소를 조건부로 정리할 수 있다.
 * doAfterTerminate()	Sequence가 성공적으로 완료된 직 후 또는 에러가 발생하여 Sequence가 종료된 직 후에 트리거 되는 동작을 추가할 수 있다.
 * doFirst()	Publisher가 구독 되기 전에 트리거 되는 동작을 추가할 수 있다.
 * doFinally()	에러를 포함해서 어떤 이유이든 간에 Publisher가 종료된 후 트리거 되는 동작을 추가할 수 있습니다.
 */
public class DoOnXxxxExample01 {
    public static void main(String[] args) {
        Flux // range부분과 filter 부분을 나누어 생각할 수 있음, 그러나 range와 filter 시퀀스 단위로 보면 순서를 밑에서 위로 실행됨
            .range(1, 5) // 0. emit할 데이터 지정
            .doFinally(signalType -> Logger.info("doFinally() > range")) // 7. 에러를 포함해서 어떤 이유이든 간에 Publisher가 종료된 후 트리거 되는 동작
            .doOnNext(data -> Logger.doOnNext("range", data)) // 5. Publisher가 데이터를 emit할 때 트리거 되는 동작
            .doOnRequest(n -> Logger.info("doOnRequest > range: {}", 1)) // 4. Publisher가 구독 중일 때 트리거 되는 동작
            .doOnSubscribe(subscription -> Logger.info("doOnSubscribe() > range")) // 2. doOnSubscribe()	Publisher가 구독 중(직후)일 때 트리거 되는 동작
            .doFirst(() -> Logger.info("doFirst() > range")) // 1. Publisher가 구독 되기 전에 트리거 되는 동작
            .doOnComplete(() -> Logger.info("doOnComplete() > range")) // 6. Publisher가 성공적으로 완료되었을 때 트리거 되는 동작

            .filter(num -> num % 2 == 1)
            .doOnNext(data -> Logger.doOnNext("filter", data)) // 5. 요청한 데이터 건수만큼 데이터 emit
            .doOnRequest(n -> Logger.info("doOnRequest > filter: {}", 1)) // 4. 데이터 요청할때 실행
            .doOnSubscribe(subscription -> Logger.info("doOnSubscribe() > filter")) // 2. 구독될때 실행
            .doFinally(signalType -> Logger.info("doFinally() > filter")) // 7. 마지막으로 실행
            .doOnComplete(() -> Logger.info("doOnComplete() > filter")) // 6. 모든 데이터가 emit되면 실행
            .doFirst(() -> Logger.info("doFirst() > filter")) // 1. 구독되기전 실행
            .subscribe(new BaseSubscriber<>() {
                @Override
                protected void hookOnSubscribe(Subscription subscription) {
                    request(1); // 3. 데이터 요청
                }

                @Override
                protected void hookOnNext(Integer value) {
                    Logger.info("# hookOnNext: {}", value);
                    request(1); // 3. 데이터 요청
                }
            });
    }
}
