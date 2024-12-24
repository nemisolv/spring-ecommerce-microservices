package net.nemisolv.gateway.client;

import net.nemisolv.gateway.payload.IntrospectRequest;
import net.nemisolv.gateway.payload.IntrospectResponse;
import net.nemisolv.lib.payload.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PostExchange;
import reactor.core.publisher.Mono;

public interface IdentityClient {
    @PostExchange(url = "/auth/introspect", contentType = MediaType.APPLICATION_JSON_VALUE)
    Mono<ApiResponse<IntrospectResponse>> introspect(@RequestBody IntrospectRequest request);
}