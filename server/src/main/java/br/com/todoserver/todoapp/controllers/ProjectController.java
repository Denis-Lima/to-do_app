package br.com.todoserver.todoapp.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.todoserver.todoapp.entities.ProjectEntity;
import br.com.todoserver.todoapp.responses.SuccessResponse;
import br.com.todoserver.todoapp.services.JWTService;
import br.com.todoserver.todoapp.services.ProjectService;

@RestController
@CrossOrigin
@RequestMapping("/projects")
public class ProjectController {
    @Autowired
    ProjectService projectService;

    @Autowired
    JWTService jwtService;

    @PostMapping
    private ResponseEntity<SuccessResponse> create(@Valid @RequestBody ProjectEntity project,
            HttpServletRequest request) {
        Long userId = jwtService.getIdFromToken(jwtService.getTokenFromRequest(request));
        return new ResponseEntity<SuccessResponse>(
                new SuccessResponse(projectService.createProject(project.getName(), userId)),
                HttpStatus.CREATED);
    }
}
