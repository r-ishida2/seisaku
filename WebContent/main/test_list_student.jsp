<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../header.jsp"%>
<%-- タイトル + CSS --%>
<div class="main-container">
	<div class="menu-wrapper">
		<%@ include file="../side.jsp"%>
	</div>
	<div class="content-container">
		<h2>成績参照</h2>

		<form action="TestListSubjectExecute.action" method="post">
		<p>科目情報</p>
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
			    <c:forEach var="cn" items="${classNums}">
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
			<input type="hidden" name="is_attend" value="true">
			<input type="submit" value="検索">
		</form>
		<form action="TestListStudentExecute.action" method="post">
		<p>学生情報</p>
			<label>学生番号</label>
			<input type="text" name="student_no" value="${student.no}" required>

			<input type="hidden" name="is_attend" value="true">
			<input type="submit" value="検索">
		</form>
		<c:if test="${not empty error}">
			<p style="color: red; font-weight: bold;">${error}</p>
		</c:if>
		<c:choose>
			<c:when test="${not empty testList}">
			   <p>氏名：${student.name}（${student.no}）</p>
			  <table style="border-collapse: separate; border-spacing: 10px;">
			    <tr>
			      <th>科目名</th>
			      <th>科目コード</th>
			      <th>テスト回数</th>
			      <th>点数</th>
			    </tr>
			    <c:forEach var="item" items="${testList}">
			      <tr>
			        <td>${item.subject.name}</td>
			        <td>${item.subject.cd}</td>
					<td>${item.no}</td>
					<td>${item.point}</td>
			      </tr>
			    </c:forEach>
			  </table>
			</c:when>

			<c:otherwise>
			  <p>テスト情報が見つかりませんでした。</p>
			</c:otherwise>

		</c:choose>
	</div>
</div>
<%@ include file="../footer.jsp"%>