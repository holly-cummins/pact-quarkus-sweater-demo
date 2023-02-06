package org.sheepy.observer.runtime;

import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class LogHandler extends Handler {
    public static final String LOG_PREFIX = "\uD83C\uDF99️ [Observer extension]";
    private final RecorderService recorder;

    public LogHandler(RecorderService recorder) {
        this.recorder = recorder;
    }

    @Override
    public void publish(LogRecord record) {
        String formattedMessage = String.format(record.getMessage(), record.getParameters());

        if (formattedMessage.contains(LOG_PREFIX)) {
            recorder.log(formattedMessage);
        }

    }

    @Override
    public void flush() {

    }

    @Override
    public void close() throws SecurityException {

    }
}

