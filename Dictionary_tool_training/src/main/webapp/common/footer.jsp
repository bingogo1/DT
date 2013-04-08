<%@ include file="/common/taglibs.jsp" %>

    <div id="divider"><div></div></div>
    <span class="left">Version 0.1 |
        <span id="validators">
            <a href="http://validator.w3.org/check?uri=referer">XHTML Valid</a> |
            <a href="http://jigsaw.w3.org/css-validator/validator-uri.html">CSS Valid</a>
        </span>
        <c:if test="${pageContext.request.remoteUser != null}">
        </c:if>
    </span>
    <span class="right">
        (C) Copyright bingogo1@hotmail.com, LP -  All Rights Reserved.
    </span>
    <!-- Built on @BUILD-TIME@ -->
