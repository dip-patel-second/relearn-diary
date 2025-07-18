package com.relearnDiary.relearn.diary.service;

import com.relearnDiary.relearn.diary.entity.User;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

public class UserArgumentsProvider implements ArgumentsProvider {
    @Disabled
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
        return Stream.of(
                Arguments.of(User.builder().userName("justtestcovrage").password("rahul").build()),
                Arguments.of(User.builder().userName("okbhai").password("").build())
        );
    }
}
