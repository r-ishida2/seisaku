<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="header.jsp" %>  <%-- タイトル + CSS --%>

<!-- メニューとコンテンツを横並びに配置するコンテナ -->
<div class="main-container">

    <!-- 右コンテンツエリア -->
    <form action="Login.action" method="post">
    <input type="text" name="id" placeholder="ID">
    <input type="text" name="password" placeholder="パスワード">
    <label for="showPassword">
    <input type="checkbox" id="showPassword" onclick="togglePasswordVisibility()" name="chk_d_ps">
    </label>
    <input type="submit" value="ログイン">
    </form>
</div>
<%@ include file="footer.jsp" %>
