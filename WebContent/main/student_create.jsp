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
	    	<option value="">-----</option>
	    	<option value="2021" <c:if test="${entYear == '2021'}">selected</c:if>>2021</option>
			<option value="2022" <c:if test="${entYear == '2022'}">selected</c:if>>2022</option>
			<option value="2023" <c:if test="${entYear == '2023'}">selected</c:if>>2023</option>
			<option value="2024" <c:if test="${entYear == '2024'}">selected</c:if>>2024</option>
			<option value="2025" <c:if test="${entYear == '2025'}">selected</c:if>>2025</option>
	    </select>
	    <label>学生番号</label>
	    <input type="text" name="no" value="${no}" required>
	    <label>氏名</label>
	    <input type="text" name="name" value="${name}" required>
	    <label>クラス</label>
	    <select name="classNum" required>
		  <option value="">----</option>
		  <option value="201" <c:if test="${classNum == '201'}">selected</c:if>>201</option>
		  <option value="202" <c:if test="${classNum == '202'}">selected</c:if>>202</option>
		  <option value="203" <c:if test="${classNum == '203'}">selected</c:if>>203</option>
		  <option value="204" <c:if test="${classNum == '204'}">selected</c:if>>204</option>
		  <option value="205" <c:if test="${classNum == '205'}">selected</c:if>>205</option>
		</select>
		<input type="hidden" name="isAttend" value="true">
		<input type="hidden" name="Cd" value="SCH001">
		<input type="submit" value="登録して終了">
	    <c:if test="${not empty message}">
		    <p style="color:red;">${message}</p>
		</c:if>
    </form>
    <a href="<c:url value='scoremanager/main/StudentList.action'/>">戻る</a>
    </div>
</div>
<%@ include file="../footer.jsp" %>