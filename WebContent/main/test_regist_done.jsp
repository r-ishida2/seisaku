<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../header.jsp" %>
<div class="main-container">
	<div class="menu-wrapper">
        <%@ include file="../side.jsp" %>
    </div>
    <div class="content-container">
    <h2>成績管理</h2>
    <label>${message}</label>
    <a href="<c:url value='/scoremanager/main/SubjectList.action'/>">戻る</a>
    <a href="<c:url value='/scoremanager/main/TestList.action'/>">成績参照</a>
    </div>
</div>
<%@ include file="../footer.jsp" %>
