<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../header.jsp"%>

<div class="main-container">
    <div class="menu-wrapper">
        <%@ include file="../side.jsp"%>
    </div>
    <div class="content-container student-list">
        <h2 class="section-title">学生管理</h2>

        <div class="register-link">
            <a href="<c:url value='/scoremanager.main.StudentCreate.action'/>">新規登録</a>
        </div>

        <!-- 検索フォーム -->
        <form action="StudentList.action" method="post" class="search-form">
            <div class="form-group">
                <label>入学年度</label>
                <select name="ent_year">
                    <option value="">---------</option>
                    <c:forEach var="year" items="${entYears}">
                        <option value="${year}" <c:if test="${ent_year == year}">selected</c:if>>${year}</option>
                    </c:forEach>
                </select>
            </div>

            <div class="form-group">
                <label>クラス</label>
                <select name="class_num">
                    <option value="">--------</option>
                    <c:forEach var="cn" items="${classNums}">
                        <option value="${cn}" <c:if test="${class_num == cn}">selected</c:if>>${cn}</option>
                    </c:forEach>
                </select>
            </div>

            <div class="form-group checkbox-area">
                <label>在籍中</label>
                <input type="hidden" name="is_attend" value="false">
                <input type="checkbox" name="is_attend" value="true" <c:if test="${is_attend == 'true'}">checked</c:if>>
            </div>

            <div class="form-group">
                <button type="submit" class="submit-button">絞り込み</button>
            </div>
        </form>

        <!-- エラーメッセージ -->
        <c:if test="${not empty error}">
            <p class="error-message">${error}</p>
        </c:if>

        <!-- 検索結果表示 -->
        <c:choose>
            <c:when test="${students.size() > 0}">
                <div class="result-count">検索結果：${students.size()}件</div>
                <table class="student-table">
                    <tr>
                        <th>学生番号</th>
                        <th>氏名</th>
                        <th>入学年度</th>
                        <th>クラス番号</th>
                        <th>在籍中</th>
                        <th></th>
                    </tr>
                    <c:forEach var="item" items="${students}">
                        <tr>
                            <td>${item.no}</td>
                            <td>${item.name}</td>
                            <td>${item.entYear}</td>
                            <td>${item.classNum}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${item.attend}">〇</c:when>
                                    <c:otherwise>×</c:otherwise>
                                </c:choose>
                            </td>
                            <td><a href="StudentUpdate.action?no=${item.no}">変更</a></td>
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
