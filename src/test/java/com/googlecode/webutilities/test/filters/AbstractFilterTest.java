package com.googlecode.webutilities.test.filters;

import com.googlecode.webutilities.test.AbstractWebComponentTest;
import com.mockrunner.servlet.ServletTestModule;

public abstract class AbstractFilterTest extends AbstractWebComponentTest {

    protected ServletTestModule servletTestModule;

    @Override
    protected void setupInitParam(String name, String value) {
        webMockObjectFactory.getMockFilterConfig().setInitParameter(name, value);
    }

    @Override
    protected void initModule() {
        servletTestModule = new ServletTestModule(webMockObjectFactory);
    }
}
