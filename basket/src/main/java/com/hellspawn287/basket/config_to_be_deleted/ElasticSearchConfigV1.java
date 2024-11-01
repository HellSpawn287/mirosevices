//package com.hellspawn287.basket.config;
//
//import co.elastic.clients.transport.TransportUtils;
//import lombok.SneakyThrows;
//import org.springframework.beans.factory.annotation.Configurable;
//import org.springframework.context.annotation.Primary;
//import org.springframework.data.elasticsearch.client.ClientConfiguration;
//import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
//import org.springframework.data.elasticsearch.client.elc.ReactiveElasticsearchConfiguration;
//
//import javax.net.ssl.SSLContext;
//import java.io.File;
//
//@Configurable
//@Primary
//public class ElasticSearchConfigV1 extends ReactiveElasticsearchConfiguration {
//
//    @SneakyThrows
//    @Override
//    public ClientConfiguration clientConfiguration() {
//        File certFile = new File("D:\\temp\\elasticsearch-8.13.1\\config\\certs\\http_ca.crt");
//        SSLContext sslContext = TransportUtils
//                .sslContextFromHttpCaCrt(certFile);
//
//        return ClientConfiguration.builder()
//                .connectedTo("localhost:9200")
//                .usingSsl(sslContext)
//                .withBasicAuth("elastic", "Qtjt+xOtSP=dVLkm60vY")
//                .build();
//    }
//}
