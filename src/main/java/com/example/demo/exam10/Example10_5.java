package com.example.demo.exam10;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
public class Example10_5 {

    public static void main(String[] args) throws InterruptedException {

        // publishOn, subscribeOn 이 사용되지 않을 경우
        // flux --> filter --> map --> subscribe

        Flux.fromArray(new Integer[] {1, 3, 5, 7})
                .doOnNext(data -> log.info("# doOnNext fromArray: {}", data))
                .filter(data -> data > 3)
                .doOnNext(data -> log.info("# doOnNext filter: {}", data))
                .map(data -> data * 10)
                .doOnNext(data -> log.info("# doOnNext map: {}", data))
                .subscribe(data -> log.info("$ onNext: {}", data));
    }

}
