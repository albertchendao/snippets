package org.example.common.stream;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.function.Function;

/**
 * 可迭代流
 *
 * @param <T> 被迭代的类型
 */
public class IterableInputStream<T> extends InputStream {

    public static final int EOF = -1;

    private static final InputStream EOF_IS = new InputStream() {
        @Override
        public int read() throws IOException {
            return EOF;
        }
    };

    private final Iterator<T> iterator;
    private final Function<T, byte[]> mapper;

    private InputStream current;

    public IterableInputStream(Iterable<T> iterable, Function<T, byte[]> mapper) {
        this.iterator = iterable.iterator();
        this.mapper = mapper;
        next();
    }

    @Override
    public int read() throws IOException {
        int n = current.read();
        while (n == EOF && current != EOF_IS) {
            next();
            n = current.read();
        }
        return n;
    }

    private void next() {
        current = iterator.hasNext()
                ? new ByteArrayInputStream(mapper.apply(iterator.next()))
                : EOF_IS;
    }
}

