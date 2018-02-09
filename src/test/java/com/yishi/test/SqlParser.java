package com.yishi.test;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.util.SelectUtils;
import net.sf.jsqlparser.util.TablesNamesFinder;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SqlParser{
    // 获取sql语句中的所有表名
// 可以获取任意类型sql语句的全部表名，这里使用的select sql
// **********传入String 得到List<String>,嵌套已测试
    public static List<String> test_select_table(String sql)
            throws JSQLParserException {
        Statement statement = (Statement) CCJSqlParserUtil.parse(sql);
        Select selectStatement = (Select) statement;
        TablesNamesFinder tablesNamesFinder = new TablesNamesFinder();

        List<String> tableList = tablesNamesFinder
                .getTableList(selectStatement);
        return tableList;
    }
    public static List<?> getSelectItems(String sql) throws JSQLParserException {
        Statement statement = (Statement) CCJSqlParserUtil.parse(sql);
        Select selectStatement = (Select) statement;
        List list=((PlainSelect) ((Select) statement).getSelectBody()).getSelectItems();
        return list;
    }

    public static void main(String[] args) throws JSQLParserException {
        System.out.println(getSelectItems("select  name as a,id as id from a"));;
        System.out.println(judge_type("select  *  from a"));
    }

    // 验证sql语法正确性，返回错误信息
// 传入 String sql
// ***********返回错误信息such as： “错误单词” “line 1” “column 80”
    public static String judge_type(String sql) {

        try {
            Statement statement = (Statement) CCJSqlParserUtil.parse(sql);
        } catch (JSQLParserException e) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            e.printStackTrace(new PrintStream(baos));
            String exception = baos.toString();
            String regEx = "Encountered(.*)";
            Pattern pat = Pattern.compile(regEx);
            Matcher mat = pat.matcher(exception);
            while (mat.find()) {
                exception = mat.group(1);
            }
            // System.out.println(exception);
            String line = "";
            String regEx2 = "line (.*),";
            pat = Pattern.compile(regEx2);
            mat = pat.matcher(exception);
            while (mat.find()) {
                line = mat.group(1);
            }
            // System.out.println(line);

            int line_num = Integer.valueOf(line).intValue();
            int indexofcolumn = exception.indexOf("column");
            String errornumber = exception.substring(indexofcolumn + 7,
                    exception.length() - 1);
            int error_num = Integer.valueOf(errornumber).intValue();
            System.out.println(error_num);

            String ERROR_location = "";
            if (error_num != 1) {
                String sql_sub = sql.substring(0, error_num - 2); // 发生错误位置前面的字符串
                // 错误信息单词往往处于错误位置的前一个地方单词
                // 获取错误位置两个前面两个空格之间的单词，并保存
                sql_sub = new StringBuilder(sql_sub).reverse().toString();
                int indexofspace = sql_sub.indexOf(" ");
                String sql_error = sql_sub.substring(0, indexofspace);
                sql_error = new StringBuilder(sql_error).reverse().toString();
                ERROR_location = "\"" + sql_error + "\"" + " at line "
                        + line_num + " at column " + error_num;
            } else {
                int indexofspace = sql.indexOf(" ");
                String sql_error = sql.substring(0, indexofspace);
                ERROR_location = "\"" + sql_error + "\"" + " at line "
                        + line_num + " at column " + error_num;
            }
            return ERROR_location; // 错误信息的返回
        }
        String result = "correct";
        return result; // Jsql可以解析，返回correct
    }
}