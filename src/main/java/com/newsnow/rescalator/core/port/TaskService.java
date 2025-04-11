package com.newsnow.rescalator.core.port;

import com.newsnow.rescalator.core.model.TaskDTO;
import com.newsnow.rescalator.core.model.TaskInfo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public interface TaskService {

    TaskInfo createRescaleTask(MultipartFile image, int width, int height) throws IOException, NoSuchAlgorithmException;

    TaskDTO readTask(Long taskId);
}
