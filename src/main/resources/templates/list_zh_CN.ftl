<script type="text/javascript">

    var bts = [
        {btName: "新增", clickEvent: "openPage('edit','編輯',true,2);", icon: "&#xe608;", classes: "layui-btn-sm"}
    ];
 <#assign  lindx = 0 >
    var searchInputs = [
    <#list table.fields as field>
        <#if field.name!="id">
        <#if lindx!=0>,</#if> {formName: "${field.propertyName}", labelName: "<#if field.comment?exists && field.comment!=''>${field.comment}<#else>${field.name}</#if>", type: "text"}
            <#assign  lindx = lindx+1 >
        </#if>
    </#list>
    ];
    var columns = [
        {title: 'ID', field: 'id', width: 1}
    <#list table.fields as field>
        <#if field_index &gt; 1 >
        , {field: '${field.propertyName}', title: '<#if field.comment?exists && field.comment!=''>${field.comment}<#else>${field.name}</#if>', width: 1}
        </#if>
    </#list>
        , {field: 'op', title: '操作', align: 'center', width: 1}
    ]
</script>
