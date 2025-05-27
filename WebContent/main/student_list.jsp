<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../header.jsp"%>
<%-- タイトル + CSS --%>
<div class="main-container">
	<div class="menu-wrapper">
		<%@ include file="../side.jsp"%>
	</div>
	<div class="content-container">
		<h2>学生一覧</h2>
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
			</select> <label>クラス</label> <select name="class_num">
				<option value="">--------</option>
				<option value="201"
					<c:if test="${class_num == '201'}">selected</c:if>>201</option>
				<option value="202"
					<c:if test="${class_num == '202'}">selected</c:if>>202</option>
				<option value="203"
					<c:if test="${class_num == '203'}">selected</c:if>>203</option>
				<option value="204"
					<c:if test="${class_num == '204'}">selected</c:if>>204</option>
				<option value="205"
					<c:if test="${class_num == '205'}">selected</c:if>>205</option>
			</select> <label>在籍中</label> <input type="checkbox" name="is_attend"
				value="true" <c:if test="${is_attend == 'true'}">checked</c:if>>
			<input type="submit" value="絞り込み">
		</form>
		<c:if test="${not empty error}">
			<p style="color: red; font-weight: bold;">${error}</p>
		</c:if>
		<c:choose>
			<c:when test="${students.size() > 0}">
				<div>検索結果: ${students.size()} 件</div>
				<table style="border-collapse: separate; border-spacing: 10px;">
					<c:forEach var="item" items="${students}">
						<tr>
							<td>${item.no}</td>
							<td>${item.name}</td>
							<td>${item.entYear}</td>
							<td>${item.classNum}</td>
							<td>${item.attend}</td>
							<td><a href="StudentChange.action?no=${item.no}">変更</a></td>
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
