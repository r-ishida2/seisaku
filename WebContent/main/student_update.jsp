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
    <form action="StudentUpdateExecute.action" method="post">
    	<label>入学年度</label>
		<a>${student.entYear}</a>
		<input type="hidden" name="entYear" value="${student.entYear}">
		<label>学生番号</label>
		<a>${student.no}</a>
		<input type="hidden" name="no" value="${student.no}">
		<label>氏名</label>
		<input type="text" name="name" value="${student.name}" required>
		<label>クラス</label>
		<select name="classNum" required>
		  <option value="201" ${student.classNum == '201' ? 'selected' : ''}>201</option>
		  <option value="202" ${student.classNum == '202' ? 'selected' : ''}>202</option>
		  <option value="203" ${student.classNum == '203' ? 'selected' : ''}>203</option>
		  <option value="204" ${student.classNum == '204' ? 'selected' : ''}>204</option>
		  <option value="205" ${student.classNum == '205' ? 'selected' : ''}>205</option>
		</select>
		<label>在籍中</label>
		<input type="hidden" name="isAttend" value="false">
		<input type="checkbox" name="isAttend" value="true" ${student.attend ? 'checked' : ''}>
		<input type="submit" value="変更">
	</form>
    </div>
</div>

<%@ include file="../footer.jsp" %>
