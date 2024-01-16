package com.example.demo.exam10;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Slf4j
public class Example10_9 {

    public static void main(String[] args) throws InterruptedException {

        // immediate 별도의 Thread 를 추가적으로 생성하지 않고 현재 Thread 작업
        // 케이스가 있다함.... 암튼 그럼

        Flux.fromArray(new Integer[] {1, 3, 5, 7})
                .publishOn(Schedulers.parallel())        
                .filter(data -> data > 3)
                .doOnNext(data -> log.info("# doOnNext filter: {}", data))
                .publishOn(Schedulers.immediate())
                .map(data -> data * 10)
                .doOnNext(data -> log.info("# doOnNext map: {}", data))
                .subscribe(data -> log.info("$ onNext: {}", data));

        Thread.sleep(500L);
    }

}
