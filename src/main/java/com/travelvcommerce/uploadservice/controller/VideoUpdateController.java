package com.travelvcommerce.uploadservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.travelvcommerce.uploadservice.dto.*;
import com.travelvcommerce.uploadservice.service.AwsS3Service;
import com.travelvcommerce.uploadservice.service.KafkaVideoInfoProducerService;
import com.travelvcommerce.uploadservice.service.VideoUpdateService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/upload-service")
public class VideoUpdateController {
    @Autowired
    private VideoUpdateService videoUpdateService;
    @Autowired
    private AwsS3Service awsS3Service;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private KafkaVideoInfoProducerService kafkaVideoInfoProducerService;

    @PutMapping("/videos/{userId}/{videoId}/thumbnail")
    public ResponseEntity<ResponseDto> updateThumbnail(@PathVariable("userId") String userId,
                                                       @PathVariable("videoId") String videoId,
                                                       @RequestPart(value = "thumbnail") MultipartFile thumbnail) {

        DenormalizedVideoDto denormalizedVideoDto;

        try {
            denormalizedVideoDto = videoUpdateService.updateThumbnail(userId, videoId, thumbnail, awsS3Service);
        } catch (NoSuchElementException e) {
            ResponseDto responseDto = ResponseDto.buildResponseDto(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDto);
        } catch (IllegalArgumentException e) {
            ResponseDto responseDto = ResponseDto.buildResponseDto(e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(responseDto);
        } catch (RuntimeException e) {
            ResponseDto responseDto = ResponseDto.buildResponseDto(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDto);
        }

        kafkaVideoInfoProducerService.putDenormalizeData(denormalizedVideoDto);
        VideoDto.VideoUpdateResponseDto videoUpdateResponseDto = modelMapper.map(denormalizedVideoDto, VideoDto.VideoUpdateResponseDto.class);
        ResponseDto responseDto = ResponseDto.buildResponseDto(objectMapper.convertValue(videoUpdateResponseDto, Map.class));

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @PutMapping("/videos/{userId}/{videoId}/tags")
    public ResponseEntity<ResponseDto> updateTags(@PathVariable("userId") String userId,
                                                  @PathVariable("videoId") String videoId,
                                                  @RequestBody TagDto.TagRequestDto tagRequestDto) {
        DenormalizedVideoDto denormalizedVideoDto;

        List<String> tagIdList = tagRequestDto.getTagIds();

        try {
            denormalizedVideoDto = videoUpdateService.updateTags(userId, videoId, tagIdList);
        } catch (NoSuchElementException e) {
            ResponseDto responseDto = ResponseDto.buildResponseDto(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDto);
        } catch (IllegalArgumentException e) {
            ResponseDto responseDto = ResponseDto.buildResponseDto(e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(responseDto);
        } catch (RuntimeException e) {
            ResponseDto responseDto = ResponseDto.buildResponseDto(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDto);
        }

        kafkaVideoInfoProducerService.putDenormalizeData(denormalizedVideoDto);
        VideoDto.VideoUpdateResponseDto videoUpdateResponseDto = modelMapper.map(denormalizedVideoDto, VideoDto.VideoUpdateResponseDto.class);
        ResponseDto responseDto = ResponseDto.buildResponseDto(objectMapper.convertValue(videoUpdateResponseDto, Map.class));

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @PutMapping("/videos/{userId}/{videoId}/ads")
    public ResponseEntity<ResponseDto> updateAds(@PathVariable("userId") String userId,
                                                 @PathVariable("videoId") String videoId,
                                                 @RequestBody AdDto.AdModifyRequestsDto adModifyRequestsDto) {
        DenormalizedVideoDto denormalizedVideoDto;

        List<AdDto.AdModifyRequestDto> adModifyRequestDtoList = adModifyRequestsDto.getAdModifyRequests();

        try {
            denormalizedVideoDto = videoUpdateService.updateAds(userId, videoId, adModifyRequestDtoList);
        } catch (NoSuchElementException e) {
            ResponseDto responseDto = ResponseDto.buildResponseDto(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDto);
        } catch (IllegalArgumentException e) {
            ResponseDto responseDto = ResponseDto.buildResponseDto(e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(responseDto);
        } catch (RuntimeException e) {
            ResponseDto responseDto = ResponseDto.buildResponseDto(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDto);
        }

        kafkaVideoInfoProducerService.putDenormalizeData(denormalizedVideoDto);
        VideoDto.VideoUpdateResponseDto videoUpdateResponseDto = modelMapper.map(denormalizedVideoDto, VideoDto.VideoUpdateResponseDto.class);
        ResponseDto responseDto = ResponseDto.buildResponseDto(objectMapper.convertValue(videoUpdateResponseDto, Map.class));

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @PutMapping("/videos/{userId}/{videoId}/title")
    public ResponseEntity<ResponseDto> updateVideoName(@PathVariable("userId") String userId,
                                                       @PathVariable("videoId") String videoId,
                                                       @RequestBody VideoDto.VideoNameUpdateRequestDto videoNameUpdateRequestDto) {
        DenormalizedVideoDto denormalizedVideoDto;

        String videoName = videoNameUpdateRequestDto.getVideoName();

        try {
            denormalizedVideoDto = videoUpdateService.updateVideoName(userId, videoId, videoName);
        } catch (NoSuchElementException e) {
            ResponseDto responseDto = ResponseDto.buildResponseDto(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDto);
        } catch (IllegalArgumentException e) {
            ResponseDto responseDto = ResponseDto.buildResponseDto(e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(responseDto);
        } catch (RuntimeException e) {
            ResponseDto responseDto = ResponseDto.buildResponseDto(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDto);
        }

        kafkaVideoInfoProducerService.putDenormalizeData(denormalizedVideoDto);
        VideoDto.VideoUpdateResponseDto videoUpdateResponseDto = modelMapper.map(denormalizedVideoDto, VideoDto.VideoUpdateResponseDto.class);
        ResponseDto responseDto = ResponseDto.buildResponseDto(objectMapper.convertValue(videoUpdateResponseDto, Map.class));

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

//    @PutMapping("/videos/uploaders/{userId}")
//    public ResponseEntity<ResponseDto> updateUploader(@PathVariable("userId") String userId,
//                                                      @RequestBody UploaderDto.UploaderModifyRequestDto
//                                                              uploaderModifyRequestDto) {
//        List<String> updatedVideoIdList;
//
//        try {
//            updatedVideoIdList = videoUpdateService.updateUploader(userId, uploaderModifyRequestDto);
//        } catch (NoSuchElementException e) {
//            ResponseDto responseDto = ResponseDto.buildResponseDto(e.getMessage());
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDto);
//        } catch (RuntimeException e) {
//            ResponseDto responseDto = ResponseDto.buildResponseDto(e.getMessage());
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
//        }
//
//        updatedVideoIdList.forEach(videoId -> contentServiceDataSyncService.putDenormalizeData(videoId, UpdatedField.UPLOADER));
//
//        ResponseDto responseDto = ResponseDto.buildResponseDto(Collections.singletonMap("updatedVideos", updatedVideoIdList));
//
//        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
//    }
}
