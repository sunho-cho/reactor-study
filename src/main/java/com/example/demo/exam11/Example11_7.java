package com.example.demo.exam11;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Slf4j
public class Example11_7 {

    public static void main(String[] args) throws InterruptedException {
        
        final String key1 = "company";

        Mono
            .just("Steve")
            // but. 외부에서는 읽을 수 없음
            //.transformDeferredContextual((stringMono, ctx) ->
            //    ctx.get("role")
            //)
            .flatMap(name ->
                Mono.deferContextual(ctx -> 
                    // inner Sequence 는 외부의 context 를 읽을 수 있음
                    Mono.just(ctx.get(key1) + ", " + name)
                    .transformDeferredContextual((mono, innerCtx) -> 
                        mono.map(data -> data + ", " + innerCtx.get("role"))
                    )
                    .contextWrite(context -> context.put("role", "CEO"))
                )
            )
            .publishOn(Schedulers.parallel())
            .contextWrite(context -> context.put(key1, "Apple"))
            .subscribe(data -> log.info("# onNext: {}", data));

        Thread.sleep(100L);
    }

}
