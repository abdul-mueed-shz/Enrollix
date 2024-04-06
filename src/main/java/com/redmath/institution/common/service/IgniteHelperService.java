package com.redmath.institution.common.service;

import lombok.RequiredArgsConstructor;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteAtomicSequence;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IgniteHelperService {

    private final Ignite ignite;

    /**
     * Increment atomic long on local node.
     *
     * @param cacheName Atomic long name
     * @return the igniteAtomicLong
     */
    public long getIgniteAtomicLongSequence(String cacheName) {
        IgniteAtomicSequence igniteAtomicSequence = ignite.atomicSequence(cacheName.concat("_Sequence"), 0, true);
        return igniteAtomicSequence.incrementAndGet();
    }

}
