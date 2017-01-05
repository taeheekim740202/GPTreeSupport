package com.gptree.common.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.gptree.common.contants.PageUrls.*;
import static com.gptree.common.contants.SessionNames.*;

/**
 * GPTree Session Vaildation
 *
 * Created by Tae-Hee, Kim on 2016-12-29.
 */
public class SessionCheckFilter implements Filter {

    public void init(FilterConfig filterConfig) throws ServletException {
    }

    /**
     * Session이 없는 요청은 로그인 Page로 이동한다.
     *
     * @param servletRequest 서버 요청 객체
     * @param servletResponse 서버 응답 객체
     * @param filterChain 필터 체인
     * @throws IOException
     * @throws ServletException
     */
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        if (servletRequest instanceof HttpServletRequest) {
            HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
            HttpSession httpSession = httpServletRequest.getSession();

            if (validRequest(httpServletRequest) && (httpSession.isNew() || httpSession.getAttribute(SESSION_USER_INFOMATION) == null))
                httpServletRequest.getRequestDispatcher(PAGE_LOGIN_REQUEST).forward(servletRequest, servletResponse);
            else {
                filterChain.doFilter(servletRequest, servletResponse);
            }
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    /**
     * 유효한 요청인지 확인한다.
     *
     * @param request 요청정보
     * @return
     */
    private boolean validRequest(HttpServletRequest request) {
        if (request.getRequestURI() != null) {
            String requestUri = request.getRequestURI().toLowerCase();

            if (requestUri.indexOf("login.gptree") > -1)
                return false;
            else {
                if (requestUri.toLowerCase().endsWith("/")) return true;
                if (requestUri.toLowerCase().endsWith("html")) return true;
                if (requestUri.toLowerCase().endsWith("gptree")) return true;
                return false;
            }
        } else return false;
    }

    public void destroy() {
    }
}
