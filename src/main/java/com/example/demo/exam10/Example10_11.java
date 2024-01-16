package com.example.demo.exam10;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Slf4j
public class Example10_11 {

    public static void main(String[] args) throws InterruptedException {
        doTask("task1")
        .subscribe(data -> log.info("# onNext: {}", data));

        doTask("task2")
        .subscribe(data -> log.info("# onNext: {}", data));

        Thread.sleep(200L);
        
    }

    private static Flux<Integer> doTask(String taskName) {

        // newSingle 호출 할 때 마다 새로운 Thread 를 생성
        // true 는 Deamon Thread 로 동작하게 할 지 여부임
        // 주 Thread 가 종료되면 자동으로 종료

        return Flux.fromArray(new Integer[] {1, 3, 5, 7})
                .publishOn(Schedulers.newSingle("new-single", true))
                .filter(data -> data > 3)
                .doOnNext(data -> log.info("# {} doOnNext filter: {}", taskName, data))
                .map(data -> data * 10)
                .doOnNext(data -> log.info("# {} doOnNext map: {}", taskName, data));

    }

    // boundedElastic ExecutorService 기반의 Thread Pool 을 생성한 후 정해진 수만큼의 Thread 를 사용하고 반납
    // 실제 많은 데이터를 처리 할 때 Blocking I/O 작업을 효과적으로 처리하기 위한 방식

    // parallel : Non Bloking I/O 에 최적화 CPU 코어 수 만큼의 스레드를 생성

    // fromExecutorService : 기존에 사용하고 있는 ExecutorService 가 있다면 ExecutorService 로 부터 Scheudler 생성
    // Reactor 에서는 이방법 권장하지 않음

    // newXXXX : 다 호출될 때 마다 새로 생성
}
