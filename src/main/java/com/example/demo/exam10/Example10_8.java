package com.example.demo.exam10;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Slf4j
public class Example10_8 {

    public static void main(String[] args) throws InterruptedException {

        // subscribeOn, publishOn 을 함께 사용할 경우
        // boundedElastic: flux --> subscribeOn -->  filter --> publishOn --> parallel: map --> subscribe

        Flux.fromArray(new Integer[] {1, 3, 5, 7})
                // subscribeOn 은 구독이 발생한 직후에 실행될 스레드를 지정
                .subscribeOn(Schedulers.boundedElastic())
                .doOnNext(data -> log.info("# doOnNext fromArray: {}", data))
                .filter(data -> data > 3)
                .doOnNext(data -> log.info("# doOnNext filter: {}", data))
                .publishOn(Schedulers.parallel())
                .map(data -> data * 10)
                .doOnNext(data -> log.info("# doOnNext map: {}", data))
                .subscribe(data -> log.info("$ onNext: {}", data));

        Thread.sleep(500L);
    }

}
