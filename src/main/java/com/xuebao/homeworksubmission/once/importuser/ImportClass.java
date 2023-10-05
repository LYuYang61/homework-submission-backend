package com.xuebao.homeworksubmission.once.importuser;

import com.alibaba.excel.EasyExcel;
import com.xuebao.homeworksubmission.model.domain.ClassTableInfo;

import java.util.List;

/**
 * 导入班级同学到数据库
 *
 * @author lyy
 */
public class ImportClass {
    public static void main(String[] args) {
        String fileName = "src\\main\\resources\\计科21-5班.xlsx";
        listenerRead(fileName);
        //synchronousRead(fileName);
        }
    /**
     * 监听器读
     * @param fileName
     */
    public static void listenerRead(String fileName) {
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        // 这里每次会读取100条数据 然后返回过来 直接调用使用数据就行
        EasyExcel.read(fileName, ClassTableInfo.class, new TableListener()).sheet().doRead();
    }

    /**
     * 同步读
     * @param fileName
     */
    public static void synchronousRead(String fileName) {
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 同步读取会自动finish
        List<ClassTableInfo> list = EasyExcel.read(fileName).head(ClassTableInfo.class).sheet().doReadSync();
        for (ClassTableInfo data : list) {
            System.out.println(data);
        }
    }
}
