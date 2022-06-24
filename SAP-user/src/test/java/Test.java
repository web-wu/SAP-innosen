import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;

/**
 * @ProjectName: SAP-innosen
 * @Author: tabwu
 * @Date: 2022/6/11 17:05
 * @Description:
 */

public class Test {

    @org.junit.Test
    public void codeGenerater() {
        FastAutoGenerator.create("jdbc:mysql://123.57.193.24:3306/yls_user", "root", "136436")
                .globalConfig(builder -> {
                    builder.author("tabwu") // 设置作者
                            .enableSwagger() // 开启 swagger 模式
//                            .fileOverride() // 覆盖已生成文件
                            .outputDir("C:\\JAVA_E\\Project_study\\SAP-innosen\\SAP-user\\src\\main\\java"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.tabwu.SAP") // 设置父包名
                            .moduleName("user") // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml, "C:\\JAVA_E\\Project_study\\SAP-innosen\\SAP-user\\src\\main\\resources\\mapper")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.controllerBuilder().enableRestStyle();
                    builder.addInclude("yls_user","yls_role","yls_user_role","yls_permission","yls_role_permission") // 设置需要生成的表名
                            .addTablePrefix("yls_", "yls_"); // 设置过滤表前缀
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }


}
