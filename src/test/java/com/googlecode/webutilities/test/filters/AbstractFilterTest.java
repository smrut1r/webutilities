package com.googlecode.webutilities.test.filters;

import com.googlecode.webutilities.test.AbstractWebComponentTest;

public abstract class AbstractFilterTest extends AbstractWebComponentTest {
    @Override
    protected void setupInitParam(String name, String value) {
        webMockObjectFactory.getMockFilterConfig().setInitParameter(name, value);
    }
}
