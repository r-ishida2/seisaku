<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../header.jsp" %>
<div class="main-container">
	<div class="menu-wrapper">
        <%@ include file="../side.jsp" %>
    </div>
    <div class="content-container">
    <h2>学生情報登録</h2>
    <label>${message}</label>
    <a href="<c:url value='scoremanager/main/StudentList.action'/>">学生一覧</a>
    </div>
</div>
<%@ include file="../footer.jsp" %>