<#assign serviceName = table.serviceName?uncap_first />
package ${package.Controller};

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.qjf.common.MyPage;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

<#if restControllerStyle>
import org.springframework.web.bind.annotation.RestController;
<#else>
import org.springframework.stereotype.Controller;
</#if>
<#if superControllerClassPackage??>
import ${superControllerClassPackage};
import javax.annotation.Resource;
</#if>

import com.qjf.common.ResultMap;
import com.qjf.demo.entity.${table.entityName};
import com.qjf.demo.service.${table.serviceName};
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;

import java.util.Map;

/**
 * <p>
 * <#if table.comment??>${table.comment}</#if> 前端控制器
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#if restControllerStyle>
@RestController
<#else>
@Controller
</#if>
@RequestMapping("<#if package.ModuleName??>/${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>")
<#if kotlin>
class ${table.controllerName}<#if superControllerClass> : ${superControllerClass}()</#if>

<#else>
    <#if superControllerClass??>
public class ${table.controllerName} extends ${superControllerClass}
<#else>
public class ${table.controllerName}
</#if>
{
    private static final Logger logger = LoggerFactory.getLogger(${table.controllerName}.class);

    @Resource
    private ${table.serviceName} ${serviceName};

    /**
     * @Author:${author}
     * @Date: ${date}
     * @Description: 列表页面
     */
    @RequestMapping(value = "list",method = RequestMethod.GET)
    public String list(){
        return COMMON_LIST;
    }

    /**
     * @Author:${author}
     * @Date: ${date}
     * @Description: 列表页面数据
     */
    @ResponseBody
    @RequestMapping(value = "listData",method = RequestMethod.GET,produces = {"application/json;charset=UTF-8"})
    public MyPage<${table.entityName}> listData(Page<${table.entityName}> page,HttpServletRequest req){
        EntityWrapper<${table.entityName}> wrapper = new EntityWrapper<>();
        return new MyPage(${serviceName}.selectPage(page,wrapper));
    }

    /**
     * @Author:${author}
     * @Date: ${date}
     * @Description: 保存方法
     */
    @RequestMapping(value = "save",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map save(${table.entityName} record,BindingResult bindingResult) {
        ResultMap rm = new ResultMap();
        try {
            if(bindingResult.hasErrors())
                return rm;
            ${serviceName}.insertOrUpdate(record);
            rm.setSuccess();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return rm;
    }

    /**
     * @Author:${author}
     * @Date: ${date}
     * @Description: 编辑页面
     */
    @RequestMapping(value = "edit",method = RequestMethod.GET)
    public String edit(Model model, Integer id) {
        if(null!=id)
            model.addAttribute("data", ${serviceName}.selectById(id));
        return COMMON_EDIT;
     }

    /**
     * @Author:${author}
     * @Date: ${date}
     * @Description: 删除
     */
    @RequestMapping(value = "delete",method = RequestMethod.DELETE,produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map delete(Integer id) {
        ResultMap rm = new ResultMap();
        try {
            ${serviceName}.deleteById(id);
            rm.setSuccess();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return rm;
     }
}
</#if>