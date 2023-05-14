package com.github.ttl.manager.interfaces;

import com.github.ttl.manager.exceptions.RootAccessException;
import com.github.ttl.manager.exceptions.TTLOperationException;
import com.github.ttl.manager.exceptions.TTLValueException;

public interface ITTLModifier {
    /**
     * Get the current Time To Live (TTL) value.
     *
     * @return the current TTL value as an integer.
     * @throws RootAccessException if root access is not granted or not available.
     * @throws TTLValueException if the TTL value cannot be retrieved.
     */
    int getTTL() throws RootAccessException, TTLValueException, TTLOperationException;

    /**
     * Set a new Time To Live (TTL) value.
     *
     * @param ttl the new TTL value as an integer.
     * @throws RootAccessException if root access is not granted or not available.
     * @throws TTLValueException if the TTL value cannot be set or not changed.
     */
    void setTTL(int ttl) throws RootAccessException, TTLValueException, TTLOperationException;
}
