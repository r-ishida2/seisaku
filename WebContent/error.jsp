<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="header.jsp" %>  <%-- タイトル + CSS --%>

<!-- メニューとコンテンツを横並びに配置するコンテナ -->
<div class="main-container">
    <!-- 左メニューエリア -->
    <div class="menu-wrapper">
        <%@ include file="side.jsp" %>
    </div>

    <!-- 右コンテンツエリア -->
    <p>${ error }</p>
</div>

<%@ include file="footer.jsp" %>
