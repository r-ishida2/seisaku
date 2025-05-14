<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../header.jsp" %>  <%-- タイトル + CSS --%>
<div class="main-container">
	<div class="menu-wrapper">
        <%@ include file="../side.jsp" %>
    </div>
    <div class="content-container">
		<h2>学生一覧</h2>
		<a href="<c:url value='/scoremanager.main.StudentCreate.action'/>">新規登録</a>
		<form action="StudentList.action" method="post">
			<label>入学年度</label>
			<select name="f1">
				<option value=null>---------</option>
				<option value="2021">2021</option>
				<option value="2022">2022</option>
				<option value="2023">2023</option>
				<option value="2024">2024</option>
				<option value="2025">2025</option>
			</select>
			<label>クラス</label>
			<select name="f2">
				<option value=null>--------</option>
				<option value="201">201</option>
				<option value="202">202</option>
				<option value="203">203</option>
				<option value="204">204</option>
				<option value="205">205</option>
			</select>
			<label>在籍中</label>
			<input type="hidden" name="f3" value="false">
			<input type="checkbox" name="f3" value="true">
			<input type="submit" value="絞り込み">
		</form>

		<c:choose>
			<c:when  test="${students.size() >0}">
				<div>検索結果:${students.size()}件</div>
				<table style="border-collapse:separate;border-spacing:10px;">
				<c:forEach var="item" items="${students}">
					<tr>
					<td>${item.no}</td>
					<td>${item.name}</td>
					<td>${item.entYear}</td>      <%-- 修正済 --%>
					<td>${item.classNum}</td>     <%-- 修正済 --%>
					<td>${item.attend}</td>       <%-- 修正済 --%>
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
<%@ include file="../footer.jsp" %>
