package cw.project.x1.component.endpoint;

import cw.project.x1.commons.ServiceRespDTO;
import cw.project.x1.component.FileStorageService;
import cw.project.x1.dto.UploadFileDTO;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping(path = "/api/files", produces = MediaType.APPLICATION_JSON_VALUE)
public class FileUploadCtrl {
    public static Logger log = LoggerFactory.getLogger(FileUploadCtrl.class);

    @Autowired
    FileStorageService fss;

    @ApiOperation("upload files to backend")
    @PostMapping("/upload-file")
    public ServiceRespDTO<UploadFileDTO> uploadFile(@RequestParam("file") MultipartFile file) {
        String fileName = fss.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
            .path("/files/")
            .path(fileName)
            .toUriString();

        return new ServiceRespDTO<>(new UploadFileDTO(
            fileName,
            fileDownloadUri,
            file.getContentType(),
            file.getSize()));
    }


    @ApiOperation("upload files to backend")
    @PostMapping("/upload-files")
    public ServiceRespDTO<List<UploadFileDTO>> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
        if (ArrayUtils.isEmpty(files)) {
            throw new IllegalArgumentException("not files");
        }

        return new ServiceRespDTO<>(Stream.of(files).parallel()
            .map(this::uploadFile)
            .map(spd -> spd.data)
            .collect(Collectors.toList()));
    }

}
