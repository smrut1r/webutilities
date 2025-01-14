/*
 * Copyright 2010-2014 Rajendra Patil
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.googlecode.webutilities.filters.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;

/**
 * Common AbstractFilter - infra filter code to be used by other filters
 * through inheritance
 * <p/>
 * This is to have following infra init parameters to all the filter
 * <p/>
 * - ignoreURLPattern - to ignore the URLs matching this regex
 * - acceptURLPattern - to process the URLs matching this regex (ignore precedes)
 * - ignoreMIMEPattern - to ignore if the response mime matches this regex
 * - acceptMIMEPattern - to process if the response mime matches this regex (ignore precedes)
 * - ignoreUAPattern - to ignore if request user agent name matches this regex
 * - acceptUAPattern - to process if request user agent name matches this regex
 * <p/>
 * This filter implements IgnoreAcceptContext with the help of above init parameters and provides
 * easy api for inherited filters to know if given req/res to be ignored or processes.
 *
 * @author rpatil
 * @version 1.0
 * @see IgnoreAcceptContext
 */
public abstract class AbstractFilter implements Filter, IgnoreAcceptContext {

    protected FilterConfig filterConfig;

    private String ignoreURLPattern;

    private String acceptURLPattern;

    private String ignoreMIMEPattern;

    private String acceptMIMEPattern;

    private String ignoreUAPattern;

    private String acceptUAPattern;

    private String ignoreQSPattern;

    private String acceptQSPattern;

    private static final String INIT_PARAM_IGNORE_URL_PATTERN = "ignoreURLPattern";

    private static final String INIT_PARAM_ACCEPT_URL_PATTERN = "acceptURLPattern";

    private static final String INIT_PARAM_IGNORE_MIME_PATTERN = "ignoreMIMEPattern";

    private static final String INIT_PARAM_ACCEPT_MIME_PATTERN = "acceptMIMEPattern";

    private static final String INIT_PARAM_IGNORE_UA_PATTERN = "ignoreUAPattern";

    private static final String INIT_PARAM_ACCEPT_UA_PATTERN = "acceptUAPattern";

    private static final String INIT_PARAM_IGNORE_QS_PATTERN = "ignoreQueryStringPattern";

    private static final String INIT_PARAM_ACCEPT_QS_PATTERN = "acceptQueryStringPattern";


    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractFilter.class.getName());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

        LOGGER.debug("Initializing...");

        this.filterConfig = filterConfig;

        this.ignoreURLPattern = filterConfig.getInitParameter(INIT_PARAM_IGNORE_URL_PATTERN);

        this.acceptURLPattern = filterConfig.getInitParameter(INIT_PARAM_ACCEPT_URL_PATTERN);

        this.ignoreMIMEPattern = filterConfig.getInitParameter(INIT_PARAM_IGNORE_MIME_PATTERN);

        this.acceptMIMEPattern = filterConfig.getInitParameter(INIT_PARAM_ACCEPT_MIME_PATTERN);

        this.ignoreUAPattern = filterConfig.getInitParameter(INIT_PARAM_IGNORE_UA_PATTERN);

        this.acceptUAPattern = filterConfig.getInitParameter(INIT_PARAM_ACCEPT_UA_PATTERN);

        this.ignoreQSPattern = filterConfig.getInitParameter(INIT_PARAM_IGNORE_QS_PATTERN);

        this.acceptQSPattern = filterConfig.getInitParameter(INIT_PARAM_ACCEPT_QS_PATTERN);

        LOGGER.debug("Abstract Filter initialized with: {\n\t{}:{},\n\t{}:{},\n\t{}:{},\n\t{}:{}\n\t{}:{},\n\t{}:{},\n" +
            "\t{}:{}\n}",
            INIT_PARAM_IGNORE_URL_PATTERN, ignoreURLPattern,
                INIT_PARAM_ACCEPT_URL_PATTERN, acceptURLPattern,
                INIT_PARAM_IGNORE_MIME_PATTERN, ignoreMIMEPattern,
                INIT_PARAM_ACCEPT_MIME_PATTERN, acceptMIMEPattern,
                INIT_PARAM_IGNORE_UA_PATTERN, ignoreUAPattern,
                INIT_PARAM_ACCEPT_UA_PATTERN, acceptUAPattern,
                INIT_PARAM_IGNORE_QS_PATTERN, ignoreQSPattern,
                INIT_PARAM_ACCEPT_QS_PATTERN, acceptQSPattern
        );
    }

    private boolean isURLIgnored(String url) {
        return this.ignoreURLPattern != null && url != null && url.matches(ignoreURLPattern);
    }

    public boolean isURLAccepted(String url) {
        return !this.isURLIgnored(url) && (this.acceptURLPattern == null || (url != null && url.matches(acceptURLPattern)));
    }

    private boolean isMIMEIgnored(String mimeType) {
        return this.ignoreMIMEPattern != null && mimeType != null && mimeType.matches(ignoreMIMEPattern);
    }

    public boolean isMIMEAccepted(String mimeType) {
        return !this.isMIMEIgnored(mimeType) && (this.acceptMIMEPattern == null || (mimeType != null && mimeType.matches(acceptMIMEPattern)));
    }

    private boolean isUserAgentIgnored(String userAgent) {
        return this.ignoreUAPattern != null && userAgent != null && userAgent.matches(ignoreUAPattern);
    }

    public boolean isUserAgentAccepted(String userAgent) {
        return !this.isUserAgentIgnored(userAgent) && (this.acceptUAPattern == null || (userAgent != null && userAgent.matches(acceptUAPattern)));
    }

    private boolean isQueryStringIgnored(String queryString) {
        return this.ignoreQSPattern != null && queryString != null && queryString.matches(ignoreQSPattern);
    }

    public boolean isQueryStringAccepted(String queryString) {
        return !this.isQueryStringIgnored(queryString) && (this.acceptQSPattern == null || (queryString != null && queryString.matches(acceptQSPattern)));
    }

  @Override
    public void destroy() {
        LOGGER.debug("destroying...");
        this.filterConfig = null;
    }

}

