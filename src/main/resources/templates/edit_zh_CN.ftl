<script type="text/javascript">

    var formRows = [
        {type: "hidden", formName: "id", formId: "id", value: "<#noparse>${data.id}</#noparse>"}
    <#list table.fields as field>
        <#if field_index &gt; 1 >
        , {formName: '${field.propertyName}', labelName: '<#if field.comment?exists && field.comment!=''>${field.comment}<#else>${field.propertyName}</#if>', type: "text",<#if field.propertyType=='Date'>classes: "layui-input-datetime",</#if>value: "<#noparse>${data.</#noparse>${field.propertyName}<#noparse>}</#noparse>"}
        </#if>
    </#list>
        , {
            type: "button", formName: "buttonGroup", options: [
                {btName: "保存", filterName: "save", laySubmit: true, type: "button", clickEvent: "page_save_ajax('save');"},
                {btName: "重置", btId: "reset", type: "reset"}
            ]
        }
    ];
</script>

