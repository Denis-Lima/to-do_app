package br.com.todoserver.todoapp.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.todoserver.todoapp.dtos.TaskDTO;
import br.com.todoserver.todoapp.entities.TaskEntity;
import br.com.todoserver.todoapp.exceptions.UnauthorizedPermissionException;
import br.com.todoserver.todoapp.responses.SuccessResponse;
import br.com.todoserver.todoapp.services.JWTService;
import br.com.todoserver.todoapp.services.TaskService;

@RestController
@CrossOrigin
@RequestMapping("/tasks")
public class TaskController {
    @Autowired
    TaskService taskService;

    @Autowired
    JWTService jwtService;

    @PostMapping
    public ResponseEntity<SuccessResponse> create(@Valid @RequestBody TaskEntity task, HttpServletRequest request) {
        if (!jwtService.isSamePerson(task.getProject().getUser().getId(), request.getCookies()))
            throw new UnauthorizedPermissionException("You can't create task for other users");
        return new ResponseEntity<SuccessResponse>(new SuccessResponse(taskService.createTask(task)),
                HttpStatus.CREATED);
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<SuccessResponse> update(@PathVariable("taskId") Long taskId,
            @Valid @RequestBody TaskDTO newTask, HttpServletRequest request) {
        return new ResponseEntity<SuccessResponse>(
                new SuccessResponse(taskService.updateTask(taskId, newTask,
                        jwtService.getIdFromToken(jwtService.getTokenFromRequest(request)))),
                HttpStatus.OK);
    }

    @DeleteMapping("/{taskId}")
    public void delete(@PathVariable("taskId") Long taskId, HttpServletRequest request, HttpServletResponse response) {
        taskService.deleteTask(taskId, jwtService.getIdFromToken(jwtService.getTokenFromRequest(request)));
        response.setStatus(200);
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<SuccessResponse> getOne(@PathVariable("taskId") Long taskId, HttpServletRequest request) {
        return new ResponseEntity<SuccessResponse>(new SuccessResponse(taskService.getTask(taskId,
                jwtService.getIdFromToken(jwtService.getTokenFromRequest(request)))), HttpStatus.OK);
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<SuccessResponse> getAll(@PathVariable("projectId") Long projectId,
            HttpServletRequest request) {
        return new ResponseEntity<SuccessResponse>(new SuccessResponse(taskService.getTasks(
                projectId,
                jwtService.getIdFromToken(jwtService.getTokenFromRequest(request)))), HttpStatus.OK);
    }
}
