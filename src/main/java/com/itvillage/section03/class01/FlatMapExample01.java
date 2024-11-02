package com.itvillage.section03.class01;

import com.itvillage.utils.Logger;
import reactor.core.publisher.Flux;

/**
 * flatMap: 각 요소에 대해 새로운 Publisher나 Stream을 생성하고, 이를 하나의 스트림으로 평탄화하여 결과를 방출
 * upstream 쪽에서 데이터가 1건 emit될때 flatmap 내부에서 여러건의 데이터가 emit 할 수 있음
 * flatMap 기본 개념 예제
 *  - inner Publisher를 통해 1대다의 데이터 매핑이 가능하다.
 */
public class FlatMapExample01 {
    public static void main(String[] args) {
        Flux
            .just("Good", "Bad")
            .flatMap(feeling -> // return 값이 새로운 publisher가 됨
                    Flux
                            .just("Morning", "Afternoon", "Evening")
                            .map(time -> feeling + " " + time))
            .subscribe(Logger::onNext);
    }
}
