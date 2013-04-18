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
                renderTo: 'loginForm',
                listeners: {
                    // rebuild third party google login link.
                    render: function() {
                        var me = this;
                        var btn = me.down('#googleLoginBtn');
                        var btnDom = Ext.getDom(btn.getId());
                        var aEl = Ext.dom.Query.selectNode('a#googleLoginLink', btnDom);
                        aEl.href = '<c:url value="/redirect/googleLogin" />';
                    }
                }
            });
        });

    </script>
</head>
<body>
    <div id="loginForm" class="span4 offset5"></div>
</body>
</html>