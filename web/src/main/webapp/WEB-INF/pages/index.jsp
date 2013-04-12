<%@ include file="/common/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title><fmt:message key="login.title"/></title>
    <!-- Extjs -->
    <link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/ext-all-gray.css' />"/>
    <script type="text/javascript" src="<c:url value='/js/ext-all-dev.js' />"></script>
    <script type="text/javascript" src="<c:url value='/js/component/login.js' />"></script>
    <script type="text/javascript">
        Ext.onReady(function() {
            Ext.create('App.ux.Login', {
                renderTo: 'loginForm'
            });
        });

    </script>
</head>
<body>
    <div id="loginForm" class="span4 offset5"></div>
</body>
</html>