<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../header.jsp" %>
<div class="main-container">
	<div class="menu-wrapper">
        <%@ include file="../side.jsp" %>
    </div>
    <div class="content-container">
    <form action="scoremanager.main.StudentCreateExecute.action" method="post">
	    <h2>学生情報登録</h2>
	    <label>入学年度</label>
		<select name="entYear" required>
		    <option value="">---------</option>
		    <c:forEach var="year" items="${entYears}">
		        <option value="${year}" <c:if test="${ent_year == year}">selected</c:if>>${year}</option>
		    </c:forEach>
		</select>
	    <label>学生番号</label>
	    <input type="text" name="no" value="${no}" required>
	    <label>氏名</label>
	    <input type="text" name="name" value="${name}" required>
	    <label>クラス</label>
		<select name="classNum" required>
		    <option value="">--------</option>
		    <c:forEach var="cn" items="${classNums}">
		        <option value="${cn}" <c:if test="${class_num == cn}">selected</c:if>>${cn}</option>
		    </c:forEach>
		</select>
		<input type="hidden" name="isAttend" value="true">
		<input type="hidden" name="Cd" value="SCH001">
		<input type="submit" value="登録して終了">
	    <c:if test="${not empty message}">
		    <p style="color:red;">${message}</p>
		</c:if>
    </form>
    <a href="<c:url value='/scoremanager/main/StudentList.action'/>">戻る</a>
    </div>
</div>
<%@ include file="../footer.jsp" %>