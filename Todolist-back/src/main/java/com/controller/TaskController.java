package com.controller;


import com.common.lang.Result;
import com.entity.Task;
import com.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 
 * @since 2021-12-19
 */
@RestController
@RequestMapping("/task")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @PostMapping("/save")
    public Result save( @RequestBody Task task) {
        taskService.save(task);
        return Result.succ(task);
    }
    @PostMapping("/delete")
    public Result delete(@RequestBody Long[] ids) {
        taskService.removeByIds(Arrays.asList(ids));
        return Result.succ("哈哈");
    }
    @PostMapping("/update")
    public Result update(@RequestBody  Task task) {

        taskService.updateById(task);
        return Result.succ(task);
    }

}
