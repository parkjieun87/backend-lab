<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%--
  Created by IntelliJ IDEA.
  User: dksxl
  Date: 26. 8. 2.
  Time: 오후 5:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>채널 목록 - 컨트롤러 연동</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <style>
        body { font-family: sans-serif; max-width: 400px; margin: 40px auto; }
        .tab_button { display: inline-block; padding: 8px 14px; cursor: pointer; border: 1px solid #ccc; }
        .tab_button.active { background: #333; color: #fff; }
        .none { display: none; }
        ul { list-style: none; padding: 0; border: 1px solid #eee; margin-top: 10px; }
        li { padding: 10px; border-bottom: 1px solid #f0f0f0; position: relative; }
        li:hover { background: #f7f7f7; }
        .badge { background: red; color: #fff; border-radius: 10px; padding: 1px 7px; font-size: 12px; float: right; }
    </style>
</head>
<body>
    <h3>대화방 목록 (컨트롤러 → JSP)</h3>

    <!-- 컨트롤러에서 model.addAttribute("loginEmpId", "1001") 로 넘긴 값 -->
    <p style="color:#888; font-size:13px;">내 사번(EL 스코프 테스트): ${loginEmpId}</p>

    <ul id="chatListTab">
        <li class="tab_button active" data-tab="chat">전체</li>
        <li class="tab_button" data-tab="bookmark">즐겨찾기</li>
        <li class="tab_button" data-tab="unread">안읽음</li>
    </ul>

    <ul id="list">
        <c:forEach var="channel" items="${channels}">
            <c:if test="${channel.delYn != 'Y'}">
                <li data-channel-id="${channel.channelId}">
                    ${channel.name} - ${channel.lastChat}

                    <c:choose>
                        <c:when test="${channel.unReadCount > 99}">
                            <span class="badge">99+</span>
                        </c:when>
                        <c:when test="${channel.unReadCount > 0}">
                            <span class="badge"> ${channel.unReadCount}</span>
                        </c:when>
                        <c:otherwise>
                            <%-- 출력 안 함 --%>
                        </c:otherwise>
                    </c:choose>
                </li>
            </c:if>
        </c:forEach>
    </ul>


    <ul class="none" id="favorite">
        <c:forEach var="channel" items="${channels}">
            <c:if test="${channel.favoriteYn == 'Y'}">
                <li data-channel-id="${channel.channelId}">
                    ${channel.name} - ${channel.lastChat}
                </li>
            </c:if>
        </c:forEach>
    </ul>

    <ul class="none" id="unreadList">
        <c:forEach var="channel" items="${channels}">
            <c:if test="${channel.unReadCount > 0}">
                <li data-channel-id="${channel.channelId}">
                    ${channel.name} - ${channel.lastChat} <span class="badge"> ${channel.unReadCount}</span>
                </li>
            </c:if>
        </c:forEach>
    </ul>

<script>
    $('#chatListTab').on('click','li',function(){
      $(this).addClass('active').siblings().removeClass('active');
      const tab = $(this).data('tab');

        $('#list, #favorite, #unreadList').addClass('none');

        switch (tab) {
            case 'chat':
                $('#list').removeClass('none');
                break;
            case 'bookmark':
                $('#favorite').removeClass('none');
                break;
            case 'unread':
                $('#unreadList').removeClass('none');
                break;
        }
    });
</script>
</body>
</html>
