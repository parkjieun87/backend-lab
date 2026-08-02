<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>1단계 - 탭 뼈대</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <style>
        body { font-family: sans-serif; max-width: 400px; margin: 40px auto; }
        .tab_button { display: inline-block; padding: 8px 14px; cursor: pointer; border: 1px solid #ccc; }
        .tab_button.active { background: #333; color: #fff; }
        .none { display: none; }
        ul { list-style: none; padding: 0; border: 1px solid #eee; margin-top: 10px; }
        li { padding: 10px; border-bottom: 1px solid #f0f0f0; }
        li:hover { background: #f7f7f7; }
    </style>
</head>
<body>

<h3>대화방 목록(미니 버전)</h3>

<ul id="chatListTab">
    <li class="tab_button active" data-tab="chat">전체</li>
    <li class="tab_button" data-tab="bookmark">즐겨찾기</li>
    <li class="tab_button" data-tab="unread">안읽음</li>
</ul>

<ul id="list">
    <li data-channel-id="1">김철수 - 안녕하세요</li>
    <li data-channel-id="2">이영희 - 회의 자료 보냈어요</li>
    <li data-channel-id="3">박민수 - 넵 확인했습니다.</li>
</ul>

<ul id="favorite" class="none">
    <li data-channel-id="2">이영희 - 회의 자료 보냈어요</li>
</ul>

<ul id="unreadList" class="none">
    <li data-channel-id="3">박민수 - 넵 확인했습니다.</li>
</ul>

<script>
    $('#chatListTab').on('click','li',function (){
        $(this)
            .addClass('active')
            .siblings()
            .removeClass('active');

        // data-tab 읽기
        const tab = $(this).data('tab');

        $('#list,#favorite,#unreadList').addClass('none');

        switch (tab){
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
