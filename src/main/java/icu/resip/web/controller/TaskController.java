package icu.resip.web.controller;

import icu.resip.entity.TaskVo;
import icu.resip.qo.TaskQo;
import icu.resip.result.Result;
import icu.resip.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 任务资源控制器
 * @Author Peng
 * @Date 2022/4/16
 */
@RestController
@RequestMapping("/api/task")
public class TaskController {

    private TaskService taskService;

    @Autowired
    public void setTaskService(TaskService taskService) {
        this.taskService = taskService;
    }

    // 查询任务列表
    @PutMapping("/list")
    public Result<List<TaskVo>> list(@RequestBody(required = false) TaskQo taskQo) {
        // 保证service层的TaskQo对象不为null
        if (taskQo ==null) {
            taskQo = new TaskQo();
        }
        // 按条件查询任务列表
        List<TaskVo> taskVoList = taskService.list(taskQo);
        return Result.success(taskVoList);
    }

}
