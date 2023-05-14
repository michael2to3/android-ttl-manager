package com.github.ttl.manager.actions;

import com.github.ttl.manager.interfaces.ITTLModifier;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class TTLModifier implements ITTLModifier {

    public int getTTL() throws TTLValueException, RootAccessException {
        Process process = null;
        BufferedReader bufferedReader = null;

        try {
            process = Runtime.getRuntime().exec("su");
            DataOutputStream os = new DataOutputStream(process.getOutputStream());
            os.writeBytes("sysctl net.ipv4.ip_default_ttl\n");
            os.writeBytes("exit\n");
            os.flush();

            bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.contains("net.ipv4.ip_default_ttl")) {
                    String[] parts = line.split("=");
                    return Integer.parseInt(parts[1].trim());
                }
            }

            throw new TTLValueException("Failed to get TTL value");
        } catch (IOException e) {
            throw new RootAccessException("Root access not granted or not available", e);
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (process != null) {
                process.destroy();
            }
        }
    }

    public void setTTL(int ttl) throws TTLValueException, RootAccessException {
        Process process = null;

        try {
            process = Runtime.getRuntime().exec("su");
            DataOutputStream os = new DataOutputStream(process.getOutputStream());
            os.writeBytes("sysctl -w net.ipv4.ip_default_ttl=" + ttl + "\n");
            os.writeBytes("exit\n");
            os.flush();

            process.waitFor();
            if (process.exitValue() != 0) {
                throw new TTLValueException("Failed to set TTL value");
            }

            if (getTTL() != ttl) {
                throw new TTLValueException("TTL value not changed");
            }
        } catch (IOException e) {
            throw new RootAccessException("Root access not granted or not available", e);
        } finally {
            if (process != null) {
                process.destroy();
            }
        }
    }
}
