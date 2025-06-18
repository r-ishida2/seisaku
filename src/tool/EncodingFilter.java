package tool;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

@WebFilter(urlPatterns={"/*"})
//@WebFilter(urlPatterns={"/chapter10/filter-sample"})
public class EncodingFilter implements Filter {

	@Override
	public void doFilter(
		ServletRequest request, ServletResponse response,
		FilterChain chain
	) throws IOException, ServletException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		System.out.println("フィルタの前処理");
		chain.doFilter(request, response);
		System.out.println("フィルタの後処理");

//        HttpServletRequest req = (HttpServletRequest) request;
//        HttpServletResponse res = (HttpServletResponse) response;
//        // HTML にフィルタの前処理を表示
//        PrintWriter out = res.getWriter();
//        out.println("<p style='color:red;'>[フィルタ] 前処理: " + req.getRequestURI() + "</p>");
//        out.flush(); // すぐにブラウザに表示
//
//        // chain.doFilter() で次の処理 (index.html など) へ進む
//        chain.doFilter(request, response);
//
//        res.flushBuffer();
//
//
//        // ★ フィルタの後処理を 2 秒遅延
//        try {
//            Thread.sleep(2000); // 2秒待機
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        // HTML にフィルタの後処理を表示
//        out.println("<p style='color:blue;'>[フィルタ] 後処理: " + req.getRequestURI() + "</p>");
//        out.flush(); // すぐにブラウザに表示

	}

	@Override
	public void init(FilterConfig filterConfig) {}
	@Override
	public void destroy() {}
}
