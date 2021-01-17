package com.example.kafka;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;

import java.util.Collection;
import java.util.Map;

/**
 * 当发生在分区或者消费者关闭士
 */
public class HandleRebalance implements ConsumerRebalanceListener {
    private   Map<TopicPartition, OffsetAndMetadata> currentOffset ;
    private Consumer consumer;

    public HandleRebalance(Consumer consumer,Map<TopicPartition, OffsetAndMetadata> currentOffset ){
        this.consumer=consumer;
        this.currentOffset = currentOffset;
    }

    @Override
    public void onPartitionsRevoked(Collection<TopicPartition> collection) {
        System.out.println("save partition offset");

        System.out.println("Lost partitions in rebalance.Commit current offsets:"+currentOffset);
        consumer.commitSync(currentOffset);

    }

    @Override
    public void onPartitionsAssigned(Collection<TopicPartition> collection) {
        System.out.println("partition assigned");
    }
}
