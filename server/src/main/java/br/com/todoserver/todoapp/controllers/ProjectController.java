package br.com.todoserver.todoapp.controllers;

import javax.servlet.http.HttpServletRequest;
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

    @GetMapping("{projectId}")
    private ResponseEntity<SuccessResponse> getOne(@PathVariable("projectId") Long projectId,
            HttpServletRequest request) {
        Long userId = jwtService.getIdFromToken(jwtService.getTokenFromRequest(request));
        return new ResponseEntity<SuccessResponse>(
                new SuccessResponse(projectService.getProject(projectId,
                        userId)),
                HttpStatus.OK);
    }

    @GetMapping
    private ResponseEntity<SuccessResponse> getAll(HttpServletRequest request) {
        Long userId = jwtService.getIdFromToken(jwtService.getTokenFromRequest(request));
        return new ResponseEntity<SuccessResponse>(
                new SuccessResponse(projectService.getProjects(userId)),
                HttpStatus.OK);
    }

    @PutMapping("{projectId}")
    private ResponseEntity<SuccessResponse> update(@PathVariable("projectId") Long projectId,
            @Valid @RequestBody ProjectEntity project, HttpServletRequest request) {
        Long userId = jwtService.getIdFromToken(jwtService.getTokenFromRequest(request));
        return new ResponseEntity<SuccessResponse>(
                new SuccessResponse(projectService.updateProjectName(projectId, userId, project.getName())),
                HttpStatus.OK);
    }

    @DeleteMapping("{projectId}")
    private ResponseEntity<SuccessResponse> delete(@PathVariable("projectId") Long projectId,
            HttpServletRequest request) {
        Long userId = jwtService.getIdFromToken(jwtService.getTokenFromRequest(request));
        return new ResponseEntity<SuccessResponse>(
                new SuccessResponse(projectService.removeProject(projectId, userId)),
                HttpStatus.OK);
    }
}
