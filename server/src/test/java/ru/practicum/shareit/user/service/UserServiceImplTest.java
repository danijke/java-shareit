package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.exception.EmailConflictException;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repo.UserRepository;

import static org.assertj.core.api.Assertions.assertThatThrownBy;


@SpringBootTest
@Transactional
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class UserServiceImplTest {
    private final UserService service;
    private final UserRepository repository;

    @Test
    void shouldThrowExIfEmailExist() {
        User user = User.builder()
                .name("user")
                .email("test@yandex.ru")
                .build();
        repository.save(user);

        UserDto userDto = UserDto.builder()
                .name("dto")
                .email("test@yandex.ru")
                .build();

        assertThatThrownBy(() -> service.postUser(userDto))
                .isInstanceOf(EmailConflictException.class);
    }
}