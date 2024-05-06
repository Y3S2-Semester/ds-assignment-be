package com.microservices.contentservice.core.service.impl;

import com.microservices.contentservice.controller.CourseServiceClient;
import com.microservices.contentservice.core.config.JwtAuthenticationFilter;
import com.microservices.contentservice.core.exception.ModuleException;
import com.microservices.contentservice.core.mapper.MapStructMapper;
import com.microservices.contentservice.core.model.Content;
import com.microservices.contentservice.core.payload.ContentCreateDto;
import com.microservices.contentservice.core.payload.ContentResponseDto;
import com.microservices.contentservice.core.payload.ContentUpdateDto;
import com.microservices.contentservice.core.payload.common.ResponseEntityDto;
import com.microservices.contentservice.core.repository.ContentRepository;
import com.microservices.contentservice.core.service.ContentService;
import com.microservices.contentservice.core.type.ApprovalStatus;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ContentServiceImpl implements ContentService {

    @NonNull
    private final ContentRepository contentRepository;
    @NonNull
    private final MapStructMapper mapper;
    @NonNull
    private final CourseServiceClient courseServiceClient;

    @Override
    public ResponseEntityDto getAllContentByCourseId(String courseId) {
        ResponseEntityDto response = courseServiceClient.getCourseById(courseId, JwtAuthenticationFilter.jwtToken.get());
        if (Objects.equals(response.getStatus(), ResponseEntityDto.UNSUCCESSFUL)) {
            throw new ModuleException("Course id not found");
        }
        List<Content> contentList = contentRepository.findAllByCourseId(courseId);
        List<ContentResponseDto> contentResponseDtoList = mapper.contentListToContentResponseDtoList(contentList);
        return new ResponseEntityDto(false, contentResponseDtoList);
    }

    @Override
    public ResponseEntityDto addContentToCourse(String courseId, ContentCreateDto contentCreateDto) {
        Content contentToSave = mapper.contentCreateDtoToContent(contentCreateDto);
        //Optional<Object> course = null; //todo interservice communication
        contentToSave.setCourseId(courseId);
        contentToSave.setVisible(false);
        contentToSave.setActive(true);
        contentToSave.setApproved(ApprovalStatus.AWAITING);
        contentToSave = contentRepository.save(contentToSave);
        return new ResponseEntityDto(false, contentToSave);
    }

    @Override
    public ResponseEntityDto updateContent(String id, ContentUpdateDto contentUpdateDto) {
        Optional<Content> optionalContent = contentRepository.findById(id);
        if (optionalContent.isEmpty())
            throw new ModuleException("Content not found to update");

        Content content = optionalContent.get();
        if (contentUpdateDto.getContentTitle() != null)
            content.setContentTitle(contentUpdateDto.getContentTitle());

        if (contentUpdateDto.getContentAccessLink() != null)
            content.setContentAccessLink(contentUpdateDto.getContentAccessLink());

        if (contentUpdateDto.getVisible() != null)
            content.setVisible(content.getVisible());

        content.setUpdatedAt(new Date());
        content = contentRepository.save(content);
        return new ResponseEntityDto(false, content);
    }

    @Override
    public ResponseEntityDto getContentById(String id) {
        Optional<Content> content = contentRepository.findById(id);
        if (content.isEmpty())
            throw new ModuleException("Course content not found");

        return new ResponseEntityDto(false, mapper.contentToContentResponseDto(content.get()));
    }

    @Override
    public ResponseEntityDto archiveContent(String id) {
        Optional<Content> optionalContent = contentRepository.findById(id);
        if (optionalContent.isEmpty())
            throw new ModuleException("Course content not found");
        Content content = optionalContent.get();
        content.setActive(false);
        content.setUpdatedAt(new Date());
        content = contentRepository.save(content);
        return new ResponseEntityDto(false, content);
    }

    @Override
    public ResponseEntityDto getAllContentAwaitingApproval() {
        List<Content> contentList = contentRepository.findAllByApproved(ApprovalStatus.AWAITING);
        List<ContentResponseDto> contentResponseDtoList = mapper.contentListToContentResponseDtoList(contentList);
        return new ResponseEntityDto(false, contentResponseDtoList);
    }

    @Override
    public ResponseEntityDto updateApprovalOfContent(String id, ApprovalStatus approvalStatus) {
        Optional<Content> optionalContent = contentRepository.findById(id);
        if (optionalContent.isEmpty())
            throw new ModuleException("Course content not found");
        Content content = optionalContent.get();
        content.setApproved(approvalStatus);
        content.setPublishedDate(new Date());
        return new ResponseEntityDto(false, mapper.contentToContentResponseDto(content));
    }
}
