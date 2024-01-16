package com.example.demo.exam10;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Slf4j
public class Example10_4 {

    public static void main(String[] args) throws InterruptedException {

        Flux.fromArray(new Integer[] {1, 3, 5, 7, 9, 11, 13, 15, 17, 19})
                // 전부 사용할 필요가 없을 경우 사용할 Thread 갯수를 입력
                .parallel(3)
                .runOn(Schedulers.parallel())
                .subscribe(data -> log.info("$ onNext: {}", data));

        Thread.sleep(100L);
    }

}
