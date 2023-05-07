package com.blog.blogapp.services.impl;

import com.blog.blogapp.services.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {


    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {
        //file name
        String name = file.getOriginalFilename();


        //random name generate file
        String randomID = UUID.randomUUID().toString();
        String fileName1 = randomID.concat(name.substring(name.lastIndexOf(".")));

        //Full path
        String filePath  = path + File.separator + fileName1;

        File f = new File(path);
        if(!f.exists()){
            f.createNewFile();
        }

        Files.copy(file.getInputStream(), Paths.get(filePath));

        return fileName1;


    }



    @Override
    public InputStream getResource(String path, String fileName) throws FileNotFoundException {
    String fullPath = path + File.separator + fileName;
    InputStream is = new FileInputStream(fullPath);

    //db logic is to return inputstream
    return is;


    }
}

