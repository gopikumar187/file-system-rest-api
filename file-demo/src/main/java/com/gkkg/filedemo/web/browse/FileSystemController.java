package com.gkkg.filedemo.web.browse;

import com.gkkg.filedemo.Service.FileService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/filesystem")
@Api(tags = {"File Resource"})
@SwaggerDefinition(
        tags = {
                @Tag(
                        name = "File Resource",
                        description = "File Resource to save/load files.")
        })
public class FileSystemController {

    private static final String  SUCCESS_STATUS="File Saved Successfully.";
    private static final String  FAIL_STATUS="Unable to uploadFile.";
    @Autowired FileService fileService;


        @PostMapping(value="/getFile",produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
        @ApiOperation(
                value = "To retrieve file.",
                notes = "To retrieve File")
        @ApiResponses(
                value = {
                        @ApiResponse(
                                code = 200,
                                message = "Successfully retrieved file.",
                                response = String.class),
                        @ApiResponse(code = 404, message = "File Not Found")
                })
            public ResponseEntity<byte[]> getFile(@RequestParam(name="fileName")String fileName,@RequestParam(name="filePath") String filePath) {
               byte[] content = fileService.load(fileName,filePath);
            HttpHeaders headers=new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType("application/pdf"));
            headers.setContentDispositionFormData(fileName, "attachment;filename"+fileName);
               if(content.length>0){
                   return new ResponseEntity<byte[]>(content,headers, HttpStatus.OK);
               }else{
                   return new ResponseEntity<byte[]>(content,headers, HttpStatus.NOT_FOUND);
               }


        }

        @PostMapping(value="/uploadFile",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
        @ApiOperation(
                value = "To upload file.",
                notes = "To upload File")
        @ApiResponses(
                value = {
                        @ApiResponse(
                                code = 200,
                                message = "Successfully uploaded file.",
                                response = String.class),
                        @ApiResponse(code = 404, message = "Unable to upload File")
                })
        public ResponseEntity<String> uploadFile(@RequestParam(name="file",required=false) MultipartFile file
                ,@RequestParam(value="fileName",required=false) String fileName) {

                String result= null;
                if(fileService.save(file,fileName)){
                    result=SUCCESS_STATUS;
                }else{
                    result =FAIL_STATUS;
                }


                return new ResponseEntity<String>(result,HttpStatus.OK);

        }
}
