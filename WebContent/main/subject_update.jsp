<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="../header.jsp" %>  <%-- タイトル + CSS --%>

<!-- メニューとコンテンツを横並びに配置するコンテナ -->
<div class="main-container">
    <!-- 左メニューエリア -->
    <div class="menu-wrapper">
        <%@ include file="../side.jsp" %>
    </div>

    <!-- 右コンテンツエリア -->
    <div class="content-container">
    <form action="SubjectUpdateExecute.action" method="post">
    	<label>入学年度</label>
		<a>${subject.cd}</a>
		<input type="hidden" name="cd" value="${subject.cd}">
		<label>氏名</label>
		<input type="text" name="name" value="${subject.name}" required>

		<input type="submit" value="変更">
	</form>
    </div>
</div>

<%@ include file="../footer.jsp" %>
