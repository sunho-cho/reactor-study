package com.example.demo.exam10;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Slf4j
public class Example10_10 {

    public static void main(String[] args) throws InterruptedException {
        doTask("task1")
        .subscribe(data -> log.info("# onNext: {}", data));

        doTask("task2")
        .subscribe(data -> log.info("# onNext: {}", data));

        Thread.sleep(200L);
        
    }

    private static Flux<Integer> doTask(String taskName) {

        // single 은 스레드 하나만 생성해서 Scheduler 가 제거 되기 전까지 재사용
        // 하나의 Thread 로 다수의 작업을 처리해야 하므로 지연시간이 짧은 작업을 처리하는 것이 효과적

        return Flux.fromArray(new Integer[] {1, 3, 5, 7})
                .publishOn(Schedulers.single())
                .filter(data -> data > 3)
                .doOnNext(data -> log.info("# {} doOnNext filter: {}", taskName, data))
                .map(data -> data * 10)
                .doOnNext(data -> log.info("# {} doOnNext map: {}", taskName, data));

    }

}
