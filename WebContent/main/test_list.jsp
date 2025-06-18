<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../header.jsp"%>
<%-- タイトル + CSS --%>
<div class="main-container">
  <div class="menu-wrapper">
    <%@ include file="../side.jsp"%>
  </div>
  <div class="content-container">
    <h2>成績参照</h2>

    <div style="border: 1px solid #ccc; padding: 20px; margin-bottom: 20px; border-radius: 5px; background-color: #f9f9f9;">
      <div style="border-bottom: 1px solid #ccc; padding-bottom: 20px; margin-bottom: 20px;">
        <form action="TestListSubjectExecute.action" method="post">
          <p style="font-weight: bold; margin-bottom: 10px;">科目情報</p>
          <div style="display: flex; align-items: center; gap: 10px; flex-wrap: wrap;">
            <label for="entYear">入学年度</label>
            <select name="entYear" id="entYear" required>
              <option value="">---------</option>
              <c:forEach var="year" items="${entYears}">
                <option value="${year}" <c:if test="${ent_year == year}">selected</c:if>>${year}</option>
              </c:forEach>
            </select>

            <label for="classNum">クラス</label>
            <select name="classNum" id="classNum" required>
              <option value="">--------</option>
              <c:forEach var="cn" items="${classNums}">
                <option value="${cn}" <c:if test="${class_num == cn}">selected</c:if>>${cn}</option>
              </c:forEach>
            </select>

            <label for="subject_cd">科目</label>
            <select name="subject_cd" id="subject_cd" required>
              <option value="">--------</option>
              <c:forEach var="subject" items="${subjectList}">
                <option value="${subject.cd}" <c:if test="${subject_cd == subject.cd}">selected</c:if>>
                  ${subject.name}
                </option>
              </c:forEach>
            </select>

            <input type="hidden" name="is_attend" value="true">
            <button type="submit">検索</button>
          </div>
        </form>
      </div>

      <div>
        <form action="TestListStudentExecute.action" method="post">
          <p style="font-weight: bold; margin-bottom: 10px;">学生情報</p>
          <div style="display: flex; align-items: center; gap: 10px; flex-wrap: wrap;">
            <label for="student_no">学生番号</label>
            <input type="text" id="student_no" name="student_no" value="${student.no}" required placeholder="学生番号を入力してください">

            <input type="hidden" name="is_attend" value="true">
            <button type="submit">検索</button>
          </div>
        </form>
      </div>
    </div>

    <p style="color: #007bff; margin-top: 15px;">
      科目情報を選択または学生情報を入力して検索ボタンをクリックしてください
    </p>
  </div>
</div>
<%@ include file="../footer.jsp"%>
