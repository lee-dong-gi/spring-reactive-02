package com.itvillage.section03.class03;

import com.itvillage.common.SampleData;
import com.itvillage.utils.Logger;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;

import java.util.List;

/**
 * Concat 활용 예제
 *  - 유형별 코로나 백신 list 를 concat 하는 예제
 */
public class ConcatExample03 {
    public static void main(String[] args) {
        Flux
            .concat(
                Flux.fromIterable(getViralVectorVaccines()), // A 서버에서 데이터를 가져옴
                Flux.fromIterable(getmRNAVaccines()), // B 서버에서 데이터를 가져옴
                Flux.fromIterable(getSubunitVaccines())) // C 서버에서 데이터를 가져옴
            .subscribe(Logger::onNext);
    }

    private static List<Tuple2<SampleData.CoronaVaccine, Integer>> getViralVectorVaccines() {
        return SampleData.viralVectorVaccines;
    }

    private static List<Tuple2<SampleData.CoronaVaccine, Integer>> getmRNAVaccines() {
        return SampleData.mRNAVaccines;
    }

    private static List<Tuple2<SampleData.CoronaVaccine, Integer>> getSubunitVaccines() {
        return SampleData.subunitVaccines;
    }
}
