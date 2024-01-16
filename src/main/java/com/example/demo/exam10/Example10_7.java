package com.example.demo.exam10;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Slf4j
public class Example10_7 {

    public static void main(String[] args) throws InterruptedException {

        // 두개 이상의 publishOn 을 사용할 경우
        // flux --> publishOn -->  New Thread2: filter --> publishOn --> new Thread1: map --> subscribe

        Flux.fromArray(new Integer[] {1, 3, 5, 7})
                .doOnNext(data -> log.info("# doOnNext fromArray: {}", data))
                .publishOn(Schedulers.parallel())
                .filter(data -> data > 3)
                .doOnNext(data -> log.info("# doOnNext filter: {}", data))
                .publishOn(Schedulers.parallel())
                .map(data -> data * 10)
                .doOnNext(data -> log.info("# doOnNext map: {}", data))
                .subscribe(data -> log.info("$ onNext: {}", data));

        Thread.sleep(500L);
    }

}
