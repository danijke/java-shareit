package ru.practicum.shareit.request.service;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repo.ItemRepository;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.request.repo.ItemRequestRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repo.UserRepository;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Transactional
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Slf4j
class ItemRequestServiceImplTest {
    private final ItemRequestService service;
    private final ItemRequestRepository repository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final EntityManager em;

    @Test
    void shouldGetAllByUserWithItems() {
        User requester = User.builder()
                .name("user 1")
                .email("test@yandex.ru")
                .build();
        requester = userRepository.save(requester);

        User owner = User.builder()
                .name("user 2")
                .email("test@example.com")
                .build();
        owner = userRepository.save(owner);

        ItemRequest request = ItemRequest.builder()
                .description("нужны тесты")
                .created(LocalDateTime.now())
                .requester(requester)
                .build();
        request = repository.save(request);

        Item item = Item.builder()
                .name("тесты")
                .description("пишет сам тесты")
                .available(true)
                .request(request)
                .owner(owner)
                .build();
        itemRepository.save(item);

        em.flush();
        em.clear();

        var result = service.getAllByUserId(requester.getId());
        log.info("result: {}", result);

        assertThat(result)
                .hasSize(1)
                .first()
                .satisfies(requestDto -> {
                    assertThat(requestDto.getDescription()).isEqualTo("нужны тесты");
                    assertThat(requestDto.getItems())
                            .hasSize(1)
                            .first()
                            .satisfies(i -> {
                                assertThat(i.getName()).isEqualTo("тесты");
                                assertThat(i.getDescription()).isEqualTo("пишет сам тесты");
                            });
                });
    }

}