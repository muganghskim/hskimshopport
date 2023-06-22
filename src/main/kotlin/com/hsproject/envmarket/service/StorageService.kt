package com.hsproject.envmarket.service

import com.hsproject.envmarket.config.StorageProperties
import lombok.extern.slf4j.Slf4j
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption

@Slf4j
@Service
class StorageService(private val storageProperties: StorageProperties) {

    val logger: Logger = LoggerFactory.getLogger(StorageService::class.java)

    private val uploadDir: Path = Paths.get(storageProperties.uploadDir)

    init {
        Files.createDirectories(uploadDir)
    }

    fun storeFile(file: MultipartFile): String {
        try {

            logger.info("경로 {}" , uploadDir)
            val filename = StringUtils.cleanPath(file.originalFilename!!)
            logger.info("파일 이름: {}", filename)

            val targetLocation = uploadDir.resolve(filename)
            logger.info("타겟 위치: {}", targetLocation)

            Files.createDirectories(uploadDir)
            Files.copy(file.inputStream, targetLocation, StandardCopyOption.REPLACE_EXISTING)
            logger.info("파일 저장 성공")

            val fileUrl = "/img/$filename"
            logger.info("저장된 파일 URL: {}", fileUrl)

            return fileUrl

        } catch (e: Exception) {
            logger.error("파일 저장 실패", e)
            throw e
        }
    }

}