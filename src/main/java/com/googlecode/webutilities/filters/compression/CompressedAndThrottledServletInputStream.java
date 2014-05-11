/*
 * Copyright 2010-2014 Rajendra Patil
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.googlecode.webutilities.filters.compression;

import com.googlecode.webutilities.common.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletInputStream;
import java.io.IOException;
import java.io.InputStream;

final class CompressedAndThrottledServletInputStream extends ServletInputStream {

    private static final Logger LOGGER = LoggerFactory.getLogger(CompressedAndThrottledServletInputStream.class.getName());

    private static final long SLEEP_DURATION_MS = 50;

    private final InputStream compressedStream;

    private boolean closed;

    private long readRatePerSecond;

    private final long startTime;

    private long bytesRead = 0;

    CompressedAndThrottledServletInputStream(InputStream inputStream, EncodedStreamsFactory encodedStreamsFactory,
                                             long allowedBytesPerSecond) throws IOException {
        this.readRatePerSecond = allowedBytesPerSecond > 0 ? allowedBytesPerSecond : Constants.DEFAULT_DECOMPRESS_BYTES_PER_SECOND;
        this.compressedStream = encodedStreamsFactory.getCompressedStream(inputStream).getCompressedInputStream();
        this.startTime = System.currentTimeMillis();
    }

    public int read() throws IOException {
        assertOpen();
        throttle();
        int count = compressedStream.read();
        if(count > 0) {
          bytesRead += count;
        }
        return count;
    }

    private void throttle() throws IOException {
        if (getReadRate() > this.readRatePerSecond) {
            try {
                Thread.sleep(SLEEP_DURATION_MS);
            } catch (InterruptedException e) {
                throw new IOException("Thread aborted", e);
            }
        }
    }
    public long getReadRate() {
        long elapsed = (System.currentTimeMillis() - startTime) / 1000;
        if (elapsed == 0) {
            return bytesRead;
        } else {
            return bytesRead / elapsed;
        }
    }
    public int read(byte[] b) throws IOException {
        assertOpen();
        throttle();
        int count = compressedStream.read(b);
        if(count > 0) {
            bytesRead += count;
        }
        return count;
    }
    public int read(byte[] b, int offset, int length) throws IOException {
        assertOpen();
        throttle();
        int count = compressedStream.read(b, offset, length);
        if(count > 0) {
            bytesRead += count;
        }
        return count;
    }

    public long skip(long n) throws IOException {
        assertOpen();
        return compressedStream.skip(n);
    }

    public int available() throws IOException {
        assertOpen();
        return compressedStream.available();
    }

    public void close() throws IOException {
        if (!closed) {
            compressedStream.close();
            closed = true;
        }
      long elapsedSecondsSinceStart = (System.currentTimeMillis() - startTime) / 1000;
      LOGGER.debug("Finished reading {} bytes in {} seconds. (average rate: {})", bytesRead, elapsedSecondsSinceStart, getReadRate());
    }

    public synchronized void mark(int limit) {
        assertOpen();
        compressedStream.mark(limit);
    }

    public synchronized void reset() throws IOException {
        assertOpen();
        compressedStream.reset();
    }

    public boolean markSupported() {
        assertOpen();
        return compressedStream.markSupported();
    }

    private void assertOpen() {
        if (closed) {
            throw new IllegalStateException("Stream has been already closed.");
        }
    }

}
