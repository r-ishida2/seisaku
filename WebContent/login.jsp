<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../header.jsp" %>

<div class="login-wrapper">
  <div class="login-card">
    <h2>ログイン</h2>

    <p style="color:red;">${MESSEGE}</p>

    <form action="scoremanager.LoginExecute.action" method="post">
      <input type="text" name="ID" placeholder="ID" required>

      <input type="password" name="PASSWORD" placeholder="パスワード" id="passwordField" required>

      <div class="checkbox-wrapper">
        <input type="checkbox" id="showPassword" onclick="togglePasswordVisibility()" name="chk_d_ps">
        <label for="showPassword">パスワードを表示</label>
      </div>

      <input type="submit" value="ログイン" class="login-button">
    </form>
  </div>
</div>

<script>
function togglePasswordVisibility() {
    const pwField = document.getElementById("passwordField");
    pwField.type = pwField.type === "password" ? "text" : "password";
}
</script>

<%@ include file="../footer.jsp" %>
