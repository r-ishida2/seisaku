<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../header.jsp" %>  <%-- タイトル + CSS --%>

<!-- メニューとコンテンツを横並びに配置するコンテナ -->
<div class="main-container">

    <!-- 右コンテンツエリア -->
    <p>${MESSEGE}</p>
    <form action="scoremanager.LoginExecute.action" method="post">
    	<input type="text" name="ID" placeholder="ID">
    	<input type="password" name="PASSWORD" placeholder="パスワード" id="passwordField">
    	<label for="showPassword">
            <input type="checkbox" id="showPassword" onclick="togglePasswordVisibility()" name="chk_d_ps">
            パスワードを表示
        </label>
    	<input type="submit" value="ログイン">
    </form>
</div>

<!-- パスワード表示切り替えスクリプト -->
<script>
function togglePasswordVisibility() {
    const pwField = document.getElementById("passwordField");
    pwField.type = pwField.type === "password" ? "text" : "password";
}
</script>

<%@ include file="../footer.jsp" %>
