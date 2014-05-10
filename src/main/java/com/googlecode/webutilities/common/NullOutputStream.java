package com.googlecode.webutilities.common;

import java.io.IOException;
import java.io.OutputStream;

public class NullOutputStream extends OutputStream {

    @Override
    public void write(int i) throws IOException {
    }

    @Override
    public void write(byte[] bytes) throws java.io.IOException {
    }

    @Override
    public void write(byte[] bytes, int i, int i1) throws java.io.IOException {
    }

}
