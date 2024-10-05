package com.spbut.ib12vp.kanban.model.enums;

import io.swagger.annotations.ApiModel;

@ApiModel
public enum TaskStatus {
    CREATED, TODO, INFO, INPROGRESS, REVIEW, DONE, CANCEL
}