package org.jasig.portlet.blackboardvcportlet.mvc.interceptor;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.handler.HandlerInterceptorAdapter;

@Component
public class WindowStateInterceptor extends HandlerInterceptorAdapter{
    
    @Override
    public void postHandleRender(RenderRequest request, RenderResponse response, Object handler, ModelAndView modelAndView) throws Exception{
        if ("detached".equals(request.getWindowState().toString())) {
           modelAndView.addObject("windowStateOverride", "detached");
        }
    }
}
