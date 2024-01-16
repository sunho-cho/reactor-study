package com.example.demo.exam11;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Slf4j
public class Example11_5 {

    public static void main(String[] args) throws InterruptedException {
        
        final String key1 = "company";

        Mono<String> mono = Mono
            .deferContextual(ctx ->
                Mono.just("Company: " + " " + ctx.get(key1))
            )
            .publishOn(Schedulers.parallel());


        // 구독별로 Context 가 관리 되기 때문에 공유 되지 않음
        mono
            .contextWrite(context -> context.put(key1, "Apple"))
            .subscribe(data -> log.info("# subscribe1 onNext: {}", data));

        mono
            .contextWrite(context -> context.put(key1, "Microsoft"))
            .subscribe(data -> log.info("# subscribe2 onNext: {}", data));

        Thread.sleep(100L);
    }

}
