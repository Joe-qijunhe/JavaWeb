<%--
  Created by IntelliJ IDEA.
  User: ASUS
  Date: 10/07/2021
  Time: 4:21 pm
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div id="page_nav">
    <%--大于首页才显示--%>
    <c:if test="${requestScope.page.pageNo > 1}">
        <a href="${requestScope.page.url}&pageNo=1">首页</a>
        <a href="${requestScope.page.url}&pageNo=${requestScope.page.pageNo - 1}">上一页</a>
    </c:if>


    <%--页码输出--%>
    <c:choose>
        <%--如果总页码小于等于 5 的情况，页码的范围是：1-总页码--%>
        <c:when test="${requestScope.page.pageTotal <= 5}">
            <c:set var="begin" value="1"/>
            <c:set var="end" value="${requestScope.page.pageTotal}"/>
        </c:when>
        <%--总页码大于 5 的情况 --%>
        <c:when test="${requestScope.page.pageTotal > 5}">
            <c:choose>
                <%--当前页码为前面 3 个：1，2，3 的情况，页码范围是：1-5. --%>
                <c:when test="${requestScope.page.pageNo <= 3}">
                    <c:set var="begin" value="1"/>
                    <c:set var="end" value="5"/>
                </c:when>
                <%--当前页码为最后 3 个，8，9，10，页码范围是：总页码减 4 - 总页码--%>
                <c:when test="${requestScope.page.pageNo > requestScope.page.pageTotal - 3}">
                    <c:set var="begin" value="${requestScope.page.pageTotal - 4}"/>
                    <c:set var="end" value="${requestScope.page.pageTotal}"/>
                </c:when>
                <%--4，5，6，7，页码范围是：当前页码减 2 - 当前页码加 2--%>
                <c:otherwise>
                    <c:set var="begin" value="${requestScope.page.pageNo - 2}"/>
                    <c:set var="end" value="${requestScope.page.pageNo + 2}"/>
                </c:otherwise>
            </c:choose>
        </c:when>
    </c:choose>

    <c:forEach begin="${begin}" end="${end}" var="i">
        <c:if test="${i == requestScope.page.pageNo}">
            [${i}]
        </c:if>
        <c:if test="${i != requestScope.page.pageNo}">
            <a href="${requestScope.page.url}&pageNo=${i}">${i}</a>
        </c:if>
    </c:forEach>


    <%--最后一页不显示下一页和末页--%>
    <c:if test="${requestScope.page.pageNo < requestScope.page.pageTotal}">
        <a href="${requestScope.page.url}&pageNo=${requestScope.page.pageNo + 1}">下一页</a>
        <a href="${requestScope.page.url}&pageNo=${requestScope.page.pageTotal}">末页</a>
    </c:if>
    共${requestScope.page.pageTotal}页，${requestScope.page.pageTotalCount}条记录
    到第<input value="${param.pageNo}" name="pn" id="pn_input"/>页
    <input id="searchPageBtn" type="button" value="确定">
</div>

<script type="text/javascript">
    $(function () {
        $("#searchPageBtn").click(function () {
            var pageNo = $("#pn_input").val();
            if (pageNo < 1 || pageNo > ${requestScope.page.pageTotal}) {
                alert("请输入有效页数");
                $("#pn_input").val("${param.pageNo}");
                return false;
            }
            // javaScript 语言中提供了一个 location 地址栏对象
            // 它有一个属性叫 href.它可以获取浏览器地址栏中的地址
            // href 属性可读，可写
            location.href = "${pageScope.basePath}${requestScope.page.url}&pageNo=" + pageNo;
        });

    });
</script>
