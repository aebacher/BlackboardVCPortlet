<%@ include file="/WEB-INF/jsp/include.jsp"%>

<spring:htmlEscape defaultHtmlEscape="true" />

<rs:aggregatedResources path="/resources.xml"/>

<portlet:defineObjects/>

<c:set var="n">
    <portlet:namespace />
</c:set>
<c:set var="prefs" scope="request" value="${renderRequest.preferences.map}" />

<style>
div[id^="${n}"] th{
    background-color: #efe8dc!important;
}
div[id^="${n}"] .uploadButton{
    border:none;
    padding-left: 0em;
}

div[id^="${n}"] .uploadButton input[type=file]{
     cursor: inherit;
}

</style>

