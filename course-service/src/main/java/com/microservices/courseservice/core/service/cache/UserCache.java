package com.microservices.courseservice.core.service.cache;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.microservices.courseservice.core.payload.fiegn.ResponseEntityDto;
import com.microservices.courseservice.core.payload.fiegn.UserResponseDto;
import com.microservices.courseservice.core.payload.fiegn.enums.Role;
import com.microservices.courseservice.core.service.client.UserServiceClient;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.*;

@Component
public class UserCache {

    @Autowired
    private Gson gson;

    @NonNull
    private final UserServiceClient userServiceClient;

    private Map<String, UserResponseDto> userMap = new HashMap<>();

    public UserCache(@NonNull UserServiceClient userServiceClient) {
        this.userServiceClient = userServiceClient;
    }

    public List<UserResponseDto> saveCallbackDetails() {
        ResponseEntityDto responseEntity = userServiceClient.getAllUserByOptionalRole(Role.INSTRUCTOR);
        List<UserResponseDto> userResponseDtos = new ArrayList<>();

        if (responseEntity != null && responseEntity.getStatus().equals("successful")) {
            userResponseDtos.addAll((Collection<? extends UserResponseDto>) responseEntity.getResults());

            for (UserResponseDto user : userResponseDtos) {
                userMap.put(user.getId(), user);
            }
        } else {
            System.out.println("Error: Unable to fetch user data.");
        }

        return userResponseDtos;
    }



    public UserResponseDto getUserByUserId(String userId) {
        if (userMap.isEmpty()) {
            return updateUserMapAndGetUser(userId);
        } else {
            if (userMap.containsKey(userId)) {
                return userMap.get(userId);
            } else {
                userMap = new HashMap<>();
                return updateUserMapAndGetUser(userId);
            }
        }
    }

    public UserResponseDto updateUserMapAndGetUser(String userId) {
        List<UserResponseDto> usersList = saveCallbackDetails();
        for (UserResponseDto user : usersList) {
            userMap.put(user.getId(), user);
        }
        return userMap.get(userId);
    }
}
