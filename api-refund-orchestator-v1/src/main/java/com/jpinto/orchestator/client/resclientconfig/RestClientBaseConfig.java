package com.jpinto.orchestator.client.resclientconfig;

//import com.mitocode.orchestrator.config.security.JwtPropagationInterceptor;
import io.micrometer.observation.ObservationRegistry;
//import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientBaseConfig {

    //Usamos @LoadBalanced para que esta RestClient.Builder pueda resolver nombres de servicios registrados en Eureka
    @Bean("loadBalancedRestClientBuilder")
    //@LoadBalanced
    public RestClient.Builder loadBalancedRestClientBuilder(
           // ObservationRegistry observationRegistry
           // ,         ObjectProvider<JwtPropagationInterceptor> jwtInterceptorProvider
    ) {

//        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
//        factory.setConnectTimeout(9000); //Cuánto tiempo espero para “conectarme” al servidor.
//        factory.setReadTimeout(9000); //Cuánto tiempo espero a que el servidor me responda después de conectarme.

        // ObservationRegistry es lo que realmente propaga el traceId
//        RestClient.Builder builder = RestClient.builder()
//                .requestFactory(factory)
//                .observationRegistry(observationRegistry)
                ;

    //    jwtInterceptorProvider.ifAvailable(builder::requestInterceptor);

        return RestClient.builder();
    }

    //Usamos @Primary para que esta sea la que se inyecte por defecto al cliente de Eureka
    @Primary
    @Bean
    public RestClient.Builder cleanRestClientBuilder() {
        return RestClient.builder();
    }
}
