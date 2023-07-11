package com.planmate.server.controller;

import com.planmate.server.domain.Subject;
import com.planmate.server.dto.request.subject.SubjectCreateRequestDto;
import com.planmate.server.dto.request.subject.SubjectEditRequestDto;
import com.planmate.server.dto.request.subject.SubjectTimeRequest;
import com.planmate.server.dto.response.subject.SubjectCreateResponse;
import com.planmate.server.dto.response.subject.SubjectResponse;
import com.planmate.server.dto.response.subject.SubjectTimeResponse;
import com.planmate.server.service.subject.SubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/subject")
@Slf4j
@Api(tags = {"과목 관련 API"})
@RequiredArgsConstructor
public class SubjectController {
    private final SubjectService subjectService;

    @GetMapping("/find")
    @ApiOperation("자신의 공부/운동 목록 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "자신의 공부/운동 목록 조회 성공"),
            @ApiResponse(responseCode = "401",description = "해당 사용자가 인증되지 않음 | 토큰 만료"),
            @ApiResponse(responseCode = "403",description = "해당 사용자가 Member 권한이 아님"),
            @ApiResponse(responseCode = "404",description = "자신의 공부/운동 목록 조회 실패함")
    })
    public ResponseEntity<List<SubjectResponse>> findSubject(@RequestParam Long subjectId) {
        List<SubjectResponse> responseList = subjectService.findSubject(subjectId);
        return ResponseEntity.ok(responseList);
    }

    @PostMapping("/create")
    @ApiOperation("새 과목을 생성")
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "새 과목 생성 성공"),
            @ApiResponse(responseCode = "401",description = "해당 사용자가 인증되지 않음 | 토큰 만료"),
            @ApiResponse(responseCode = "403",description = "해당 사용자가 Member 권한이 아님"),
            @ApiResponse(responseCode = "404",description = "새 과목을 생성하는데 실패함")
    })
    public ResponseEntity<SubjectCreateResponse> addSubject(@RequestBody SubjectCreateRequestDto subjectCreateRequestDto) {
        SubjectCreateResponse response = subjectService.createSubject(subjectCreateRequestDto);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/reset")
    @ApiOperation("공부/운동 시간 리셋")
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "공부/운동 시간 업데이트 성공"),
            @ApiResponse(responseCode = "401",description = "해당 사용자가 인증되지 않음 | 토큰 만료"),
            @ApiResponse(responseCode = "403",description = "해당 사용자가 Member 권한이 아님"),
            @ApiResponse(responseCode = "404",description = "공부/운동 시간 업데이트에 실패함")
    })
    public ResponseEntity<Boolean> resetTime() {
        return ResponseEntity.ok(subjectService.initTime());
    }

    @PostMapping("/time")
    @ApiOperation("공부/운동 시간 업데이트")
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "공부/운동 시간 업데이트 성공"),
            @ApiResponse(responseCode = "401",description = "해당 사용자가 인증되지 않음 | 토큰 만료"),
            @ApiResponse(responseCode = "403",description = "해당 사용자가 Member 권한이 아님"),
            @ApiResponse(responseCode = "404",description = "공부/운동 시간 업데이트에 실패함")
    })
    public ResponseEntity<SubjectTimeResponse> updateTime(@RequestBody SubjectTimeRequest subjectTimeRequest) {
        SubjectTimeResponse responseDto = subjectService.updateSubjectTime(subjectTimeRequest);
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/edit")
    @ApiOperation("과목 정보 수정")
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "과목 수정 성공")
    })
    public ResponseEntity<Subject> editSubject(@RequestBody SubjectEditRequestDto subjectEditRequestDto) {
        Subject subject = subjectService.editSubject(subjectEditRequestDto);
        return ResponseEntity.ok(subject);
    }
    
    @DeleteMapping("/remove")
    @ApiOperation("과목 삭제")
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "과목 삭제 성공")
    })
    public ResponseEntity<Boolean> deleteSubject(@RequestParam Long subjectId) {
        subjectService.deleteSubject(subjectId);
        return ResponseEntity.ok(true);
    }
}
