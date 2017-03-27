package com.mark.disruptor;

import java.nio.ByteBuffer;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;


/**
 * Author: Mark
 * Date  : 2017/3/27
 */
public class DisruptorDemo {

    static class LongEvent {
        private Long value;

        public void setValue(Long value) {
            this.value = value;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("LongEvent{");
            sb.append("value=").append(value);
            sb.append('}');
            return sb.toString();
        }
    }

    static class LongEventFactory implements EventFactory<LongEvent> {

        @Override
        public LongEvent newInstance() {
            return new LongEvent();
        }
    }

    static class LongEventHandler implements EventHandler<LongEvent> {
        @Override
        public void onEvent(LongEvent event, long sequence, boolean endOfBatch) throws Exception {
            System.out.println("Event: " + event);
        }
    }

    static class LongEventProducer {
        private final RingBuffer<LongEvent> ringBuffer;

        LongEventProducer(RingBuffer<LongEvent> ringBuffer) {
            this.ringBuffer = ringBuffer;
        }

        public void onData(ByteBuffer byteBuffer) {
            final long sequence = ringBuffer.next();
            try {
                final LongEvent longEvent = ringBuffer.get(sequence);
                longEvent.setValue(byteBuffer.getLong(0));
            } finally {
                ringBuffer.publish(sequence);
            }

        }
    }

    public static void main(String[] args) throws InterruptedException {
        LongEventFactory factory = new LongEventFactory();
        int bufferSize = 1024;
        final Disruptor<LongEvent> disruptor = new Disruptor<>(factory, bufferSize, Executors.defaultThreadFactory());
        disruptor.handleEventsWith(new LongEventHandler());
        disruptor.start();
        final RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();
        final LongEventProducer producer = new LongEventProducer(ringBuffer);
        final ByteBuffer buffer = ByteBuffer.allocate(8);
        for (long i = 0; i < 1_000_000; i++) {
            buffer.putLong(0, i);
            producer.onData(buffer);
//            TimeUnit.SECONDS.sleep(1);
        }
    }

}
