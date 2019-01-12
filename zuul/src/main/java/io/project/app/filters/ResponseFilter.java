package io.project.app.filters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ResponseFilter extends ZuulFilter {

    private static final int FILTER_ORDER = 1;
    private static final boolean SHOULD_FILTER = true;

    @Override
    public String filterType() {
        return FilterUtils.POST_FILTER_TYPE;
    }

    @Override
    public int filterOrder() {
        return FILTER_ORDER;
    }

    @Override
    public boolean shouldFilter() {
        return SHOULD_FILTER;
    }

    @Override
    public Object run() {
        log.info("*************** RESPONSE STARTED **************** ");
        RequestContext ctx = RequestContext.getCurrentContext();

        log.info("HEADER NAMES " + ctx.getResponse().getHeaderNames().toString());

        ctx.getResponse().addHeader("Access-Control-Expose-Headers", "MP-AUTH-TOKEN");
        //ctx.getResponse().addHeader("Access-Control-Allow-Origin", "*");
        return ctx.getResponse();
    }
}
