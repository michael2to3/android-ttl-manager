package com.github.ttl.manager.actions;

import com.github.ttl.manager.exceptions.RootAccessException;
import com.github.ttl.manager.exceptions.TTLOperationException;
import com.github.ttl.manager.exceptions.TTLValueException;
import com.github.ttl.manager.interfaces.ITTLModifier;

import java.io.IOException;

public class TTLModifier implements ITTLModifier {
    final ConsoleStream consoleStream;

    @Deprecated
    TTLModifier() {
        try {
            consoleStream = ConsoleStream.createConsoleStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    TTLModifier(final ConsoleStream stream) {
        consoleStream = stream;
    }

    public int getTTL() throws RootAccessException, TTLValueException {
        final String output;
        try {
            consoleStream.exec("su");
            output = consoleStream.exec("sysctl net.ipv4.ip_default_ttl\n");
        } catch (IOException | InterruptedException e) {
            throw new RootAccessException("Try get ttl value", e);
        }

        if (output.contains("net.ipv4.ip_default_ttl")) {
            String[] parts = line.split("=");
            return Integer.parseInt(parts[1].trim());
        }

        throw new TTLValueException("Failed to get TTL value");
    }

    public void setTTL(final int ttl) throws RootAccessException, TTLValueException, TTLOperationException {
        try {
            consoleStream.exec("sysctl -w net.ipv4.ip_default_ttl=" + ttl);
        } catch (IOException e) {
            throw new RootAccessException("Root access not granted or not available", e);
        } catch (InterruptedException e) {
            throw new TTLOperationException("Operation interrupted", e);
        }
    }
}
