package com.gptree.common.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import com.gptree.common.config.CommonConfigure;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * GPTree Session Vaildation
 *
 * Created by Tae-Hee, Kim on 2016-12-29.
 */
public class SessionCheckFilter implements Filter {

    /**
     * 환경파일
     */
    private CommonConfigure commonConfigure;

    /**
     * 로거
     */
    private Logger Log = LoggerFactory.getLogger(SessionCheckFilter.class);

    /**
     * 필터 초기화
     *
     * @param filterConfig
     * @throws ServletException
     */
    public void init(FilterConfig filterConfig) throws ServletException {
        // ignore
    }

    /**
     * Spring 환경파일 설정
     *
     * @param commonConfigure
     */
    public void setCommonConfigure(CommonConfigure commonConfigure)  {
        this.commonConfigure = commonConfigure;
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

            Log.debug("Request URI : {}", httpServletRequest.getRequestURI());
            Log.debug("Check Session : {}", validSession(httpSession));

            if ("/".equals(httpServletRequest.getRequestURI()) && validSession(httpSession)) {
                httpServletRequest.getRequestDispatcher(commonConfigure.getPageValue("PAGE_LOGIN_REQUEST")).forward(servletRequest, servletResponse);
            } else if (validRequest(httpServletRequest) && validSession(httpSession))
                httpServletRequest.getRequestDispatcher(commonConfigure.getPageValue("PAGE_LOGIN_REQUEST")).forward(servletRequest, servletResponse);
            else
                filterChain.doFilter(servletRequest, servletResponse);
        } else
            filterChain.doFilter(servletRequest, servletResponse);
    }

    /**
     * 유효한 Session이 설정되어 있는지 확인한다.
     *
     * @param session Session
     * @return 유효한 Session 여부
     */
    private boolean validSession(HttpSession session) {
        return session.isNew() || session.getAttribute(commonConfigure.getSessionName("SESSION_USER_INFOMATION")) == null;
    }

    /**
     * 유효한 요청인지 확인한다.
     *
     * @param request 요청정보
     * @return
     */
    private boolean validRequest(HttpServletRequest request) {
        if (request.getRequestURI() != null) {
            return (commonConfigure.getList("sessionCheckExtensions/sessionCheckExtension").contains(getUriExtension(request))
                && request.getRequestURI().indexOf(commonConfigure.getValue("sessionCheckExtensions/sessionCheckIgnoreUrl")) > -1);
        } else return false;
    }

    /**
     * 요청한 URL의 확장자를 가져온다.
     * (예) html
     *
     * @param request 요청 URL
     * @return 확장자
     */
    private String getUriExtension(HttpServletRequest request) {
        if (request.getRequestURI().indexOf(".") > -1) {
            String requestUri = request.getRequestURI().toLowerCase(); // Site의 파일명은 전부 소문자로 구성한다.
            return StringUtils.substringAfterLast(requestUri, ".");
        } else
            return null;
    }

    /**
     * 필터 종료 시 설정
     */
    public void destroy() {
        // ignore
    }
}
