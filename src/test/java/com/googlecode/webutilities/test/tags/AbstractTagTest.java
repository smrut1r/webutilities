package com.googlecode.webutilities.test.tags;

import com.googlecode.webutilities.test.AbstractWebComponentTest;
import com.mockrunner.tag.TagTestModule;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractTagTest extends AbstractWebComponentTest {

    protected TagTestModule tagTestModule;

    protected Map<Object, Object> attributeMap = new HashMap<Object, Object>();

    @Override
    protected void setupInitParam(String name, String value) {
        attributeMap.put(name, value);
    }

    @Override
    public void setupInitParams() {
        String value = properties.getProperty(this.currentTestNumber + ".test.init.params");
        if (value != null && !value.trim().equals("")) {
            String[] params = value.split(",");
            for (String param : params) {
                String[] keyAndValue = param.split(":");
                this.setupInitParam(keyAndValue[0], keyAndValue[1]);
            }
        }
    }

    @Override
    public void setupResources() {
        //Do nothing to set resources
    }

    @Override
    public void prepare() throws Exception {
        this.setupTag();
        this.setupTagBodyContent();
    }

    @Override
    protected void initModule() {
        tagTestModule = new TagTestModule(webMockObjectFactory);
    }

    public abstract void setupTag();

    public abstract void setupTagBodyContent() throws Exception;

}
