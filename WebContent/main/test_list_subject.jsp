<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../header.jsp"%>
<div class="main-container">
	<div class="menu-wrapper">
		<%@ include file="../side.jsp"%>
	</div>
	<div class="content-container">
		<h2>成績参照</h2>

		<form action="TestListSubjectExecute.action" method="post">
			<p>科目情報</p>
			<label>入学年度</label> <select name="ent_year" required>
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
			</select> <label>クラス</label> <select name="class_num" required>
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
			</select> <label>科目</label> <select name="subject_cd" required>
				<option value="">--------</option>
				<option value="A01"
					<c:if test="${subject_cd == 'A01'}">selected</c:if>>Python</option>
				<option value="Python"
					<c:if test="${subject_cd == 'Python'}">selected</c:if>>Python(仮)</option>
				<option value="Java"
					<c:if test="${subject_cd == 'Java'}">selected</c:if>>Java(仮)</option>
				<option value="Script"
					<c:if test="${subject_cd == 'Script'}">selected</c:if>>Script(仮)</option>
				<option value="Flask"
					<c:if test="${subject_cd == 'Flask'}">selected</c:if>>Flask(仮)</option>
				<option value="AWS"
					<c:if test="${subject_cd == 'AWS'}">selected</c:if>>AWS(仮)</option>
				<option value="AAA"
					<c:if test="${subject_cd == 'AAA'}">selected</c:if>>AAA(仮)</option>
			</select> <input type="hidden" name="is_attend" value="true"> <input
				type="submit" value="検索">
		</form>

		<form action="TestListStudentExecute.action" method="post">
			<p>学生情報</p>
			<label>学生番号</label> <input type="text" name="student_no"
				value="${student.no}" required> <input type="hidden"
				name="is_attend" value="true"> <input type="submit"
				value="検索">
		</form>

		<c:if test="${not empty error}">
			<p style="color: red; font-weight: bold;">${error}</p>
		</c:if>

		<c:choose>
			<c:when test="${testList.size() > 0}">
				<div>検索結果: ${testList.size()} 件</div>
				<table style="border-collapse: separate; border-spacing: 10px;">
					<tr>
						<th>入学年度</th>
						<th>クラス</th>
						<th>学生番号</th>
						<th>氏名</th>
						<th>1回目</th>
						<th>2回目</th>
					</tr>
					<c:forEach var="item" items="${testList}">
						<tr>
							<td>${item.entYear}</td>
							<td>${item.classNum}</td>
							<td>${item.studentNo}</td>
							<td>${item.studentName}</td>
							<td><c:set var="point1" value="-" /> <c:forEach var="entry"
									items="${item.points.entrySet()}">
									<c:if test="${entry.key == 1 && entry.value != null}">
										<c:set var="point1" value="${entry.value}" />
									</c:if>
								</c:forEach> ${point1}</td>
							<td><c:set var="point2" value="-" /> <c:forEach var="entry"
									items="${item.points.entrySet()}">
									<c:if test="${entry.key == 2 && entry.value != null}">
										<c:set var="point2" value="${entry.value}" />
									</c:if>
								</c:forEach> ${point2}</td>

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
