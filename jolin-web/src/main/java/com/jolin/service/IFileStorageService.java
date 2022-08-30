package com.jolin.service;

import com.jolin.common.domain.CommonDomain;
import com.jolin.common.exception.BaseException;
import com.jolin.common.service.IBaseService;
import com.jolin.dto.FileStorageDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IFileStorageService<D extends CommonDomain> extends IBaseService<FileStorageDTO, D> {
    FileStorageDTO saveFile(MultipartFile file, String relativePath, String replacedFileName) throws BaseException, IOException;

    //获取文件上传的根路径
    String getFileStorageRootPath();
}
