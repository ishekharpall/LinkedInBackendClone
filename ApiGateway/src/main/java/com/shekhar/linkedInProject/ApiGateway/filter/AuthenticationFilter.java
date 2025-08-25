package com.shekhar.linkedInProject.ApiGateway.filter;


import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

@Slf4j
@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    private final JwtService jwtService;
    public AuthenticationFilter(JwtService jwtService) {
        super(Config.class);
        this.jwtService=jwtService;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            log.info("Auth request: {}", exchange.getRequest().getURI());

            final String tokenHeader = exchange.getRequest().getHeaders().getFirst("Authorization");

            if(tokenHeader==null || !tokenHeader.startsWith("Bearer")){
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }
      final String token = tokenHeader.split("Bearer ")[1];
   try{
       String userId = jwtService.getUserIdFromToken(token);
       ServerWebExchange mutatedException = exchange.mutate()
               .request(r-> r.header("X-User-Id",userId))
               .build();
       return chain.filter(mutatedException);

   }catch(JwtException e){
       log.info("Jwt Exception {}",e.getLocalizedMessage());
       exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
       return exchange.getResponse().setComplete();
          }
        };
      }

    static class Config{

      }
}
