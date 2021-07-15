<%--
  Created by IntelliJ IDEA.
  User: ASUS
  Date: 7/07/2021
  Time: 4:11 pm
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:if test="${empty sessionScope.user}">
    <div>
        <span><a href="pages/user/login.jsp">请先登录</a></span>
    </div>
</c:if>
<c:if test="${not empty sessionScope.user}">
    <div>
        <span>欢迎<span class="um_span">${sessionScope.user.username}</span>光临尚硅谷书城</span>
        <a href="orderServlet?action=showMyOrders&userId=${sessionScope.user.id}">我的订单</a>
        <a href="userServlet?action=logout">注销</a>&nbsp;&nbsp;
        <a href="index.jsp">返回</a>
    </div>
</c:if>
