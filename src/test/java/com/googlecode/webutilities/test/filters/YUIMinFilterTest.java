/*
 * Copyright 2010-2011 Rajendra Patil
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.googlecode.webutilities.test.filters;

import com.googlecode.webutilities.filters.YUIMinFilter;
import com.googlecode.webutilities.servlets.JSCSSMergeServlet;
import com.googlecode.webutilities.test.AbstractWebComponentTest;
import com.mockrunner.servlet.ServletTestModule;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class YUIMinFilterTest extends AbstractFilterTest {

    private JSCSSMergeServlet jscssMergeServlet = new JSCSSMergeServlet();

    private YUIMinFilter yuiMinFilter = new YUIMinFilter();

    private ServletTestModule servletTestModule;

    private static final Logger LOGGER = LoggerFactory.getLogger(YUIMinFilterTest.class.getName());

    @Override
    protected String getTestPropertiesName() {
        return YUIMinFilterTest.class.getSimpleName() + ".properties";
    }

    @Override
    public void prepare() {

        servletTestModule = new ServletTestModule(webMockObjectFactory);

        servletTestModule.setServlet(jscssMergeServlet, true);

        servletTestModule.addFilter(yuiMinFilter, true);
        servletTestModule.setDoChain(true);

    }

    @Override
    public void executeCurrentTestLogic() throws Exception {
        servletTestModule.doFilter();

        String actualOutput = servletTestModule.getOutput();

        Assert.assertNotNull(actualOutput);

        String expectedOutput = this.getExpectedOutput();

        Assert.assertEquals(expectedOutput.trim(), actualOutput.trim());

        Assert.assertEquals("" + actualOutput.length(), webMockObjectFactory.getMockResponse().getHeader("Content-Length"));
    }

}
