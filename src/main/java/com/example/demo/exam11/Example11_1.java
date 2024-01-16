package com.example.demo.exam11;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Slf4j
public class Example11_1 {

    public static void main(String[] args) throws InterruptedException {

        // Context : 어떠한 상황에서 그 상황을 처리하기 위해 필요한 정보
        // Reactor 에서의 Context 는 ThreadLocal 과 다소 유사하나 각각 실행 Thread 와 매핑되는데
        // Subscriber 와 매핑된다.


        Mono
            // 원본 Data Source Level 에서 읽는 방식
            // ctx -> ContextView
            .deferContextual(ctx ->
                Mono.just("Hello" + " " + ctx.get("firstName") + " " + ctx.get("lastName"))
                        .doOnNext(data -> log.info(" # just doOnNext : {}", data))
            )
            // Operator Chain 상의 서로 다른 Thread 들이 Context 의 저장된 데이터를 손쉽게 접근할 수 있음
            // subscribeOn, publishOn 이 다른 Thread (ThreaLocal 과 차이)
            .subscribeOn(Schedulers.boundedElastic())
            .publishOn(Schedulers.parallel())
            // Operator Chain 중간에서 읽는 방식
            .transformDeferredContextual((mono, ctx) -> 
                mono.map(data -> data + " --> is lastName: " + ctx.get("lastName"))
            )
            // Context 는 Key, Value 형태로 저장
            .contextWrite(context -> context.put("lastName", "Jobs"))
            .contextWrite(context -> context.put("firstName", "Steve"))
            .subscribe(data -> log.info("# onNext: {}", data));

        Thread.sleep(100L);
    }

}
