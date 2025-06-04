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
            <select name="ent_year">
                <option value="">---------</option>
                <option value="2021" <c:if test="${ent_year == '2021'}">selected</c:if>>2021</option>
                <option value="2022" <c:if test="${ent_year == '2022'}">selected</c:if>>2022</option>
                <option value="2023" <c:if test="${ent_year == '2023'}">selected</c:if>>2023</option>
                <option value="2024" <c:if test="${ent_year == '2024'}">selected</c:if>>2024</option>
                <option value="2025" <c:if test="${ent_year == '2025'}">selected</c:if>>2025</option>
            </select>

            <label>クラス</label>
            <select name="class_num">
                <option value="">--------</option>
                <option value="201" <c:if test="${class_num == '201'}">selected</c:if>>201</option>
                <option value="202" <c:if test="${class_num == '202'}">selected</c:if>>202</option>
                <option value="203" <c:if test="${class_num == '203'}">selected</c:if>>203</option>
                <option value="204" <c:if test="${class_num == '204'}">selected</c:if>>204</option>
                <option value="205" <c:if test="${class_num == '205'}">selected</c:if>>205</option>
            </select>

            <label>科目</label>
            <select name="subject">
                <option value="">--------</option>
                <option value="Python" <c:if test="${subject == 'Python'}">selected</c:if>>Python</option>
                <option value="Java" <c:if test="${subject == 'Java'}">selected</c:if>>Java</option>
                <option value="Script" <c:if test="${subject == 'Script'}">selected</c:if>>Script</option>
                <option value="Flask" <c:if test="${subject == 'Flask'}">selected</c:if>>Flask</option>
                <option value="AWS" <c:if test="${subject == 'AWS'}">selected</c:if>>AWS</option>
            </select>

            <label>回数</label>
            <select name="num">
                <option value="">--------</option>
                <option value="1" <c:if test="${num == '1'}">selected</c:if>>1</option>
                <option value="2" <c:if test="${num == '2'}">selected</c:if>>2</option>
            </select>

            <input type="submit" value="絞り込み">
        </form>

        <c:if test="${not empty error}">
            <p style="color: red; font-weight: bold;">${error}</p>
        </c:if>

        <c:choose>
            <c:when test="${tests.size() > 0}">
                <form action="TestRegistExecute.action" method="post">
                    <input type="hidden" name="ent_year" value="${ent_year}">
                    <input type="hidden" name="class_num" value="${class_num}">
                    <input type="hidden" name="subject" value="${subject}">
                    <input type="hidden" name="num" value="${num}">

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
                                <td>${item.entYear}</td>
                                <td>${item.classNum}</td>
                                <td>
                                    ${item.no}
                                    <input type="hidden" name="no" value="${item.no}">
                                </td>
                                <td>${item.name}</td>
                                <td>
                                    <input type="number" name="point" value="${item.score}" min="0" max="100" required>
                                </td>
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
