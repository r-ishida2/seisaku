<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../header.jsp"%>
<%-- タイトル + CSS --%>
<div class="main-container">
	<div class="menu-wrapper">
		<%@ include file="../side.jsp"%>
	</div>
	<div class="content-container">
		<h2>学生管理</h2>
		<a href="<c:url value='/scoremanager.main.StudentCreate.action'/>">新規登録</a>

		<form action="StudentList.action" method="post">
			<label>入学年度</label> <select name="ent_year">
				<option value="">---------</option>
				<option value="2021"
					<c:if test="${ent_year == '2021'}">selected</c:if>>2021</option>
				<option value="2022"
					<c:if test="${ent_year == '2022'}">selected</c:if>>2022</option>
				<option value="2023"
					<c:if test="${ent_year == '2023'}">selected</c:if>>2023</option>
				<option value="2024"
					<c:if test="${ent_year == '2024'}">selected</c:if>>2024</option>
				<option value="2025"
					<c:if test="${ent_year == '2025'}">selected</c:if>>2025</option>
			</select>
			<label>クラス</label>
			<select name="class_num">
			    <option value="">--------</option>
			    <c:forEach var="cn" items="${classNums}">
			        <option value="${cn}" <c:if test="${class_num == cn}">selected</c:if>>${cn}</option>
			    </c:forEach>
			</select>
			<label>在籍中</label>
			<input type="hidden" name="is_attend" value="false">
			<input type="checkbox" name="is_attend" value="true"
       <c:if test="${is_attend == 'true'}">checked</c:if>>
			<input type="submit" value="絞り込み">
		</form>
		<c:if test="${not empty error}">
			<p style="color: red; font-weight: bold;">${error}</p>
		</c:if>
		<c:choose>
			<c:when test="${students.size() > 0}">
				<div>検索結果: ${students.size()} 件</div>
				<table style="border-collapse: separate; border-spacing: 10px;">
				<tr>
					<th>学生番号</th>
					<th>氏名</th>
					<th>入学年度</th>
					<th>クラス番号</th>
					<th>在籍中</th>
				</tr>
				<c:forEach var="item" items="${students}">
				<tr>
					<td>${item.no}</td>
					<td>${item.name}</td>
					<td>${item.entYear}</td>
					<td>${item.classNum}</td>
					<td><c:choose><c:when test="${item.attend}">〇</c:when><c:otherwise>×</c:otherwise></c:choose></td>
					<td><a href="StudentUpdate.action?no=${item.no}">変更</a></td>
				</tr>
				</c:forEach>
				</table>
			</c:when>
			<c:otherwise>
				<p>学生情報が存在しませんでした</p>
			</c:otherwise>
		</c:choose>
	</div>
</div>
<%@ include file="../footer.jsp"%>
