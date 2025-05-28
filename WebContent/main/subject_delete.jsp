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
    <h2>科目情報削除</h2>
    <form action="SubjectDeleteExecute.action" method="post">
		<a>「${subject.name}（${subject.cd}）」を削除してもよろしいですか？</a>
		<input type="submit" value="削除">
	</form>
	<a href="<c:url value='/scoremanager/main/SubjectList.action'/>">戻る</a>
    </div>
</div>

<%@ include file="../footer.jsp" %>
