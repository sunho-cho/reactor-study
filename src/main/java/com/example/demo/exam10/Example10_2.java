package com.example.demo.exam10;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Slf4j
public class Example10_2 {

    public static void main(String[] args) throws InterruptedException {

        Flux.fromArray(new Integer[] {1, 3, 5, 7})
                .doOnNext(data -> log.info("# doOnNext: {}", data))
                .doOnSubscribe(subscribe -> log.info("# doOnSubscribe"))
                // Downstream 으로 Signal 을 전송할 때 실행되는 Thread 를 제어하는 역할을 하는 Operator
                // publishOn 을 기준으로 아래쪽인 Downstream 의 실행 Thread 를 변경
                .publishOn(Schedulers.parallel())
                .subscribe(data -> log.info("# onNext: {}", data));

        Thread.sleep(500L);
    }

}
