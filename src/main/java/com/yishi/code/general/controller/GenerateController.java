//package com.yishi.code.general.controller;
//
//import com.alibaba.fastjson.JSON;
//import com.yishi.code.general.dto.*;
//import com.yishi.code.general.example.LoadFunction;
//import com.yishi.code.general.model.CodeForm;
//import com.yishi.code.general.modify.RequestMappingHandlerMappingX;
//import com.yishi.code.general.service.TableInfoService;
//import com.yishi.code.general.service.impl.GenerateServieImpl;
//import com.yishi.code.general.util.RegexUtil;
//import com.yishi.code.general.x.XObject;
//import com.yishi.code.general.x.XObjectDirector;
//import com.yishi.code.general.x.impl.*;
//import com.datanew.dao.BaseDao;
//import com.datanew.dao.BaseDao1;
//import com.datanew.exception.RedirectException;
//import org.springframework.beans.factory.support.BeanDefinitionBuilder;
//import org.springframework.beans.factory.support.DefaultListableBeanFactory;
//import org.springframework.context.ConfigurableApplicationContext;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.stereotype.Controller;
//import org.springframework.util.MultiValueMap;
//import org.springframework.util.ReflectionUtils;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.context.ContextLoader;
//import org.springframework.web.method.HandlerMethod;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.multipart.MultipartHttpServletRequest;
//import org.springframework.web.servlet.handler.AbstractHandlerMethodMapping;
//
//import javax.annotation.Resource;
//import javax.servlet.http.HttpServletResponse;
//import java.io.*;
//import java.lang.reflect.Field;
//import java.lang.reflect.InvocationTargetException;
//import java.lang.reflect.Method;
//import java.net.URLEncoder;
//import java.sql.Connection;
//import java.sql.DatabaseMetaData;
//import java.sql.SQLException;
//import java.text.SimpleDateFormat;
//import java.util.*;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//
//@Controller
//@RequestMapping("/")
//public class GenerateController {
//    static Map dictMapping;
//
//    static {
//        dictMapping = new HashMap();
//        dictMapping.put("select", "BaseBasicdataselect");
//        dictMapping.put("tree", "CodeForm");
//        dictMapping.put("comboTree", "CodeForm");
//        dictMapping.put("searchTree", "CodeForm");
//
//    }
//
//    private ExecutorService fixedThreadPool = Executors.newFixedThreadPool(30);
//    @Resource
//    private GenerateServieImpl generateServie;
//    @Resource
//    private BaseDao baseDao;
//
//    @RequestMapping("getDictSelectData")
//    @ResponseBody
//    private Object getDictSelectData(String controlType) {
//
//        String targetTableName = (String) dictMapping.get(controlType);
//        if (targetTableName == null)
//            return new ArrayList<>();
//        return generateServie.getDictSelectData(controlType, targetTableName);
//
//    }
//
//    private void saveFile(InputStream in, File file, boolean append) throws IOException {
//        try (FileOutputStream out = new FileOutputStream(file, append)
//        ) {
//            int by = -1;
//            while ((in.read()) != -1) {
//                out.write(by);
//            }
//        } finally {
//            try {
//                in.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//    }
//
//    private FileInfo saveFileInfo(MultipartFile file) throws IOException {
//        String id = UUID.randomUUID().toString();
//        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("filetemp/" + id), "utf-8"))) {
//            FileInfo info = new FileInfo();
//            String idref = UUID.randomUUID().toString();
//            info.name = file.getOriginalFilename();
//            info.id = id;
//            info.size = file.getSize();
//            info.type = file.getContentType();
//            info.date = System.nanoTime();
//            info.idref = idref;
//            bw.write("name:" + file.getOriginalFilename() + "\n");
//            bw.write("size:" + file.getSize() + "\n");
//            bw.write("date:" + System.currentTimeMillis() + "\n");
//            bw.write("type:" + file.getContentType() + "\n");
//            bw.write("id:" + id + "\n");
//            bw.write("idref:" + idref + "\n");
//            return info;
//        }
//    }
//
//    @RequestMapping("upload")
//    @ResponseBody
//    public Object webupload(MultipartHttpServletRequest req, Integer chunks, Integer chunk, String name) throws IOException {
//        MultiValueMap<String, MultipartFile> map = req.getMultiFileMap();
//        Integer count = 0;
//        Iterator<String> iter = map.keySet().iterator();
//        File dir = new File("filetemp");
//        if (!dir.exists()) dir.mkdirs();
//        System.out.println(dir.getAbsolutePath());
//        String idstr = null;
//        while (iter.hasNext()) {
//            String str = (String) iter.next();
//            List<MultipartFile> fileList = map.get(str);
//            FileInfo info = null;
//            for (MultipartFile multipartFile : fileList) {
//                System.out.println(multipartFile.getOriginalFilename());
//                if (chunks != null && chunks > 1) {
////                    //如果chunk==0,则代表第一块碎片,不需要合并
////                    if(chunk==0){
////                        info=saveFileInfo(multipartFile);
////                    }
////                    File tempFile = new File("filetemp/" + info.getIdref()+"_temp");
////
////                    saveFile(multipartFile.getInputStream(), tempFile, chunk == 0 ? false : true);
////                    //上传并合并完成，则将临时名称更改为指定名称
////                    if (chunks - chunk == 1) {
////                        String suffix=info.name.replaceAll(".*\\.([^\\.]+)$","$1");
////
////                        File targetFile=new File("filetemp/"+info.getIdref()+suffix);
////                        tempFile.renameTo(targetFile);
////                    }
//                    Result result = Result.fail();
//                    result.setMsg("chunk upload is temporary not provided");
//                    return result;
//                } else {
//                    info = saveFileInfo(multipartFile);
//                    String suffix = info.name.replaceAll(".+(\\.[^\\.]+)$", "$1");
//
//                    //否则直接将文件内容拷贝至新文件
//                    File targetFile = new File("filetemp/" + info.getIdref() + suffix);
//                    multipartFile.transferTo(targetFile);
//                    idstr = info.id;
//                }
//            }
//        }
//        Map map1 = new HashMap();
//        map1.put("name", "ceshi");
//        map1.put("type", "exe");
//        map1.put("size", "1024");
//        map1.put("id", idstr);
//        map1.put("uploadDate", "");
//        return map1;
//    }
//
//    @ResponseBody
//    @RequestMapping("fileInfo")
//    public Object fileInfo(String id) throws IOException {
//        if (id == null) return null;
//        List list = new ArrayList();
//        for (String s : id.split(",")) {
//            try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("filetemp/" + s), "utf-8"))) {
//
//                String name = br.readLine().replaceAll("^name\\:", "");
//                String size = br.readLine().replaceAll("^size\\:", "");
//                String date = new SimpleDateFormat("yyyy/MM/dd").format(new Date(Long.parseLong(br.readLine().replaceAll("^date\\:", ""))));
//                String type = name.replaceAll("(.+\\.)(.+)$", "$2");
//                String idStr = br.readLine();
//                String idref = br.readLine();
//
//                Map map = new HashMap();
//                map.put("name", name);
//                map.put("type", type);
//                map.put("size", size);
//                map.put("uploadDate", date);
//                map.put("fileId", s);
//                list.add(map);
//            }
//
//        }
//        return list;
//    }
//
//    @RequestMapping("download")
//    public void downloadFile(String id, HttpServletResponse response) {
//        try{
//            response.setContentType("text/plain;charset=utf-8");
//            String[] ids = id.split(",");
//            Set<String> idSet = new HashSet<>();
//            for (String id1 : ids) {
//                idSet.add(id1.trim());
//
//            }
//            if (idSet.size() == 1) {
//                try (FileInputStream in = new FileInputStream("filetemp/" + idSet.iterator().next())) {
//                    int b;
//                    while ((b = in.read()) > -1) {
//                        response.getOutputStream().write(b);
//                    }
//                }
//
//            } else if (idSet.size() > 1) {
//                try (FileOutputStream out = new FileOutputStream("filetemp/" + System.nanoTime() + ".zip");) {
//                    ZipCode zipCode = new ZipCode(out, null);
//                    Set<String> nameSet = new HashSet<>();
//                    for (String s : idSet) {
//                        try (FileReader fr = new FileReader("filetemp/" + s + ".txt")) {
//
//                        }
//                    }
//                }
//
//            }
//        }catch (Exception e){
//            throw new RedirectException(e);
//        }
//    }
//
//    @RequestMapping("delete")
//    @ResponseBody
//    public Object deleteFile(String id) {
//        return new HashMap<>();
//    }
//
//
//    @RequestMapping("getBaseConfig")
//    @ResponseBody
//    public Object getBaseConfig(String tableName) {
//        return null;
//    }
//
//
//
//    @RequestMapping("loadTableConfig")
//    @ResponseBody
//    public Object loadTableConfig(String content) throws IOException {
//        List<TableMeta> tableMetas = JSON.parseArray(content, TableMeta.class);
//        TableInfoService tableInfoService = ContextLoader.getCurrentWebApplicationContext().getBean(TableInfoService.class);
//
//        Map<String, TableMeta> referencemap = new HashMap<>();
//        for (TableMeta t : tableMetas) {
//            generateServie.parseTableMetas(referencemap, t, tableInfoService);
//        }
//        tableMetas.clear();
//        for (Map.Entry<String, TableMeta> en : referencemap.entrySet()) {
//            tableMetas.add(en.getValue());
//        }
//
//        String rootPath ;
//        rootPath =Thread.currentThread().getContextClassLoader().getResource("/").getPath();
//        for (TableMeta t : tableMetas) {
//            new LoadFunction(t, rootPath).run();
//        }
//        return Result.success();
//
//    }
//
//    @RequestMapping("unloadTableConfig")
//    @ResponseBody
//    public Object unloadTableConfig(String content) throws IOException {
//        List<TableMeta> tableMetas = JSON.parseArray(content, TableMeta.class);
//        String rootPath = null;
//        rootPath = new ClassPathResource("").getFile().getAbsolutePath();
//        for (TableMeta t : tableMetas) {
//            new LoadFunction(t, rootPath).unload();
//        }
//
//        return Result.success();
//
//    }
//
//    @RequestMapping("getTableMeta")
//    @ResponseBody
//    public Object getTableMeta(String schema, String table, String columnNamePattern) throws SQLException {
//        try (Connection conn = baseDao.getConnection();) {
//            DatabaseMetaData dbMetaData = conn.getMetaData();
//            TableMeta tableMeta = new TableMeta(dbMetaData, null, schema, table, columnNamePattern);
//            return tableMeta;
//        }
//    }
//
//    @RequestMapping("getSchemas")
//    @ResponseBody
//    public Object getSchemas(String schemaPattern) throws SQLException {
//        try (Connection conn = baseDao.getConnection();) {
//            DatabaseMetaData dbMetaData = conn.getMetaData();
//            DataBaseMeta tableMeta = new DataBaseMeta(dbMetaData, null, schemaPattern);
//            return tableMeta;
//        }
//    }
//
//    @RequestMapping("getTables")
//    @ResponseBody
//    public Object getTables(String schemaPattern, String tableNamePattern) throws SQLException {
//        try (Connection conn = baseDao.getConnection()) {
//            DatabaseMetaData dbMetaData = conn.getMetaData();
//            SchemaMeta tableMeta = new SchemaMeta(dbMetaData, null, schemaPattern, tableNamePattern, new String[]{"TABLE", "VIEW"});
//            return tableMeta;
//        }
//    }
//
//    @RequestMapping("getSequences")
//    @ResponseBody
//    public Object getSequenceS(String schemaPattern, String tableNamePattern) throws SQLException {
//        try (Connection conn = baseDao.getConnection();) {
//            DatabaseMetaData dbMetaData = conn.getMetaData();
//            SchemaMeta tableMeta = new SchemaMeta(dbMetaData, null, schemaPattern, tableNamePattern, new String[]{"SEQUENCE"});
//            return tableMeta;
//        }
//    }
//
//    /***
//     * @method      process
//     * @author      kedou
//     * @version
//     * @see         导出代码
//      * @param content    对应的配置项
//     * @param response
//     * @return      void
//     * @exception
//     * @date        2018/10/18 14:50
//     */
//    @RequestMapping("process")
//    public void process(String content, HttpServletResponse response) throws ClassNotFoundException, InvocationTargetException, IllegalAccessException, IOException {
//
//        generateServie.process(content,response);
//    }
//
//    @RequestMapping("processWithoutModify")
//    public void processWithoutModify(String schema, String table, String columnNamePattern, HttpServletResponse response) throws SQLException, IOException {
//        try (Connection conn = baseDao.getConnection();) {
//            DatabaseMetaData dbMetaData = conn.getMetaData();
//            TableMeta t = new TableMeta(dbMetaData, null, schema, table, columnNamePattern);
//            String fileName = t.getEntityName() + ".zip";
//            OutputStream bOut = new ByteArrayOutputStream();
//            ZipCode zipCode = new ZipCode(bOut, null);
//            zipCode.setEncode(t.getEncode());
//            String prefix = t.getJavaPathPrefix();
//            String htmlPrefix = t.getHtmlPathPrefix();
//
//            XObject entity = new XObjectDirector(new EntityBuilder(t)).construct();
//            XObject controller = new XObjectDirector(new ControllerBuilder(t)).construct();
//            XObject impl = new XObjectDirector(new ServiceImplBuilder(t)).construct();
//            XObject service = new XObjectDirector(new ServiceBuilder(t)).construct();
//            zipCode.write(entity.getPath(prefix), entity.render());
//            zipCode.write(controller.getPath(prefix), controller.render());
//            zipCode.write(impl.getPath(prefix), impl.render());
//            zipCode.write(service.getPath(prefix), service.render());
//            String hqlGenPath = RegexUtil.formateduplicateChar(prefix + ".com.datanew.util.unalterable.HqlGen", ".", File.separator) + ".java";
//            String pagePath = RegexUtil.formateduplicateChar(prefix + ".com.datanew.dto.unalterable.Page", ".", File.separator) + ".java";
//            String pageDataPath = RegexUtil.formateduplicateChar(prefix + ".com.datanew.dto.unalterable.PageData", ".", File.separator) + ".java";
//            String regexUtil = RegexUtil.formateduplicateChar(prefix + ".com.datanew.util.unalterable.RegexUtil", ".", File.separator) + ".java";
//            String html = RegexUtil.formateduplicateChar(htmlPrefix + "." + RegexUtil.firstToLower(t.getEntityName()), ".", File.separator) + ".html";
//            zipCode.write(hqlGenPath, new ClassPathResource(RegexUtil.formateDir("code/template/HqlGen.template")).getInputStream());
//            zipCode.write(pagePath, new ClassPathResource(RegexUtil.formateDir("code/template/Page.template")).getInputStream());
//            zipCode.write(pageDataPath, new ClassPathResource(RegexUtil.formateDir("code/template/PageData.template")).getInputStream());
//            zipCode.write(regexUtil, new ClassPathResource(RegexUtil.formateDir("code/template/RegexUtil.template")).getInputStream());
//            zipCode.write(html, new HtmlGenerator().buildHtml(t));
//
//            zipCode.close();
//
//            response.setContentType("application/octet-stream");
//            response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + URLEncoder.encode(fileName, "utf-8"));
//            response.getOutputStream().write(((ByteArrayOutputStream) bOut).toByteArray());
//
//
//        }
//    }
//
//    public void load(Class clazz) throws InvocationTargetException, IllegalAccessException {
//        //将applicationContext转换为ConfigurableApplicationContext
////        ConfigurableApplicationContext configurableApplicationContext = (ConfigurableApplicationContext) applicationContext;
//        ConfigurableApplicationContext configurableApplicationContext = (ConfigurableApplicationContext) ContextLoader.getCurrentWebApplicationContext();
//        ;
//
//        // 获取bean工厂并转换为DefaultListableBeanFactory
//        DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) configurableApplicationContext.getBeanFactory();
//
//        // 通过BeanDefinitionBuilder创建bean定义
//        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(clazz);
//
//        // 设置属性userService,此属性引用已经定义的bean:userService,这里userService已经被spring容器管理了.
////        beanDefinitionBuilder.addPropertyReference("userService", "userService");
//
//        // 注册bean
//
//        defaultListableBeanFactory.registerBeanDefinition(RegexUtil.firstToLower(clazz.getSimpleName()), beanDefinitionBuilder.getRawBeanDefinition());
//
//
//        Object bean = configurableApplicationContext.getBean(RegexUtil.firstToLower(clazz.getSimpleName()));
////		defaultListableBeanFactory.removeBeanDefinition("requestMappingHandlerMapping");
////        RequestMappingHandlerMapping requestMappingHandlerMapping=configurableApplicationContext.getBean(RequestMappingHandlerMappingX.class);
//        AbstractHandlerMethodMapping rmapping = configurableApplicationContext.getBean(RequestMappingHandlerMappingX.class);
////		Method getMappingForMethod =ReflectionUtils.findMethod(RequestMappingHandlerMapping.class, "getMappingForMethod",Method.class,Class.class);
//        Method getMappingForMethod = ReflectionUtils.findMethod(AbstractHandlerMethodMapping.class, "detectHandlerMethods", Object.class);
//        getMappingForMethod.setAccessible(true);
////		RequestMappingInfo mapping_info = (RequestMappingInfo) getMappingForMethod.invoke(requestMappingHandlerMapping, getMappingForMethod,GenerateController.class);
//        Field handmethods = ReflectionUtils.findField(AbstractHandlerMethodMapping.class, "handlerMethods");
//        handmethods.setAccessible(true);
//        Map<Object, HandlerMethod> handlerMethodMap = (Map<Object, HandlerMethod>) handmethods.get(rmapping);
//        if (clazz.isAnnotationPresent(Controller.class))
//            getMappingForMethod.invoke(rmapping, bean);
////        return userController.toAction("动态注册生成调用");
//
//        //删除bean.
//        //defaultListableBeanFactory.removeBeanDefinition("testService");
//    }
//
//    void loadEntity(Class clazz) {
//        ((BaseDao1) baseDao).obtainSessionFactory(clazz);
////        ConfigurableApplicationContext configurableApplicationContext = (ConfigurableApplicationContext) ContextLoader.getCurrentWebApplicationContext();   ;
////
////        try {
////            configurableApplicationContext.getBean(LocalSessionFactoryBean.class).afterPropertiesSet();
////        } catch (IOException e) {
////            e.printStackTrace();
////        }
//
//        // 获取bean工厂并转换为DefaultListableBeanFactory
//
//
//    }
//
//    @RequestMapping("/e1")
//    @ResponseBody
//    public Object test(String type) {
//        switch (type) {
//            case "1":
//                throw new RuntimeException("运行时异常");
//            case "2":
//                throw new IllegalArgumentException("运行时异常");
//            case "3":
//                throw new NumberFormatException("数字格式化异常");
//
//        }
//        return null;
//
//    }
//
//    @RequestMapping("/e2")
//    public void test1(String type) {
//        switch (type) {
//            case "1":
//                throw new RuntimeException("运行时异常");
//            case "2":
//                throw new IllegalArgumentException("运行时异常");
//            case "3":
//                throw new NumberFormatException("数字格式化异常");
//            case "4":
//                throw new RedirectException("转发");
//        }
//
//    }
//}
