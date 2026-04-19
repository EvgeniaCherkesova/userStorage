package ru.cherkesova.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.cherkesova.dto.UserRequest;
import ru.cherkesova.dto.UserResponse;
import ru.cherkesova.exception.DuplicateEmailException;
import ru.cherkesova.exception.ResourceNotFoundException;
import ru.cherkesova.model.AppUser;
import ru.cherkesova.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserResponse> getAll() {
        return userRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public UserResponse getById(long id) {
        AppUser user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: id=" + id));
        return toResponse(user);
    }

    @Transactional
    public UserResponse create(UserRequest request) {
        userRepository.findByEmail(request.getEmail()).ifPresent(existing -> {
            throw new DuplicateEmailException("Email already in use: " + request.getEmail());
        });

        AppUser user = new AppUser(request.getName(), request.getEmail());
        AppUser saved = userRepository.save(user);
        return toResponse(saved);
    }

    @Transactional
    public UserResponse update(long id, UserRequest request) {
        AppUser user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: id=" + id));

        userRepository.findByEmail(request.getEmail())
                .filter(existing -> !existing.getId().equals(id))
                .ifPresent(conflict -> {
                    throw new DuplicateEmailException("Email already in use: " + request.getEmail());
                });

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        AppUser saved = userRepository.save(user);
        return toResponse(saved);
    }

    @Transactional
    public void delete(long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found: id=" + id);
        }
        userRepository.deleteById(id);
    }

    private UserResponse toResponse(AppUser user) {
        return new UserResponse(user.getId(), user.getName(), user.getEmail());
    }
}

