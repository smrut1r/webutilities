package com.googlecode.webutilities.test.servlets;

import com.googlecode.webutilities.test.AbstractWebComponentTest;

public abstract class AbstractServletTest extends AbstractWebComponentTest {
    @Override
    protected void setupInitParam(String name, String value) {
        webMockObjectFactory.getMockServletConfig().setInitParameter(name, value);
    }
}
