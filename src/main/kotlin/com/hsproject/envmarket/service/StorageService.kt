package com.hsproject.envmarket.service

import com.hsproject.envmarket.config.StorageProperties
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption

@Service
class StorageService(private val storageProperties: StorageProperties) {

    private val uploadDir: Path = Paths.get(storageProperties.uploadDir)

    init {
        Files.createDirectories(uploadDir)
    }

    fun storeFile(file: MultipartFile): String {
        val filename = StringUtils.cleanPath(file.originalFilename!!)
        val targetLocation = uploadDir.resolve(filename)
        Files.copy(file.inputStream, targetLocation, StandardCopyOption.REPLACE_EXISTING)

        return "/uploads/$filename"
    }
}