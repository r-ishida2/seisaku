<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../header.jsp"%>
<%-- タイトル + CSS --%>
<div class="main-container">
	<div class="menu-wrapper">
		<%@ include file="../side.jsp"%>
	</div>
	<div class="content-container">
		<h2>科目管理</h2>
		<a href="<c:url value='/scoremanager.main.SubjectCreate.action'/>">新規登録</a>

		<table style="border-collapse: separate; border-spacing: 10px;">
			<tr>
				<th>科目コード</th>
				<th>科目名</th>
			</tr>
			<c:forEach var="item" items="${subjects}">
				<tr>
					<td>${item.cd}</td>
					<td>${item.name}</td>
					<td><a href="SubjectUpdate.action?no=${item.cd}">変更</a></td>
					<td><a href="SubjectDelete.action?no=${item.cd}">削除</a></td>
				</tr>
			</c:forEach>
		</table>
	</div>
</div>
<%@ include file="../footer.jsp"%>
