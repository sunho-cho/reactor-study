package com.example.demo.exam10;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Slf4j
public class Example10_1 {

    // Scheduler 는 Reactor Sequence 에서 사용되는 Thread 를 관리해 주는 관리자 역할
    // Java 프로그래밍에서 생성되는 것은 물리적 Thread 를 나눈 논리적 Thread
    // 물리적 Thread = 병렬성 Parallelism
    // 논리적 Thread = 동시성 Concurrency (동시에 실행되는 것처럼 보이는 것을 의미 물리적 Thread 를 번갈아 가면서 실행 하여)
    //                                  물리적 Thread 갯수 내에서

    public static void main(String[] args) throws InterruptedException {

        Flux.fromArray(new Integer[] {1, 3, 5, 7})
                // 구독이 발생한 직후 실행될 Thread 지정
                // Schedulers 를 지정
                .subscribeOn(Schedulers.boundedElastic())
                // Flux 에 emit 되는 데이터 로그를 찍기
                .doOnNext(data -> log.info("# doOnNext: {}", data))
                // 구독이 발행한 시점에 추가적인 어떤 처리가 필요한 경우 사용
                // 구독이 발생한 시점에 실행되는 Thread 확인용
                .doOnSubscribe(subscribe -> log.info("# doOnSubscribe")) // 최초 실행으로 main Thread로 실행
                .subscribe(data -> log.info("# onNext: {}", data));

        Thread.sleep(500L);
    }

}
