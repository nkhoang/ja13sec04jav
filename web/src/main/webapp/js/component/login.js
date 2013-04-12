Ext.define('App.ux.Login', {
    extend : 'Ext.container.Container',
    alias : 'widget.loginPanel',
    layout:{
        type:'vbox',
        align:'center'
    },
    initComponent : function () {
        var me = this;
        Ext.applyIf(me, {

            items : [
                {
                    xtype : 'form',
                    title : 'Please Login',
                    frame : true,
                    width : 300,
                    margin : '80 0 0 0',
                    fieldDefaults : {
                        labelWidth : 70
                    },
                    defaults : {
                        anchor : '100%',
                        margin : 10
                    },
                    defaultType : 'textfield',
                    items : [
                        {
                            xtype : 'label',
                            id : 'errorLabel',
                            padding : '0 0 0 50',
                            hidden : true,
                            style : {
                                color : '#ff0000'
                            }
                        },
                        {
                            allowBlank : false,
                            fieldLabel : 'User ID',
                            itemId : 'txtUsername',
                            name : 'j_username',
                            stateful : true,
                            stateId : 'username',
                            emptyText : 'User name',
                            enableKeyEvents : true,
                            listeners : {
                                keydown : function (field, e, eOpts) {
                                    if (e.getKey() == Ext.EventObject.ENTER) {
                                        this.up('form').onSubmit();
                                    }
                                }
                            }
                        },
                        {
                            allowBlank : false,
                            fieldLabel : 'Password',
                            itemId : 'txtPassword',
                            name : 'j_password',
                            emptyText : 'Password',
                            inputType : 'password',
                            enableKeyEvents : true,
                            listeners : {
                                keydown : function (field, e, eOpts) {
                                    if (e.getKey() == Ext.EventObject.ENTER) {
                                        this.up('form').onSubmit();
                                    }
                                }
                            }
                        }
                    ],
                    buttons : [
                        {
                            text : 'Reset',
                            itemId : 'btnClean',
                            handler : function () {
                                this.up('form').getForm().reset();
                            }
                        },
                        {
                            text : 'Login',
                            itemId : 'btnLogin',
                            disabled : true,
                            formBind : true,
                            handler : function () {
                                this.up('form').onSubmit();
                            }
                        }
                    ],
                    onSubmit : function () {
                        var formPanel = this;
                        var form = formPanel.getForm();
                        if (form.isValid) {
                            form.submit({
                                method : 'POST',
                                url : 'j_spring_security_check',
                                success : function () {
                                    window.location = "index.jsp";
                                },
                                failure : function () {
                                    var lblField = formPanel.child('label');
                                    lblField.setText('Authentication Failed. Try Again.', false);
                                    lblField.show();
                                    form.reset();
                                    formPanel.down('#txtUsername').focus(false, 100);
                                }
                            })
                        }
                    },
                    listeners : {
                        afterrender : function () {
                            this.down('#txtUsername').focus(false, 100);
                        }
                    }
                },
                {
                    xtype : 'container',
                    style: 'text-align: center;',
                    html : 'Copyright &copy;2013.'
                }
            ]
        });
        me.callParent();
    }
})