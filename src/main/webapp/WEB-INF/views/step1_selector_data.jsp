<%--
  Created by IntelliJ IDEA.
  User: dksxl
  Date: 26. 7. 28.
  Time: 오후 10:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Step1 - 선택자 & .data() 버그 실습</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <style>
        body { font-family: sans-serif; padding: 20px; }
        ul { list-style: none; padding: 0; }
        li { padding: 8px; margin: 4px 0; background: #f5f5f5; border-radius: 4px; }
        .tab-title { font-weight: bold; margin-top: 16px; }
        button { margin: 4px 8px 4px 0; padding: 6px 12px; }
        #log { white-space: pre-wrap; font-family: monospace; background: #222; color: #0f0;
            padding: 12px; margin-top: 16px; height: 200px; overflow-y: auto; }
    </style>
</head>
<body>
<h2>탭 3개 (일부러 id 중복시킴)</h2>

<div class="tab-title">list 탭</div>
<ul id="list">
    <li id="ch1">채널1 <span class="count">0</span></li>
</ul>

<div class="tab-title">favorite 탭</div>
<ul id="favorite">
    <li id="ch1">채널1 <span class="count">0</span></li>
</ul>

<div class="tab-title">unreadList 탭</div>
<ul id="unreadList">
    <li id="ch1">채널1 <span class="count">0</span></li>
</ul>

<hr>

<button id="btnIdSelector">① $('#ch1') 로 카운트 올리기 (버그 재현)</button>
<button id="btnAttrSelector">② $('[id="ch1"]') 로 카운트 올리기 (전체 반영)</button>
<br>

<div id="log"></div>

<script>
    // 로그를 화면에 찍어주는 헬퍼. console.log 대신 눈으로 바로 보려고 만듦.
    function log(msg) {
        $('#log').append(msg + '\n');
        $('#log').scrollTop($('#log')[0].scrollHeight);
    }

    // ------------------------------------------------------------
    // ① $('#ch1')  ← id 선택자
    // 브라우저 내부적으로 document.getElementById('ch1') 과 동일하게 동작.
    // HTML 스펙상 id는 유일해야 하므로, "문서 전체에서 가장 먼저 나오는 1개"만
    // 찾고 그 즉시 멈춘다. 그래서 tab이 3개라도 항상 결과는 1개.
    // ------------------------------------------------------------
    $('#btnIdSelector').on('click', function() {
        log('--- ① $("#ch1") 클릭 ---');
        var $el = $('#ch1');
        log('선택된 요소 개수: ' + $el.length + '  (몇 개가 나왔는지 먼저 확인)');
        $el.find('.count').text(function(i, oldVal) {
            return Number(oldVal) + 1;
        });
        log('→ list 탭의 카운트만 올라갔을 것. favorite/unreadList는 그대로.');
    });

    // ------------------------------------------------------------
    // ② $('[id="ch1"]')  ← 속성(attribute) 선택자
    // "id가 ch1인 놈을 다 찾아라"는 의미의 일반 속성 선택자.
    // id의 유일성 규칙과 무관하게 CSS 선택자 엔진이 매칭되는 요소를 전부 찾는다.
    // 그래서 탭 3개에 중복 id가 있어도 3개 모두 선택됨.
    // ------------------------------------------------------------
    $('#btnAttrSelector').on('click', function() {
        log('--- ② $(\'[id="ch1"]\') 클릭 ---');
        var $els = $('[id="ch1"]');
        log('선택된 요소 개수: ' + $els.length + '  (①과 비교해보기)');
        $els.each(function() {
            var $count = $(this).find('.count');
            $count.text(Number($count.text()) + 1);
        });
        log('→ 탭 3개 카운트가 전부 올라갔을 것.');
    });

</script>
</body>
</html>