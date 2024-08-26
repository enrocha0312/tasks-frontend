package com.cursojenkins.tasksfrontend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Todo {
    private Long id;
    private String task;
    @DateTimeFormat(pattern="dd/MM/yyyy")
    private LocalDate dueDate;
    @Override
    public String toString() {
        return "Todo [id=" + id + ", task=" + task + ", dueDate=" + dueDate + "]";
    }
}
