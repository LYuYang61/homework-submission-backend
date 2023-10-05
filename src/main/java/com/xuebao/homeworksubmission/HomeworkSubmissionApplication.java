package com.xuebao.homeworksubmission;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.xuebao.homeworksubmission.mapper")
public class HomeworkSubmissionApplication {

    public static void main(String[] args) {
        SpringApplication.run(HomeworkSubmissionApplication.class, args);
    }

}
