package com.relearnDiary.relearn.diary.service;

import com.relearnDiary.relearn.diary.entity.User;
import com.relearnDiary.relearn.diary.diaryEntryRepo.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@Disabled
public class UserDetailServiceImplTest {

    @InjectMocks
    private UserDetailServiceImpl userDetailService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // ✅ use openMocks (newer)
    }


    @Test
    void loadByUserNameTest() {
        when(userRepository.findByUserName(ArgumentMatchers.anyString()))
                .thenReturn(User.builder()
                        .userName("Ram")
                        .password("something")
                        .roles(new ArrayList<>())
                        .build());

        UserDetails user = userDetailService.loadUserByUsername("Ram");
        assertNotNull(user);
    }
}
