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
            // token 을 전달하기 위한 목적으로 Context 를 사용
            // Mono 가 어떤 과정을 거치든 상관없이 가장 마지막 리턴된 Mono 를 구독하기 직전에 contextWrite 로 데이터를 저장하기 때문에
            .contextWrite(Context.of(HEADER_AUTH_TOKEN, "eyJhbGciOi"));

        mono.subscribe(data -> log.info("# onNext: {}", data));
    }

    private static Mono<String> postBook(Mono<Book> book) {
        return Mono
                // 주어진 모노를 모든 주어진 모노가 아이템을 생산할 때 이행될 새로운 모노로 병합합니다.
                // 해당 값을 Tuple2로 집계합니다. 소스의 오류나 빈 완료로 인해 다른 소스가 발생하게 됩니다.
                // 취소되고 결과 Mono는 각각 즉시 오류가 발생하거나 완료됩니다.
                
                // 두개의 모노를 합쳐 다시 Context 를 읽어와 사용
                .zip(book, Mono.deferContextual(ctx -> Mono.just(ctx.get(HEADER_AUTH_TOKEN))))
                // 이 Mono에서 방출된 항목을 비동기적으로 변환하여 다른 Mono에서 방출된 값을 반환합니다.
                // (possibly changing the value type).
                .flatMap(tuple -> {
                    String response = "POST the book(" + tuple.getT1().getBookName() + 
                    ", " + tuple.getT1().getAuthor() + ") with token: " +
                    tuple.getT2();
                    return Mono.just(response); // HTTP POST 전송을 했다고 가정
                });
    }

    // Context 는 인증 정보 같은 직교성(독립성)을 가지는 정보를 전송하는데 적합

}
