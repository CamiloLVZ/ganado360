package com.ganado.gateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Configuration
public class WebClientConfig {

    @Value("${gateway.internal.secret:Ganado360InternalSecretKey2026}")
    private String internalSecret;

    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder()
                .filter((request, next) -> Mono.deferContextual(ctx -> {
                    ClientRequest.Builder builder = ClientRequest.from(request)
                            .header("X-Internal-Gateway-Token", internalSecret);

                    if (ctx.hasKey("authUserId")) {
                        String userId = ctx.get("authUserId");
                        builder.header("X-Auth-User-Id", userId);
                    }
                    if (ctx.hasKey("authUserEmail")) {
                        String userEmail = ctx.get("authUserEmail");
                        builder.header("X-Auth-User-Email", userEmail);
                    }

                    return next.exchange(builder.build());
                }));
    }
}
