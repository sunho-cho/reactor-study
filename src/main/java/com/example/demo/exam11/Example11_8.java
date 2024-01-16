package com.example.demo.exam11;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;
@Slf4j
public class Example11_8 {

	public static final String HEADER_AUTH_TOKEN = "authToken";

	public static void main(String[] args) {
		
		Mono<String> mono = postBook(Mono.just(
				new Book("abcd-1111-3533-2809", "Reactor's Bible", "Kevin"))
			)
			.contextWrite(Context.of(HEADER_AUTH_TOKEN, "eyJhbGciOi"));

		mono.subscribe(data -> log.info("# onNext: {}", data));
	}

	private static Mono<String> postBook(Mono<Book> book) {
		return Mono
				.zip(book, 
					Mono
						.deferContextual(ctx -> Mono.just(ctx.get(HEADER_AUTH_TOKEN)))
				)
				.flatMap(tuple -> {
					String response = "POST the book(" + tuple.getT1().getBookName() + 
					", " + tuple.getT1().getAuthor() + ") with token: " +
					tuple.getT2();
					return Mono.just(response); // HTTP POST 전송을 했다고 가정
				});
	}

}
