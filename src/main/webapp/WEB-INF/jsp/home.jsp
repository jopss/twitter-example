<%@include file="/WEB-INF/tags/taglibs.jsp"%>

<h1> <fmt:message key='twitter' /> </h1>
<hr />

<ul>
    <li><a href="${contextPage}/twitter/public/"><fmt:message key='twitter.public' /></a></li>
    <li><a href="${contextPage}/twitter/auth/"><fmt:message key='twitter.auth' /></a></li>
</ul>