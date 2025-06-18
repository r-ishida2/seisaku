<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../header.jsp" %>

<div class="logout-container">
    <h2>ログアウト</h2>
    <p class="logout-message">ログアウトしました</p>
    <a class="login-link" href="<c:url value='/scoremanager.Login.action'/>">ログイン</a>
</div>

<%@ include file="../footer.jsp" %>
