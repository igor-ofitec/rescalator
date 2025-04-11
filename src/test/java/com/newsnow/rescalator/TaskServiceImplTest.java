package com.newsnow.rescalator;

import com.newsnow.rescalator.adapter.repository.TaskRepository;
import com.newsnow.rescalator.core.model.Task;
import com.newsnow.rescalator.core.model.TaskDTO;
import com.newsnow.rescalator.core.service.TaskServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private MultipartFile mockMultipartFile;

    @InjectMocks
    private TaskServiceImpl taskServiceImpl;

    @BeforeEach
    public void setUp() {
    }

    @Test
    public void testCreateRescaleTaskIOException() throws IOException, NoSuchAlgorithmException {
        given(mockMultipartFile.getOriginalFilename()).willReturn("Oso.jpg");
        doThrow(new IOException()).when(mockMultipartFile).transferTo(any(File.class));

        assertThrows(IOException.class, () -> {
            taskServiceImpl.createRescaleTask(mockMultipartFile, 100, 100);
        });
    }

    @Test
    public void testReadTaskSuccess() {
        Long taskId = 1L;
        Task task = new Task();
        task.setId(taskId);
        task.setTimestamp(LocalDateTime.now());
        task.setMd5("mock-md5");
        task.setWidth(100);
        task.setHeight(100);
        task.setImageUrl("http://localhost:8081/uploads/resized_Oso.jpg");

        given(taskRepository.findById(taskId)).willReturn(Optional.of(task));

        TaskDTO taskDTO = taskServiceImpl.readTask(taskId);

        assertNotNull(taskDTO);
        assertEquals(task.getMd5(), taskDTO.getMd5());
        assertEquals(task.getWidth(), taskDTO.getWidth());
        assertEquals(task.getHeight(), taskDTO.getHeight());
        assertEquals(task.getImageUrl(), taskDTO.getImageUrl());
    }

    @Test
    public void testReadTaskNotFound() {
        Long taskId = 1L;

        given(taskRepository.findById(taskId)).willReturn(Optional.empty());

        TaskDTO taskDTO = taskServiceImpl.readTask(taskId);

        assertNull(taskDTO);
    }
}