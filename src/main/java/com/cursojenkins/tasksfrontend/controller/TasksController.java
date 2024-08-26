package com.cursojenkins.tasksfrontend.controller;

import com.cursojenkins.tasksfrontend.model.Todo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class TasksController {
    @Value("${backend.host}")
    private String BACKEND_HOST;
    @Value("${backend.port}")
    private String BACKEND_PORT;
    @Value("${app.version}")
    private String VERSION;
    public String getBackendURL() {
        return "http://" + BACKEND_HOST + ":" + BACKEND_PORT;
    }
    @GetMapping("")
    public String index(Model model){
        model.addAttribute("todos", getTodos());
        if(VERSION.startsWith("build"))
            model.addAttribute("version", VERSION);
        return "index";
    }
    @GetMapping("add")
    public String add(Model model) {
        model.addAttribute("todo", new Todo());
        return "add";
    }
    @PostMapping("save")
    public String save(Todo todo, Model model) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.postForObject(
                    getBackendURL() + "/tasks-backend/todo", todo, Object.class);
            model.addAttribute("success", "Success!");
            return "index";
        } catch(Exception e) {
            Pattern compile = Pattern.compile("message\":\"(.*)\",");
            Matcher m = compile.matcher(e.getMessage());
            m.find();
            model.addAttribute("error", m.group(1));
            model.addAttribute("todo", todo);
            return "add";
        } finally {
            model.addAttribute("todos", getTodos());
        }
    }

    @SuppressWarnings("unchecked")
    private List<Todo> getTodos() {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(
                getBackendURL() + "/tasks-backend/todo", List.class);
    }
}
