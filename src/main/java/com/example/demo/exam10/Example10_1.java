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
				.subscribeOn(Schedulers.boundedElastic())
				.doOnNext(data -> log.info("# doOnNext: {}", data))
				.doOnSubscribe(subscribe -> log.info("# doOnSubscribe"))
				.subscribe(data -> log.info("# onNext: {}", data));

		Thread.sleep(500L);
	}

}
