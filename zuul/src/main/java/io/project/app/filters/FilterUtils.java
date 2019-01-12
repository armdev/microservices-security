package io.project.app.filters;

import com.netflix.zuul.context.RequestContext;
import org.springframework.stereotype.Component;

@Component
public class FilterUtils {

    public static final String PRE_FILTER_TYPE = "pre";
    public static final String POST_FILTER_TYPE = "post";
    public static final String ROUTE_FILTER_TYPE = "route";
    public static final String ZUUL_TOKEN = "zuul-token";

    public final String getHeaderByName(String headerName) {
        RequestContext ctx = RequestContext.getCurrentContext();
        if (ctx.getRequest().getHeader(headerName) != null) {
            return ctx.getRequest().getHeader(headerName);
        } else {
            return ctx.getZuulRequestHeaders().get(headerName);
        }
    }

}
