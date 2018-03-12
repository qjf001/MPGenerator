
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.toolkit.StringUtils;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by geli on 2018/1/7.
 * mpg 运行示例
 * 必要条件 ： mpg 包 2.1.9版本 ； jdbc.properties 配置文件
 */
public class MPGenerator {

    static Properties properties = new Properties();
    static String projectRootPath = "D://";
    static{
        try {
            properties.load(MPGenerator.class.getClass().getResourceAsStream("/jdbc.properties"));//
        } catch (IOException e) {
            e.printStackTrace();
        }

        try{
            projectRootPath = new File("").getCanonicalPath();// 工程根路径
            if(properties.size()==0)
                properties.load(new FileInputStream(new File(projectRootPath+"\\src\\main\\resources\\jdbc.properties")));
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    private static final String author = "QJF";
    private static final String dbRriverName = properties.get("jdbc.driver").toString();
    private static final String dbUrl = properties.get("jdbc.url").toString();
    private static final String dbUser = properties.get("jdbc.username").toString();
    private static final String dbPwd = properties.get("jdbc.password").toString();
    private static final String packageName = "com.qjf.demo";
    private static final String superControllerClass = "com.qjf.common.BasicController";
    // 生成文件保存路径
    private static final String outputDir = projectRootPath+"\\target\\MPGenerator";
//    private static final String outputDir = projectRootPath+"\\src\\main\\java"; // !!会覆盖工程已有文件

    @Test
    public void generateCode() {
        String[] tableNames = new String[]{"t_qrtz_console"};
        generateByTables(tableNames);
    }

    private void generateByTables(String[] tableNames) {
        generateByTables(packageName, "t_", tableNames);
    }

    private void generateByTables(String packageName, String prefix, String... tableNames) {
        String[] tablePrefix = new String[tableNames.length];
        for (int i = 0; i < tableNames.length; i++) {
            tablePrefix[i] = prefix;
        }

        GlobalConfig config = new GlobalConfig();

        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setDbType(DbType.MYSQL)
                .setUrl(dbUrl)
                .setUsername(dbUser)
                .setPassword(dbPwd)
                .setTypeConvert(new MySqlTypeConvert() {
                    // 自定义数据库表字段类型转换【可选】
                    @Override
                    public DbColumnType processTypeConvert(String fieldType) {
                        System.out.println("转换类型：" + fieldType);
                        // 注意！！processTypeConvert 存在默认类型转换，如果不是你要的效果请自定义返回、非如下直接返回。
                        if (fieldType.toLowerCase().contains("tinyint"))
                            return DbColumnType.TINYINT;

                        if (fieldType.toLowerCase().contains("point"))
                            return DbColumnType.OBJECT;
                        return super.processTypeConvert(fieldType);
                    }
                })
                .setDriverName(dbRriverName);
        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig
                .setCapitalMode(true)
                .setEntityLombokModel(false)
                .setDbColumnUnderline(true)
                .setTablePrefix(tablePrefix)// 表前缀
                .setNaming(NamingStrategy.underline_to_camel)// 实体字段驼峰命名
                .entityTableFieldAnnotationEnable(true)// 开启实体类字段注解
                .setInclude(tableNames);//修改替换成你需要的表名，多个表名传数组
                // 自定义 controller 父类
                if(!StringUtils.isEmpty(superControllerClass))
                    strategyConfig.setSuperControllerClass(superControllerClass);

        config.setActiveRecord(false)
                .setEnableCache(false)// 二级缓存
                .setBaseResultMap(true)
                .setBaseColumnList(true)
                .setAuthor(author)
                .setMapperName("%sMapper")// %s 会以实体类名填充
                .setXmlName("%sMapper")
                .setServiceName("%sService")
                .setServiceImplName("%sServiceImpl")
                .setControllerName("%sController")
                .setListName("%s_list")
                .setEditName("%s_edit")
                .setViewSuffix(".ftl")// 视图文件尾缀
                .setOutputDir(outputDir)
                .setOpen(false)// 生成文件后打开目录
                .setFileOverride(true);// 覆盖同名文件

        AutoGenerator mpg = new AutoGenerator();
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.setGlobalConfig(config)
                .setDataSource(dataSourceConfig)
                .setStrategy(strategyConfig)
                .setPackageInfo(
                        new PackageConfig()
                                .setParent(packageName)
                                .setController("controller")
                                .setEntity("entity")
                                .setMapper("mapper")
                                .setService("service")
                                .setServiceImpl("service.impl")
                                .setXml("mapper")
                                .setList("ftl")
                                .setEdit("ftl")
                );

        TemplateConfig tc = new TemplateConfig();// 默认模板路径 /resources/templates
//         tc.setController("...");// 如果自定义的模板路径不是默认路径，修改为自己的路径
//         tc.setEntity(null);// 设置为null 则不生成该类
//         tc.setMapper(null);
//         tc.setXml(null);
//         tc.setService(null);
//         tc.setServiceImpl(null);
//         如上任何一个模块如果设置 空 OR Null 将不生成该模块。
         mpg.setTemplate(tc);

        mpg.execute();
    }

}
