package com.sps.electronic.store.services.impl;

import com.sps.electronic.store.exceptions.BadApiRequest;
import com.sps.electronic.store.services.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    private Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    @Override
    public String uploadFile(MultipartFile file, Path path) throws IOException {

        String originalFilename = file.getOriginalFilename();
        logger.info("fileName : {}", originalFilename);

        //here original file name is same so we have to generate randomly
        String filename = UUID.randomUUID().toString();

        //to take .png
        String extention = originalFilename.substring(originalFilename.lastIndexOf("."));

        //now join this file name with the extention like abc.png
        String fileNameWithExtention = filename + extention;

        String fullPathWithFileName = path+ File.separator + fileNameWithExtention;

        logger.info("full image path: {}", fullPathWithFileName);

        if (extention.equalsIgnoreCase(".png") || extention.equalsIgnoreCase(".jpg") || extention.equalsIgnoreCase(".jpeg")){

            //file save
            logger.info("file extention is :{}", extention);

            File folder = new File(String.valueOf(path));

            if (!folder.exists()){
                //create the folder
                folder.mkdirs();
            }

            //upload a file
            Files.copy(file.getInputStream(), Paths.get(fullPathWithFileName));
            return fileNameWithExtention;

        }else {
            throw new BadApiRequest("file with this"+extention+" not allowed !!");
        }

    }

    @Override
    public InputStream getResource(String path, String name) throws FileNotFoundException {
        String fullPath = path + File.separator+name;

        InputStream inputStream = new FileInputStream(fullPath);
        return inputStream;
    }
}
