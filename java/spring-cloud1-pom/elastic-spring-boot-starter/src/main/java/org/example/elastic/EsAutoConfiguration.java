package org.example.elastic;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Slf4j
@Configuration
@ConditionalOnProperty(name = "elastic.enable", havingValue = "true")
@EnableConfigurationProperties(EsProperties.class)
public class EsAutoConfiguration {

    @Bean(destroyMethod = "close")
    public RestHighLevelClient restHighLevelClient(EsProperties esProperties) {
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        final Long keepAlive = esProperties.getKeepAlive();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(esProperties.getUsername(), esProperties.getPassword()));
        final List<String> ips = esProperties.getIps();
        HttpHost[] hosts = new HttpHost[ips.size()];
        for (int i = 0; i < ips.size(); i++) {
            hosts[i] = new HttpHost(ips.get(i), esProperties.getPort());
        }
        final RestClientBuilder builder = RestClient.builder(hosts)
                .setHttpClientConfigCallback(httpClientBuilder -> {
                    httpClientBuilder.disableAuthCaching();
                    httpClientBuilder.setKeepAliveStrategy((response, context) -> {
                        long result = DefaultConnectionKeepAliveStrategy.INSTANCE.getKeepAliveDuration(response, context);
                        if (result == -1 && keepAlive != null) result = keepAlive;
                        return result;
                    });
                    return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
                });
        RestHighLevelClient client = new RestHighLevelClient(builder);
        log.info("Successfully connect to ElasticSearch: {}", esProperties.getIps());
        return client;
    }

    @Bean
    public EsHelper esHelper(RestHighLevelClient restHighLevelClient) {
        return new EsHelper(restHighLevelClient);
    }

}
