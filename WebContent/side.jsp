<!-- 本ファイルはメニューエリアを表示する JSP です。 -->

<!-- TODO: 現在は仮の実装です。必要に応じて修正または書き換えてください。 -->

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- 左メニューエリア -->
<div class="side-menu">
  <ul>
    <li><a href="<c:url value='/index.jsp'/>">メニュー</a></li>
    <li><a href="<c:url value='/scoremanager/main/StudentList.action'/>">学生管理</a></li>
    <li>成績管理</li>
    <li><a href="<c:url value='/scoremanager/main/TestRegist.action'/>">成績登録</a></li>
    <li><a href="<c:url value='/scoremanager/main/TestList.action'/>">成績参照</a></li>
    <li><a href="<c:url value='/scoremanager/main/SubjectList.action'/>">科目管理</a></li>
  </ul>
</div>
