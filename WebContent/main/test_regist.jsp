<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../header.jsp"%>

<div class="main-container">
	<div class="menu-wrapper">
		<%@ include file="../side.jsp"%>
	</div>
	<div class="content-container">
		<h2>成績管理</h2>
		<form action="TestRegist.action" method="post">
			<label>入学年度</label>
			<select name="entYear" required>
			    <option value="">---------</option>
			    <c:forEach var="year" items="${entYears}">
			        <option value="${year}" <c:if test="${ent_year == year}">selected</c:if>>${year}</option>
			    </c:forEach>
			</select>
			<label>クラス</label>
			<select name="classNum" required>
			    <option value="">--------</option>
			    <c:forEach var="cn" items="${classNumList}">
			        <option value="${cn}" <c:if test="${class_num == cn}">selected</c:if>>${cn}</option>
			    </c:forEach>
			</select>
			<label>科目</label>
			<select name="subject_cd" required>
			    <option value="">--------</option>
			    <c:forEach var="subject" items="${subjectList}">
			        <option value="${subject.cd}" <c:if test="${subject_cd == subject.cd}">selected</c:if>>
			            ${subject.name}
			        </option>
			    </c:forEach>
			</select>
			<label>回数</label>
			<select name="no">
				<option value="">--------</option>
				<option value="1" <c:if test="${no == '1'}">selected</c:if>>1</option>
				<option value="2" <c:if test="${no == '2'}">selected</c:if>>2</option>
			</select> <input type="submit" value="絞り込み">
		</form>

		<c:if test="${not empty error}">
			<p style="color: red; font-weight: bold;">${error}</p>
		</c:if>

		<c:choose>
			<c:when test="${tests.size() > 0}">
				<form action="TestRegistExecute.action" method="post">
					<!-- 絞り込み条件を維持して送信 -->
					<input type="hidden" name="ent_year" value="${ent_year}"> <input
						type="hidden" name="class_num" value="${class_num}"> <input
						type="hidden" name="subject_cd" value="${subject_cd}"> <input
						type="hidden" name="no" value="${no}">

					<div>検索結果: ${tests.size()} 件</div>
					<table style="border-collapse: separate; border-spacing: 10px;">
						<tr>
							<th>入学年度</th>
							<th>クラス</th>
							<th>学生番号</th>
							<th>氏名</th>
							<th>点数</th>
						</tr>
						<c:forEach var="item" items="${tests}">
							<tr>
								<td>${item.student.entYear}</td>
								<td>${item.student.classNum}</td>
								<td>${item.student.no} <input type="hidden" name="noList"
									value="${item.student.no}">
								</td>
								<td>${item.student.name}</td>
								<td><input type="number" name="pointList"
									value="${item.point}" min="0" max="100" required></td>
							</tr>
						</c:forEach>
					</table>
					<input type="submit" value="成績登録">
				</form>
			</c:when>
			<c:otherwise>
				<p>成績情報が存在しませんでした</p>
			</c:otherwise>
		</c:choose>

	</div>
</div>

<%@ include file="../footer.jsp"%>
