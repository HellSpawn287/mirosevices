//package com.hellspawn287.basket.config;
//
//import co.elastic.clients.elasticsearch.ElasticsearchClient;
//import co.elastic.clients.json.jackson.JacksonJsonpMapper;
//import co.elastic.clients.transport.ElasticsearchTransport;
//import co.elastic.clients.transport.TransportUtils;
//import co.elastic.clients.transport.rest_client.RestClientTransport;
//import org.apache.http.HttpHost;
//
//import org.apache.http.auth.AuthScope;
//import org.apache.http.auth.Credentials;
//import org.apache.http.auth.UsernamePasswordCredentials;
//import org.apache.http.client.CredentialsProvider;
//import org.apache.http.impl.client.BasicCredentialsProvider;
//import org.elasticsearch.client.RestClient;
//import org.springframework.beans.factory.annotation.Configurable;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Primary;
//
//import javax.net.ssl.SSLContext;
//import java.io.File;
//import java.io.IOException;
//
//@Configurable
//public class ElasticsearchConfig {
//
//    @Bean
//    @Primary
//    public ElasticsearchClient elasticsearchClient() throws IOException {
//        File certFile = new File("D:\\temp\\elasticsearch-8.13.1\\config\\certs\\http_ca.crt");
//
//        Credentials credentials = new UsernamePasswordCredentials("elastic", "Qtjt+xOtSP=dVLkm60vY");
//
//        SSLContext sslContext = TransportUtils
//                .sslContextFromHttpCaCrt(certFile);
//
//        CredentialsProvider credsProv = new BasicCredentialsProvider();
//        credsProv.setCredentials(
//                new AuthScope("localhost", 9200), credentials
//        );
//
//        RestClient restClient = RestClient
//                .builder(new HttpHost("localhost", 9200, "https"))
//                .setHttpClientConfigCallback(hc -> hc
//                        .setSSLContext(sslContext)
//                        .setDefaultCredentialsProvider(credsProv)
//                )
//                .build();
//
//// Create the transport and the API client
//        ElasticsearchTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());
//        return new ElasticsearchClient(transport);
//    }
//}
