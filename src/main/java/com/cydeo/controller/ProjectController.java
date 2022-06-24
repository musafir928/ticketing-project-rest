package com.cydeo.controller;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.entity.ResponseWrapper;
import com.cydeo.service.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/project")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/all-projects")
    public ResponseEntity<ResponseWrapper> getALlProjects() {
        return ResponseEntity
                .ok(
                        new ResponseWrapper(
                                "successfully got all projects",
                                projectService.listAllProjects(),
                                HttpStatus.OK
                        )
                );
    }

    @GetMapping("/{projectCode}")
    public ResponseEntity<ResponseWrapper> getProjectByCode(@PathVariable("projectCode") String projectCode) {
        return ResponseEntity
                .ok(
                        new ResponseWrapper(
                                "successfully got project for project code" + projectCode,
                                projectService.getByProjectCode(projectCode),
                                HttpStatus.OK
                        )
                );
    }

    @PostMapping
    public ResponseEntity<ResponseWrapper> createProject(@RequestBody ProjectDTO projectDTO) {
        projectService.save(projectDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        new ResponseWrapper(
                                "project successfully created",
                                HttpStatus.CREATED
                        )
                );
    }

    @PutMapping
    public ResponseEntity<ResponseWrapper> updateProject(@RequestBody ProjectDTO projectDTO) {
        projectService.update(projectDTO);
        return ResponseEntity
                .ok(
                        new ResponseWrapper(
                                "project successfully updated",
                                projectDTO,
                                HttpStatus.OK
                        )
                );
    }

    @DeleteMapping("/{projectCode}")
    public ResponseEntity<ResponseWrapper> deleteProject(@PathVariable("projectCode") String projectCode) {
        projectService.delete(projectCode);
        return ResponseEntity
                .ok(
                        new ResponseWrapper(
                                "project successfully deleted",
                                HttpStatus.OK
                        )
                );
    }

    @GetMapping("/manager/project-status")
    public ResponseEntity<ResponseWrapper> getProjectByManager() {
        List<ProjectDTO> projects = projectService.listAllProjectDetails();
        return ResponseEntity
                .ok(
                        new ResponseWrapper(
                                "get all project by manager",
                                projects,
                                HttpStatus.OK
                        )
                );
    }

    @PutMapping("/manager/complete/{projectCode}")
    public ResponseEntity<ResponseWrapper> managerCompleteProject(@PathVariable("projectCode") String projectCode) {
        projectService.complete(projectCode);
        return ResponseEntity
                .ok(
                        new ResponseWrapper(
                                "project: " + projectCode + " successfully completed",
                                HttpStatus.OK
                        )
                );
    }

}
