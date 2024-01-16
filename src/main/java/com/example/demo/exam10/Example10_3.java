package com.example.demo.exam10;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Slf4j
public class Example10_3 {

    public static void main(String[] args) throws InterruptedException {

        Flux.fromArray(new Integer[] {1, 3, 5, 7, 9, 11, 13, 15, 17, 19})
                // subscribeOn, publishOn : 의 경우 동시성을 가지는 논리적인 스레드에 해당
                // parallel : 병렬성을 가지는 물리적인 Thread에 해당 ()
                // CPU의 Thread 에 분배 역할만  
                .parallel()
                // 실제 병렬로 작업을 수행할 스레드 할당은 runOn
                .runOn(Schedulers.parallel())
                .subscribe(data -> log.info("$ onNext: {}", data));

        Thread.sleep(100L);
    }

}
