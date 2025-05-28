<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../header.jsp" %>
<div class="main-container">
	<div class="menu-wrapper">
        <%@ include file="../side.jsp" %>
    </div>
    <div class="content-container">
    <form action="scoremanager.main.SubjectCreateExecute.action" method="post">
	    <h2>科目情報登録</h2>
	    <label>科目コード</label>
	    <input type="text" name="cd" value="${cd}" required>
	    <label>科目名</label>
	    <input type="text" name="name" value="${name}" required>
		<input type="submit" value="登録して終了">
	    <c:if test="${not empty message}">
		    <p style="color:red;">${message}</p>
		</c:if>
    </form>
    <a href="<c:url value='scoremanager/main/SubjectList.action'/>">戻る</a>
    </div>
</div>
<%@ include file="../footer.jsp" %>