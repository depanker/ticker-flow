package com.depanker.ticker.helper;

import com.depanker.ticker.exceptions.OperationFailedException;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

public interface FileWriter {
    default void writeToFile(String filePath, String data) throws IOException {
        try (RandomAccessFile file = new RandomAccessFile(filePath, "rw"); FileChannel channel = file.getChannel();) {
            if (isAppend()) {
                file.seek(file.length());
            }
            FileLock fl = channel.tryLock();
            if (fl != null) {
                ByteBuffer buf = ByteBuffer.allocate(1024);
                buf.clear();
                buf.put(data.getBytes());
                buf.flip();
                while (buf.hasRemaining()) {
                    channel.write(buf);
                }
                channel.force(true);
                fl.release();
            } else {
                throw new OperationFailedException(String.format("Unable to write file tick File=%s, data=%s", file,data));
            }
        }
    }

    default long getFileLength(String filePath) throws IOException {
        try(RandomAccessFile file = new RandomAccessFile(filePath, "r")) {
            return file.length();
        } catch (Throwable e) {
        }
        return 0;
    }
    boolean isAppend();
}
