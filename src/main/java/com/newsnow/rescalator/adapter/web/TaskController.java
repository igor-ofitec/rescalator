package com.newsnow.rescalator.adapter.web;

import com.newsnow.rescalator.core.model.TaskDTO;
import com.newsnow.rescalator.core.model.TaskInfo;
import com.newsnow.rescalator.core.port.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/task")
public class TaskController {

    TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @Operation(summary = "Create a new rescale task", description = "Receives an image and specific dimensions to rescale it.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Task created successfully",
                    content = @Content(schema = @Schema(implementation = TaskInfo.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping
    public ResponseEntity<TaskInfo> createRescaleTask(
            @RequestParam("image") MultipartFile image,
            @RequestParam("width") int width,
            @RequestParam("height") int height) throws IOException, NoSuchAlgorithmException {
        return new ResponseEntity<>(taskService.createRescaleTask(image, width, height), HttpStatus.CREATED);
    }

    @Operation(summary = "Get task information", description = "Returns the information associated with the task.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task found",
                    content = @Content(schema = @Schema(implementation = TaskDTO.class))),
            @ApiResponse(responseCode = "404", description = "Task not found")
    })
    @GetMapping("/{taskid}")
    public ResponseEntity<TaskDTO> getTask(@PathVariable Long taskid) {
        TaskDTO task = taskService.readTask(taskid);
        if (task != null) {
            return new ResponseEntity<>(task, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}


