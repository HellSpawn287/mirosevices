package com.hellspawn287.statistics.listener;

import com.hellspawn287.avro.ProductBasket;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.generic.GenericData;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class StatisticListener {

    @KafkaListener(topics = "products-basket", groupId = "statistics")
    public void handleStatisticsMessage(GenericData.Record message) {
        log.info("[StatisticListener] message: {}", message.toString());
        ProductBasket productBasket = ProductBasket.newBuilder()
                .setProductID((CharSequence) message.get("productID"))
                .setProductQuantity((Integer) message.get("productQuantity"))
                .setOwner((CharSequence) message.get("owner"))
                .build();
        log.info("[ProductBasket]:  {}", productBasket.toString());
    }
}
