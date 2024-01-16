package com.example.demo.exam11;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Slf4j
public class Example11_6 {

    public static void main(String[] args) throws InterruptedException {
        
        // 동일 값을 넣으면 Chain 가장 위쪽에 위치한 contextWrite 저장한값이 표현

        final String key1 = "company";
        final String key2 = "name";

        Mono
            .deferContextual(ctx ->
                Mono.just(ctx.get(key1))
            )
            .publishOn(Schedulers.parallel())
            .contextWrite(context -> context.put(key2, "Bill"))
            .transformDeferredContextual((mono, ctx) -> 
                // 아래에서 위로 전파 되므로 아직 key2 의 값이 존재하지 않음
                // 그러므로 모두 읽게 하려면 contextWrite 가 맨 마지막에 위치해야함.
                mono.map(data -> data + ", " + ctx.getOrDefault(key2, "Steve"))
            )
            .contextWrite(context -> context.put(key1, "Apple"))
            .subscribe(data -> log.info("# onNext: {}", data));

        Thread.sleep(100L);
    }

}
