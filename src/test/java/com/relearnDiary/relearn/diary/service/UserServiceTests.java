package com.relearnDiary.relearn.diary.service;

import com.relearnDiary.relearn.diary.entity.User;
import com.relearnDiary.relearn.diary.diaryEntryRepo.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@Disabled
class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @ParameterizedTest
    @ArgumentsSource(UserArgumentsProvider.class)
     void testFindByUserName(User user) {
        // Simulate: user does NOT exist in DB
        when(userRepository.findByUserName(user.getUserName())).thenReturn(null);
        // Simulate: user is saved successfully
        when(userRepository.save(any(User.class))).thenReturn(user);

        boolean result = userService.saveNewUser(user);
        assertTrue(result); // ✅ This will now pass
    }
}
