<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../header.jsp" %>
<div class="main-container">
	<div class="menu-wrapper">
        <%@ include file="../side.jsp" %>
    </div>
    <div class="content-container">
    <form action="scoremanager.StudentCreateExecute.action" method="post">
	    <h2>学生情報登録</h2>
	    <label>入学年度</label>
	    <select name="ent_year">
	    	<option value="2021">2021</option>
			<option value="2022">2022</option>
			<option value="2023">2023</option>
			<option value="2024">2024</option>
			<option value="2025">2025</option>
	    </select>
	    <label>学生番号</label>
	    <input type="text" name="no">
	    <label>氏名</label>
	    <input type="text" name="name">
	    <label>クラス</label>
	    <select name="class_num">
			<option value="201">201</option>
			<option value="202">202</option>
			<option value="203">203</option>
			<option value="204">204</option>
			<option value="205">205</option>
		</select>
		<input type="submit" value="登録して終了">
    </form>
    <a href="<c:url value='scoremanager/main/StudentList.action'/>">戻る</a>
    </div>
</div>
<%@ include file="../footer.jsp" %>