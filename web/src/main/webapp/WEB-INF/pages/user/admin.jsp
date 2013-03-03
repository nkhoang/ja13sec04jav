<%@ include file="/common/taglibs.jsp" %>
<html>
<head>
    <title><fmt:message key="admin.title"/></title>
    <script type="text/javascript" src="http://code.jquery.com/jquery-1.9.1.min.js"></script>

    <script type="text/javascript" src="<c:url value='/js/ext-all-dev.js' />"></script>
    <link href="<c:url value='/css/ext-standard.css'/>" rel="stylesheet" media="all"/>
    <script type="text/javascript">
        // define model
        Ext.define('AWS.model.user.Role', {
            extend : 'Ext.data.Model',
            fields : [
                'id',
                'name'
            ]
        });

        Ext.define('AWS.model.user.Group', {
            extend : 'Ext.data.Model',
            requires : [
                'AM.model.audit.Role'
            ],
            fields : [
                'id',
                'name'
            ],

            hasMany : [
                { model : 'AWS.model.user.Role', name : 'roles', associationKey : 'roles' }
            ]
        });

        // define store
        Ext.define('AWS.store.user.Group', {
            extend : 'Ext.data.Store',
            autoLoad : false,
            model : 'AWS.model.user.Group',
            pageSize : 10,
            remoteSort : true,
            proxy : {
                type : 'ajax',
                url : '',
                reader : {
                    type : 'json',
                    root : 'data'
                }
            }
        });

        Ext.define('AWS.ux.user.admin.GroupPanel', {
            extend : 'Ext.grid.Panel',
            alias : 'widget.adminGroupPanel',
            requires : [ 'AWS.store.user.Group' ],
            collapsed : false,
            frame : true,
            features : [
                {
                    ftype : 'filters',
                    encode : true,
                    local : false
                }
            ],
            url : 'service/cirrus/auditstore/request/pending/PRS_IDENTIFIER',
            initComponent : function () {
                var me = this;

                if (!me.projectModel) {
                    Ext.Error.raise("Please specify project model");
                }
                var storeId = (me.projectModel.get('id') ? me.projectModel.get('id') + 'unmatched.AuditRequest' : 'unmatched.AuditRequest');

                var store = Ext.create('AM.store.unmatched.AuditRequest', { storeId : storeId })
                store.proxy.url = AM.buildUrl(me.url)
                Ext.applyIf(me, {
                    store : storeId,
                    columns : {
                        defaults : {
                            xtype : 'gridcolumn',
                            sortable : false,
                            menuDisabled : true
                        },
                        items : [
                            {

                                dataIndex : 'id',
                                text : 'Audit ID',
                                renderer : function (value) {
                                    return Ext.String.format('<a href="#auditor.Matched/{0}">{1}</a>', value, value)
                                }
                            },
                            {
                                dataIndex : 'hmsId',
                                text : 'HMS ID'
                            },
                            {
                                dataIndex : 'type',
                                text : 'Request Type',
                                menuDisabled : false,
                                filter : {
                                    type : 'list',
                                    options : ['DEA', 'NPI', 'STLIC']
                                }
                            },
                            {
                                dataIndex : 'reqStatus',
                                text : 'Request Status'
                            },
                            {
                                dataIndex : 'sources',
                                text : 'Client',
                                renderer : me.sourcesRenderer,
                                menuDisabled : false,
                                filter : {
                                    type : 'list',
                                    options : AM.singletons.data.prsSources
                                }
                            }
                        ]
                    },
                    dockedItems : [
                        {
                            xtype : 'pagingtoolbar',
                            store : storeId,
                            inputItemWidth : 45,
                            dock : 'bottom',
                            width : 360,
                            displayInfo : true
                        }
                    ]
                });
                me.callParent(arguments);
            },

            sourcesRenderer : function (sources) {
                var client = '';
                for (var i = 0; i < sources.length; i++) {
                    var src = sources[i].source;
                    if (i > 0) {
                        client += ', ';
                    }
                    client += AM.convertSourceToClientId(src);
                }

                return client;
            }
        });
    </script>
</head>
<body>

</body>
</html>
