package com.bigname.core.util;

/**
 * Created by Manu on 6/5/2018.
 */
public class Counter {

    public static final Counter ZERO = new Counter(0);
    public static final Counter ONE = new Counter(1);
    public static final Counter TEN = new Counter(10);
    public static final Counter HUNDRED = new Counter(100);

    private long i = 0;

    public Counter(long i) {
        this.i = i;
    }

    public Counter auto() {
        return new Counter(this.i);
    }

    public Counter clear() {
        i = 0;
        return this;
    }

    public Counter incr() {
        i ++;
        return this;
    }

    public Counter decr() {
        i --;
        return this;
    }

    public Counter add(int value) {
        i += value;
        return this;
    }

    public Counter add(Counter value) {
        i += value.i;
        return this;
    }

    public Counter subtract(int value) {
        i -= value;
        return this;
    }

    public Counter subtract(Counter value) {
        i -= value.i;
        return this;
    }

    public int intValue() {
        return (int)i;
    }

    public long longValue() {
        return i;
    }

    public String toString() {
        return Long.toString(i);
    }

    public boolean equals(Object that) {
        return that != null && that instanceof Counter && ((Counter) that).i == this.i;
    }
}
