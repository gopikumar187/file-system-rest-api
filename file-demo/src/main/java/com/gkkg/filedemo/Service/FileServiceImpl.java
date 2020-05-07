package com.gkkg.filedemo.Service;

import com.gkkg.filedemo.exception.CustomFileNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Service
public class FileServiceImpl implements FileService {

   @Value("${local.path}")
   private String destLocation;


    @Override
    public Boolean save(MultipartFile file,String fileName) {
        return writeFileToLocalPath(file,fileName);

    }

    @Override
    public byte[] load(String fileName, String filePath) {
        byte[] content=null;
        try {
                ByteArrayOutputStream  byteArrayOutputStream=getFileFromPath(filePath,fileName);
                content=byteArrayOutputStream.toByteArray();
            }
        catch (Exception ex){
            throw new CustomFileNotFoundException("File not found " + fileName + "exception-->"+ex.getMessage());

        }
        return content;
    }


    private ByteArrayOutputStream getFileFromPath(String path, String fileName) throws IOException {
        ByteArrayOutputStream bos=null;
        FileInputStream fis=null;
        try {
            StringBuilder builder=new StringBuilder();
            builder.append(path).append(fileName);


            File file=new File(builder.toString());
            bos=new ByteArrayOutputStream();
            fis=new FileInputStream(file);
            final byte[] buf=new byte[20480];
            for(int readNum;(readNum=fis.read(buf))!=-1;) {
                bos.write(buf,0,readNum);

            }
            return bos;
        }catch (Exception e) {
            // TODO: handle exception
        }finally {
            if(bos!=null) {
                bos.close();
            }

            if(fis!=null) {
                fis.close();
            }
        }

        return null;
    }

    private boolean writeFileToLocalPath(MultipartFile uploadFile,String fileName) {
        String fileNameOriginal=uploadFile.getOriginalFilename();
        File fileToSave=new File(destLocation+fileName);
        try {
            uploadFile.transferTo(fileToSave);
            return true;
        } catch (IllegalStateException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }

    }
}
