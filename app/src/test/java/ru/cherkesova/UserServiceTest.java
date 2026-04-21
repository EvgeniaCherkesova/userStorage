package ru.cherkesova;

//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import ru.cherkesova.dto.UserRequest;
//import ru.cherkesova.dto.UserResponse;
//import ru.cherkesova.exception.DuplicateEmailException;
//import ru.cherkesova.exception.ResourceNotFoundException;
//import ru.cherkesova.model.AppUser;
//import ru.cherkesova.repository.UserRepository;
//import ru.cherkesova.service.UserService;
//
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//class UserServiceTest {
//
//    @Mock
//    private UserRepository userRepository;
//
//    @InjectMocks
//    private UserService userService;
//
//    @Test
//    void shouldReturnAllUsers() {
//        List<AppUser> users = List.of(
//                new AppUser("John", "john@test.com"),
//                new AppUser("Jane", "jane@test.com")
//        );
//
//        when(userRepository.findAll()).thenReturn(users);
//
//        List<UserResponse> result = userService.getAll();
//
//        assertEquals(2, result.size());
//        assertEquals("John", result.get(0).getName());
//    }
//
//    @Test
//    void shouldReturnUserById() {
//        AppUser user = new AppUser("John", "john@test.com");
//        user.setId(1L);
//
//        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
//
//        UserResponse result = userService.getById(1L);
//
//        assertEquals("John", result.getName());
//    }
//
//    @Test
//    void shouldThrowIfUserNotFound() {
//        when(userRepository.findById(1L)).thenReturn(Optional.empty());
//
//        assertThrows(ResourceNotFoundException.class,
//                () -> userService.getById(1L));
//    }
//
//    @Test
//    void shouldCreateUser() {
//        UserRequest request = new UserRequest();
//        request.setName("John");
//        request.setEmail("john@test.com");
//
//        when(userRepository.findByEmail("john@test.com"))
//                .thenReturn(Optional.empty());
//
//        AppUser saved = new AppUser("John", "john@test.com");
//        saved.setId(1L);
//
//        when(userRepository.save(any())).thenReturn(saved);
//
//        UserResponse result = userService.create(request);
//
//        assertEquals(1L, result.getId());
//        assertEquals("john@test.com", result.getEmail());
//    }
//
//    @Test
//    void shouldThrowOnDuplicateEmailCreate() {
//        UserRequest request = new UserRequest();
//        request.setName("John");
//        request.setEmail("john@test.com");
//
//        when(userRepository.findByEmail("john@test.com"))
//                .thenReturn(Optional.of(new AppUser()));
//
//        assertThrows(DuplicateEmailException.class,
//                () -> userService.create(request));
//    }
//
//    @Test
//    void shouldUpdateUser() {
//        AppUser existing = new AppUser("Old", "old@test.com");
//        existing.setId(1L);
//
//        when(userRepository.findById(1L)).thenReturn(Optional.of(existing));
//        when(userRepository.findByEmail("new@test.com"))
//                .thenReturn(Optional.empty());
//
//        when(userRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));
//
//        UserRequest request = new UserRequest();
//        request.setName("New");
//        request.setEmail("new@test.com");
//
//        UserResponse result = userService.update(1L, request);
//
//        assertEquals("New", result.getName());
//        assertEquals("new@test.com", result.getEmail());
//    }
//
//    @Test
//    void shouldThrowOnDuplicateEmailUpdate() {
//        AppUser existing = new AppUser("Old", "old@test.com");
//        existing.setId(1L);
//
//        AppUser conflict = new AppUser("Other", "new@test.com");
//        conflict.setId(2L);
//
//        when(userRepository.findById(1L)).thenReturn(Optional.of(existing));
//        when(userRepository.findByEmail("new@test.com"))
//                .thenReturn(Optional.of(conflict));
//
//        UserRequest request = new UserRequest();
//        request.setName("New");
//        request.setEmail("new@test.com");
//
//        assertThrows(DuplicateEmailException.class,
//                () -> userService.update(1L, request));
//    }
//
//    @Test
//    void shouldDeleteUser() {
//        when(userRepository.existsById(1L)).thenReturn(true);
//
//        userService.delete(1L);
//
//        verify(userRepository).deleteById(1L);
//    }
//
//    @Test
//    void shouldThrowWhenDeletingNonExistingUser() {
//        when(userRepository.existsById(1L)).thenReturn(false);
//
//        assertThrows(ResourceNotFoundException.class,
//                () -> userService.delete(1L));
//    }
//}
