package com.example.demo.exam11;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Slf4j
public class Example11_7 {

	public static void main(String[] args) throws InterruptedException {
		
		final String key1 = "company";

		Mono
			.just("Steve")
			//.transformDeferredContextual((stringMono, ctx) ->
			//	ctx.get("role")
			//)
			.flatMap(name ->
				Mono.deferContextual(ctx -> 
					Mono.just(ctx.get(key1) + ", " + name)
					.transformDeferredContextual((mono, innerCtx) -> 
						mono.map(data -> data + ", " + innerCtx.get("role"))
					)
					.contextWrite(context -> context.put("role", "CEO"))
				)
			)
			.publishOn(Schedulers.parallel())
			.contextWrite(context -> context.put(key1, "Apple"))
			.subscribe(data -> log.info("# onNext: {}", data));

		Thread.sleep(100L);
	}

}
