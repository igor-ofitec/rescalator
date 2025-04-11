package com.newsnow.rescalator.core.service;

import com.newsnow.rescalator.adapter.repository.TaskRepository;
import com.newsnow.rescalator.common.util.RescalatorUtils;
import com.newsnow.rescalator.core.model.Task;
import com.newsnow.rescalator.core.model.TaskDTO;
import com.newsnow.rescalator.core.model.TaskInfo;
import com.newsnow.rescalator.core.port.TaskService;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Value("${rescalator.local.upload}")
    private String localUpload;

    public TaskInfo createRescaleTask(MultipartFile image, int width, int height) throws IOException, NoSuchAlgorithmException {

        // Guardar la imagen original
        String originalFilename = image.getOriginalFilename();
        File originalFile = new File(localUpload + originalFilename);
        image.transferTo(originalFile);

        // Calcular MD5 del archivo original
        String md5 = RescalatorUtils.calculateMD5(originalFile);

        // Reescalar la imagen
        File resizedFile = new File(localUpload + "resized_" + originalFilename);
        Thumbnails.of(originalFile)
                .size(width, height)
                .toFile(resizedFile);

        // Esto simula la llamada a un servicio de carga en la nube
        String imageUrl = "http://localhost:8081/uploads/resized_" + originalFilename;

        // Crear y guardar la tarea
        Task task = new Task();
        task.setTimestamp(LocalDateTime.now());
        task.setMd5(md5);
        task.setWidth(width);
        task.setHeight(height);
        task.setImageUrl(imageUrl);
        taskRepository.save(task);

        //Devolver el id de la Task creada, no solo la URL

        return new TaskInfo(task.getId(), task.getImageUrl());
    }

    public TaskDTO readTask(Long taskId) {
        Optional<Task> task = taskRepository.findById(taskId);
        return task.map(value -> new TaskDTO(value.getTimestamp(), value.getMd5(), value.getWidth(), value.getHeight(), value.getImageUrl())).orElse(null);
    }
}
