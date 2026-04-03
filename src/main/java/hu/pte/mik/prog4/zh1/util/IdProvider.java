package hu.pte.mik.prog4.zh1.util;

import java.util.concurrent.atomic.AtomicLong;

public class IdProvider {
    private static final IdProvider instance = new IdProvider();

    private final AtomicLong id = new AtomicLong(1L);

    public IdProvider() {
    }

    public static IdProvider getInstance() {
        return instance;
    }

    public long getNewId(){
        return this.id.incrementAndGet();
    }
}
