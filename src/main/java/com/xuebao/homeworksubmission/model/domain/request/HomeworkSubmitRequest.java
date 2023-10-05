package com.xuebao.homeworksubmission.model.domain.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class HomeworkSubmitRequest implements Serializable {
    private static final long serialVersionUID = 3191241716373120793L;

    private String homeworkID;

    private String userAccount;
}
