<!-- 本ファイルはトップページのJSPです -->

<!-- TODO: 現在は仮の実装です。必要に応じて修正または書き換えてください。 -->

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="header.jsp" %>  <%-- タイトル + CSS --%>

<!-- メニューとコンテンツを横並びに配置するコンテナ -->
<div class="main-container">
    <!-- 左メニューエリア -->
    <div class="menu-wrapper">
        <%@ include file="side.jsp" %>
    </div>

    <!-- 右コンテンツエリア -->
    <div class="content-container">
    <h2>メニュー</h2>
		<ul class="nav">
		    <li>
		        <span class="title"></span>
		        <a href="<c:url value='scoremanager/main/StudentList.action'/>">学生管理</a>
		    </li>
		    <li>
		        <span class="title">成績管理</span>
		        <a href="<c:url value='scoremanager/main/TestRegist.action'/>">成績登録</a>
		        <a href="<c:url value='scoremanager/main/TestList.action'/>">成績参照</a>
		    </li>
		    <li>
		        <span class="title"></span>
		        <a href="<c:url value='scoremanager/main/SubjectList.action'/>">科目管理</a>
		    </li>
		</ul>
    </div>
</div>

<%@ include file="footer.jsp" %>
