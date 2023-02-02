package com.api.ecommerce.api;

import com.api.ecommerce.dtos.UserDto;
import com.api.ecommerce.dtos.UserListDto;
import com.api.ecommerce.dtos.UserListPaginationDto;
import com.api.ecommerce.dtos.UserStatusDto;
import com.api.ecommerce.services.UserService;
import com.api.ecommerce.util.FileUploadUtil;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/v1/users")
@Slf4j
public class UserApi {

    private final UserService userService;

    public UserApi(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserDto> save(@RequestPart(name = "image", required = false) MultipartFile image, @RequestPart("userDto") UserDto userDto) {

        // Image Name
        String fileName = image.getOriginalFilename();
        if (fileName != null && !fileName.isEmpty()) {
            userDto.setPhotos(fileName);
        } else {
            userDto.setPhotos("default.png");
        }

        // Save user
        UserDto savedUser = userService.save(userDto);

        // Save image
        if (fileName != null && !fileName.isEmpty() && savedUser != null) {
            String uploadDir = "user-photos/" + savedUser.getId();
            try {
                FileUploadUtil.saveFile(uploadDir, fileName, image);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> findById(@PathVariable Long id) {
        return new ResponseEntity<>(userService.findById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<UserListPaginationDto> findAll(
            @RequestParam(value = "page", defaultValue = "0", required = false) int page,
            @RequestParam(value = "size", defaultValue = "10", required = false) int size,
            @RequestParam(value = "order", defaultValue = "ASC", required = false) Sort.Direction direction,
            @RequestParam(value = "sort", required = false) String sortBy,
            @RequestParam(value = "keyword", required = false) String keyword) {
        return new ResponseEntity<>(userService.findAll(page,size,direction,sortBy, keyword), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<UserDto> update(@Valid @RequestBody UserDto userDto) {
        return new ResponseEntity<>(userService.update(userDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/existsByEmail")
    public ResponseEntity<Boolean> existsByEmail(@RequestParam String email) {
        return new ResponseEntity<>(userService.existsByEmail(email), HttpStatus.OK);
    }

    @PutMapping("/{id}/enabled/{status}")
    public ResponseEntity<UserStatusDto> updateStatus(@PathVariable Long id, @PathVariable Boolean status) {
        return new ResponseEntity<>(userService.updateStatus(id,status), HttpStatus.OK);
    }
}
