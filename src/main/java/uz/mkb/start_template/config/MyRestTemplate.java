package uz.mkb.start_template.config;

import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.core5.http.HttpHost;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class MyRestTemplate {

    @Value("${proxy.host}")
    String proxy_host;

    @Value("${proxy.port}")
    int proxy_port;

    @Bean
    public RestTemplate restTemplate(){
        HttpHost httpHost = new HttpHost(proxy_host, proxy_port);
        HttpClientBuilder clientBuilder = HttpClientBuilder.create();
        clientBuilder.setProxy(httpHost);
        CloseableHttpClient httpClient = clientBuilder.build();
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(
                new HttpComponentsClientHttpRequestFactory(httpClient)
        );
        return restTemplate;
    }


}
