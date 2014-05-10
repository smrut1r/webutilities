package com.googlecode.webutilities.test.servlets;

import com.googlecode.webutilities.test.AbstractWebComponentTest;
import com.mockrunner.servlet.ServletTestModule;

public abstract class AbstractServletTest extends AbstractWebComponentTest {

    protected ServletTestModule servletTestModule;

    @Override
    protected void setupInitParam(String name, String value) {
        webMockObjectFactory.getMockServletConfig().setInitParameter(name, value);
    }

    @Override
    protected void initModule() {
        this.servletTestModule = new ServletTestModule(this.webMockObjectFactory);
    }
}
