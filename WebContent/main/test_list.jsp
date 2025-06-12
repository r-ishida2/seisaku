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
		<p>科目情報を選択または学生情報を入力して検索ボタンをクリックしてください</p>
	</div>
</div>
<%@ include file="../footer.jsp"%>